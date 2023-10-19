package com.main.feifan;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.feifan.common.os.Task;
import com.main.feifan.common.os.Threads;
import com.main.feifan.config.Globals;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.config.TrojanHelper;
import com.main.feifan.config.TrojanURLHelper;
import com.main.feifan.config.TrojanURLParseResult;
import com.main.feifan.connection.TrojanConnection;
import com.main.feifan.event.MainMessage;
import com.main.feifan.servers.data.ServerListDataManager;
import com.main.feifan.servers.data.ServerListDataSource;
import com.main.feifan.tile.ProxyHelper;
import com.main.feifan.ui.home.HomeFragment;
import com.main.feifan.utils.ProcessUtils;
import com.main.feifan.utils.ToastUtils;
import com.main.feifan.viewModel.MainVmModel;
import com.main.x_base.BaseActivity;
import com.main.x_base.binding.DataBindingConfig;
import com.main.x_base.page.StateHolder;
import com.main.x_base.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity implements TrojanConnection.Callback{
    private static final String TAG = "MainActivity";
    private MainActivityStates mStates;
    private volatile ITrojanService trojanService;
    MainVmModel mainVmModel;
    private ActivityResultLauncher<Intent> startProxyActivityResultLauncher;
    private final TrojanConnection connection = new TrojanConnection(false);
    private ServerListDataSource serverListDataManager;
    private boolean isReconnection = false;//是否需要重连
    @Override
    protected void initViewModel() {
        mStates = getActivityScopeViewModel(MainActivityStates.class);
        mainVmModel = getApplicationScopeViewModel(MainVmModel.class);
        startProxyActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                            ProxyHelper.startProxyService(Utils.getApp());
                    }
                });
        ProcessUtils.copyRawResourceToDir(this,R.raw.cacert, Globals.getCaCertPath(), true);
        ProcessUtils.copyRawResourceToDir(this,R.raw.country, Globals.getCountryMmdbPath(), true);
        ProcessUtils.copyRawResourceToDir(this,R.raw.clash_config, Globals.getClashConfigPath(), false);
        serverListDataManager = new ServerListDataManager(Globals.getTrojanConfigListPath(), false, "", 0L);
        connection.connect(this, this);

        TrojanConfig cachedConfig = TrojanHelper.readTrojanConfig(Globals.getTrojanConfigPath());
        if (cachedConfig != null) {
            LogHelper.i("","TrojanConfig---cachedConfig:"+cachedConfig.toString());
            applyConfigInstance(cachedConfig);
        }
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip 2: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 View 实例 Null 安全一致性问题，
        // 如此，View 实例 Null 安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样背景下，用于为 base 页面 DataBinding 提供绑定项。

        return new DataBindingConfig(R.layout.activity_main, BR.vm, mStates);
    }

    @Override
    public void initData() {
        super.initData();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);
        mainVmModel.output( this, msg ->{//接受信息
            switch (msg.eventId){
                case MainMessage.EVENT_CONNECT_CHANG:
                    TrojanConfig config = Globals.getTrojanConfigInstance();
                    if (proxyState == -1 ) {//没有连接
                        applyConfigInstance(msg.getTrojanConfig());
                    }else if(proxyState == ProxyService.STARTED){//已经连接
                        if(!config.getIdentifier().equals(msg.getTrojanConfig().getIdentifier())){//连接的地址不一样
                            applyConfigInstance(msg.getTrojanConfig());//更新数据
                            ProxyHelper.stopProxyService(Utils.getApp());//先暂停
                            isReconnection = true;
                        }
                    }
                    break;
                    case MainMessage.EVENT_HOME_CONNECT_CHANG:
                        setConnection();
                        break;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkTrojanURLFromClipboard();
    }

    private void checkTrojanURLFromClipboard() {
        Threads.instance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                if (!clipboardManager.hasPrimaryClip()) {
                    return;
                }
                ClipData clipData = clipboardManager.getPrimaryClip();
                // check clipboard
                if (clipData == null || clipData.getItemCount() == 0) {
                    return;
                }
                final CharSequence clipboardText = clipData.getItemAt(0).coerceToText(MainActivity.this);
                // check scheme
                TrojanURLParseResult parseResult = TrojanURLHelper.ParseTrojanURL(clipboardText.toString());
                if (parseResult == null) {
                    return;
                }

                // show once if trojan url
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("", ""));
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.clipboard_import_tip)
                        .setPositiveButton(R.string.common_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TrojanConfig newConfig = TrojanURLHelper.CombineTrojanURLParseResultToTrojanConfig(parseResult, Globals.getTrojanConfigInstance());
                                Globals.setTrojanConfigInstance(newConfig);
                                applyConfigInstance(newConfig);
                                TrojanConfig config = Globals.getTrojanConfigInstance();
                                TrojanHelper.WriteTrojanConfig(config, Globals.getTrojanConfigPath());
                            }
                        })
                        .setNegativeButton(R.string.common_cancel, null)
                        .create()
                        .show();
            }
        });
    }

    private final Object lock = new Object();
    private static final long INVALID_PORT = -1L;
    @Override
    public void onServiceConnected(final ITrojanService service) {
        LogHelper.i(TAG, "onServiceConnected");
        synchronized (lock) {
            trojanService = service;
            try {
                final int state = service.getState();
                proxyState = state;
                //mainVmModel.input(new MainMessage(MainMessage.EVENT_HOME_CONNECT_STATE,state));
                mainVmModel.getState().setValue(state);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onServiceDisconnected() {
        LogHelper.i(TAG, "onServiceDisconnected");
        synchronized (lock) {
            trojanService = null;
        }
    }

    @Override
    public void onStateChanged(int state, String msg) {
        LogHelper.i(TAG, "onStateChanged# state: " + state + " msg: " + msg);
        proxyState = state;
        if(proxyState == ProxyService.STOPPED && isReconnection){//暂停了 需要重连
            isReconnection = false;
            setConnection();//重新连接
        }
        //mainVmModel.input(new MainMessage(MainMessage.EVENT_HOME_CONNECT_STATE,state));
        mainVmModel.getState().setValue(state);
    }

    @Override
    public void onTestResult(final String testUrl, final boolean connected, final long delay, @NonNull final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTestConnectionResult(testUrl, connected, delay, error);
            }
        });
    }

    private void showTestConnectionResult(String testUrl, boolean connected, long delay, @NonNull String error) {
        if (connected) {
            Toast.makeText(Utils.getApp(), getString(R.string.connected_to__in__ms,
                    testUrl, String.valueOf(delay)), Toast.LENGTH_LONG).show();
        } else {
            LogHelper.e(TAG, "TestError: " + error);
            Toast.makeText(Utils.getApp(),
                    getString(R.string.failed_to_connect_to__,
                            testUrl, error),
                    Toast.LENGTH_LONG).show();
        }
    }



    private void applyConfigInstance(TrojanConfig config) {
        TrojanConfig ins = Globals.getTrojanConfigInstance();

        if (config != null) {
            String remoteServerRemark = config.getRemoteServerRemark();
            String remoteAddress = config.getRemoteAddr();
            String remoteServerSNI = config.getSNI();
            int remotePort = config.getRemotePort();
            String password = config.getPassword();
            boolean verifyCert = config.getVerifyCert();
            boolean enableIpv6 = config.getEnableIpv6();

            ins.setRemoteServerRemark(remoteServerRemark);
            ins.setSNI(remoteServerSNI);
            ins.setRemoteAddr(remoteAddress);
            ins.setRemotePort(remotePort);
            ins.setPassword(password);
            ins.setVerifyCert(verifyCert);
            ins.setEnableIpv6(enableIpv6);
            mainVmModel.getConfigMutableResult().setValue(ins);
            Threads.instance().runOnWorkThread(new Task() {
                @Override
                public void onRun() {
                    serverListDataManager.saveServerConfig(ins);
                }
            });
        }
    }

    @Override
    public void onBinderDied() {
        LogHelper.i(TAG, "onBinderDied");
        connection.disconnect(this);
        // connect the new binder
        // todo is it necessary to re-connect?
        connection.connect(this, this);
    }

    private @ProxyService.ProxyState
    int proxyState = ProxyService.STATE_NONE;
    public void setConnection(){
        if (!Globals.getTrojanConfigInstance().isValidRunningConfig()) {
            Toast.makeText(this,
                    R.string.invalid_configuration,
                    Toast.LENGTH_LONG).show();
            return;
        }
        LogHelper.i(TAG,"TrojanConfig---STARTED"+proxyState);
        if (proxyState == ProxyService.STATE_NONE || proxyState == ProxyService.STOPPED) {
            TrojanHelper.WriteTrojanConfig(
                    Globals.getTrojanConfigInstance(),
                    Globals.getTrojanConfigPath()
            );
            TrojanHelper.ShowConfig(Globals.getTrojanConfigPath());
            // start ProxyService
            Intent i = VpnService.prepare(Utils.getApp());
            if (i != null) {
                startProxyActivityResultLauncher.launch(i);
            } else {
                ProxyHelper.startProxyService(Utils.getApp());
            }
        } else if (proxyState == ProxyService.STARTED) {
            // stop ProxyService
            ProxyHelper.stopProxyService(Utils.getApp());
        }
    }

    public static class MainActivityStates extends StateHolder {

    }



}