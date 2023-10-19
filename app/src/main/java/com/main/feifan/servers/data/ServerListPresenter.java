package com.main.feifan.servers.data;

import com.main.feifan.common.os.Threads;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.servers.contract.ServerListContract;
import com.stealthcopter.networktools.ping.PingStats;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ServerListPresenter implements ServerListContract.Presenter{
    private final ServerListContract.View mView;
    private final ServerListDataSource mDataManager;
    private Set<TrojanConfig> mBatchDeleteConfigSet;

    public ServerListPresenter(ServerListContract.View view, ServerListDataSource dataManager) {
        mView = view;
        mDataManager = dataManager;
        view.setPresenter(this);
    }



    @Override
    public void pingAllProxyServer(List<TrojanConfigWrapper> configList) {
        int index = 0;
        for (TrojanConfigWrapper config : configList) {
            mDataManager.pingTrojanConfigServer(config, new PingServerCallBack(this),index);
            index++;
        }
    }

    private void setPingDelayTime(TrojanConfigWrapper config, float pingDelayTime,int position) {
        Threads.instance().runOnUiThread(() -> {
            mView.setPingServerDelayTime(config, pingDelayTime,position);
        });
    }
    private static class PingServerCallBack implements ServerListDataSource.PingCallback {
        private final WeakReference<ServerListPresenter> mPresenterRef;

        public PingServerCallBack(ServerListPresenter presenter) {
            mPresenterRef = new WeakReference<>(presenter);
        }

        @Override
        public void onSuccess(TrojanConfigWrapper config, PingStats pingStats,int position) {
            ServerListPresenter presenter = mPresenterRef.get();
            if (presenter != null) {
                //LogHelper.d(TAG, "ping "+ config.getRemoteAddr() + ": "+ pingStats.toString());
                if (!pingStats.isReachable()) {
                    presenter.setPingDelayTime(config, ServerListDataManager.SERVER_UNABLE_TO_REACH,position);
                    return;
                }
                BigDecimal b = BigDecimal.valueOf(pingStats.getAverageTimeTaken());
                float pingDelayTime = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                presenter.setPingDelayTime(config, pingDelayTime,position);
            }
        }

        @Override
        public void onFailed(TrojanConfigWrapper config,int position) {
            ServerListPresenter presenter = mPresenterRef.get();
            if (presenter != null) {
                presenter.setPingDelayTime(config, ServerListDataManager.SERVER_UNABLE_TO_REACH,position);
            }
        }
    }

}
