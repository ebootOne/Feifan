package com.main.feifan.servers.contract;

import com.main.feifan.config.TrojanConfig;
import com.main.feifan.servers.base.BasePresenter;
import com.main.feifan.servers.base.BaseView;
import com.main.feifan.servers.data.TrojanConfigWrapper;

import java.util.List;

public class ServerListContract {
    public interface Presenter extends BasePresenter {
        void pingAllProxyServer(List<TrojanConfigWrapper> configList);
    }

    public interface View extends BaseView<Presenter> {
        void setPingServerDelayTime(TrojanConfigWrapper config, float timeout,int position);
    }
}
