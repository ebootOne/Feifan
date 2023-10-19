package com.main.feifan.ui.connect;

import androidx.appcompat.app.AppCompatActivity;

import com.main.feifan.BR;
import com.main.feifan.R;
import com.main.feifan.common.os.Task;
import com.main.feifan.common.os.Threads;
import com.main.feifan.config.Globals;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.config.TrojanHelper;
import com.main.feifan.event.MainMessage;
import com.main.feifan.event.ReadFileEvent;
import com.main.feifan.request.ReadFileRequester;
import com.main.feifan.servers.contract.ServerListContract;
import com.main.feifan.servers.data.ServerListDataManager;
import com.main.feifan.servers.data.ServerListPresenter;
import com.main.feifan.servers.data.TrojanConfigWrapper;
import com.main.feifan.ui.adapter.ConnectItemAdapter;
import com.main.feifan.utils.ToastUtils;
import com.main.feifan.viewModel.MainVmModel;
import com.main.x_base.BaseFragment;
import com.main.x_base.binding.DataBindingConfig;
import com.main.x_base.page.StateHolder;
import com.main.x_base.state.State;

import java.util.ArrayList;
import java.util.List;

public class ConnectFragment extends BaseFragment implements ServerListContract.View{
    ConnectStates connectStates;
    MainVmModel mainVmModel;
    ReadFileRequester readFileRequester;
    ConnectItemAdapter mAdapter;
    int currentPosition = -1;
    private ServerListContract.Presenter mPresenter;
    @Override
    protected void initViewModel() {
        connectStates = getFragmentScopeViewModel(ConnectStates.class);
        mainVmModel = getApplicationScopeViewModel(MainVmModel.class);
        new ServerListPresenter(this,new ServerListDataManager(Globals.getTrojanConfigListPath(), false, "", 0L));
        readFileRequester = getFragmentScopeViewModel(ReadFileRequester.class);
        readFileRequester.input(new ReadFileEvent(ReadFileEvent.READ_FILE_PROXY));//通过线程去拿数据
        readFileRequester.output((AppCompatActivity) getActivity(), msg ->{
            ArrayList<TrojanConfigWrapper> mData = new ArrayList<>();
            for (TrojanConfig config : msg.getTrojanConfigs()) {
                TrojanConfigWrapper wrapper = new TrojanConfigWrapper(config);
                if(mainVmModel.getConfigMutableResult().getValue().getIdentifier().equals(config.getIdentifier())) {//与当前正在运行的相同，表示选中的
                    wrapper.setSelected(true);
                    currentPosition = msg.getTrojanConfigs().indexOf(config);
                }
                mData.add(wrapper);
            }
            connectStates.listState.set(mData);
            mPresenter.pingAllProxyServer(mAdapter.getCurrentList());//网络延迟
        });
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mAdapter = new ConnectItemAdapter(getContext());
        return new DataBindingConfig(R.layout.fragment_connect, BR.vm, connectStates)
                .addBindingParam(BR.adapter, mAdapter)
                .addBindingParam(BR.clickProxy,new ClickProxy());
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //TODO tip 3：绑定跟随视图控制器生命周期、可叫停、单独放在 UseCase 中处理的业务
        getLifecycle().addObserver(readFileRequester);
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter.setOnItemClickListener((viewId, item, position) -> {
            //ToastUtils.showLongToast("点击了");
            //mainVmModel.getConfigMutableResult().setValue(connectStates.listState.get().get(position));
            if(currentPosition != position) {
                TrojanConfigWrapper config = mAdapter.getCurrentList().get(position);
                TrojanConfig currentTrojanConfig = Globals.getTrojanConfigInstance();
                if(!config.getIdentifier().equals(currentTrojanConfig.getIdentifier())) {//连接的地址不一样
                    config.setSelected(true);
                    mAdapter.getCurrentList().get(currentPosition).setSelected(false);
                    mAdapter.notifyItemChanged(currentPosition);
                    currentPosition = position;
                    mAdapter.notifyItemChanged(position);
                    mainVmModel.input(new MainMessage(MainMessage.EVENT_CONNECT_CHANG, config));//更新连接
                }
            }
        });

    }

    public class ClickProxy{
        public void update(){
            mPresenter.pingAllProxyServer(mAdapter.getCurrentList());//网络延迟
        }
    }
    @Override
    public void setPingServerDelayTime(TrojanConfigWrapper config, float timeout,int position) {
        mAdapter.setPingServerDelayTime(config, timeout,position);
    }

    @Override
    public void setPresenter(ServerListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static class ConnectStates extends StateHolder {
        public final State<List<TrojanConfigWrapper>> listState = new State<>(new ArrayList<>());

    }



}