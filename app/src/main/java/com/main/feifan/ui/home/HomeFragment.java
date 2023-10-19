package com.main.feifan.ui.home;

import android.content.Intent;
import android.net.VpnService;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.main.feifan.BR;
import com.main.feifan.ITrojanService;
import com.main.feifan.MainActivity;
import com.main.feifan.ProxyService;
import com.main.feifan.R;
import com.main.feifan.config.Globals;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.config.TrojanHelper;
import com.main.feifan.connection.TrojanConnection;
import com.main.feifan.event.MainMessage;
import com.main.feifan.tile.ProxyHelper;
import com.main.feifan.utils.ToastUtils;
import com.main.feifan.viewModel.MainVmModel;
import com.main.x_base.BaseFragment;
import com.main.x_base.binding.DataBindingConfig;
import com.main.x_base.page.StateHolder;
import com.main.x_base.state.State;
import com.main.x_base.utils.Utils;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    HomeViewModel homeVmModel;
    private MainVmModel mainVmModel;
    @Override
    protected void initViewModel() {

        mainVmModel = getApplicationScopeViewModel(MainVmModel.class);
        homeVmModel = getFragmentScopeViewModel(HomeViewModel.class);

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        return new DataBindingConfig(R.layout.fragment_home, BR.vm, homeVmModel).addBindingParam(BR.click,new ClickProxy());
    }

    @Override
    public void initData() {
        super.initData();
        mainVmModel.getState().observe(this,state ->{
            homeVmModel.isEnable.set(true);
            updateViews(state);
        });

    }

    public class ClickProxy{
        public void toConnect(){
            homeVmModel.isEnable.set(false);
            mainVmModel.input(new MainMessage(MainMessage.EVENT_HOME_CONNECT_CHANG));
        }
    }

    private void updateViews(int state) {
        LogHelper.i(TAG, "updateViews"+state);
        boolean inputEnabled = false;
        switch (state) {
            case ProxyService.STARTED://已经开始
                ToastUtils.showLongToast(getString(R.string.connected_txt));
                inputEnabled = true;
                break;
        }
        homeVmModel.isConnect.set(inputEnabled);
    }


    public static class HomeViewModel extends StateHolder {

        public final State<Boolean> isConnect = new State<>(false);
        public final State<Boolean> isEnable = new State<>(false);
    }


}