package com.main.feifan.event;

import com.main.feifan.config.TrojanConfig;

public class MainMessage {
    public final static int EVENT_CONNECT_CHANG = 1;//连接改变 选择节点连接过来的 TrojanConfig对象
    public final static int EVENT_HOME_CONNECT_CHANG = 2;// 去连接
    public final static int EVENT_HOME_CONNECT_RECONNECTION = 3;// 连断开重连
    public final static int EVENT_HOME_CONNECT_FAIL = 4;// 连接失败
    public final static int EVENT_HOME_CONNECT_SUCCESS = 5;// 连接成功
    public final int eventId;
    public  int  state;
    public TrojanConfig trojanConfig;

    public MainMessage(int eventId) {
        this.eventId = eventId;
    }

    public TrojanConfig getTrojanConfig() {
        return trojanConfig;
    }


    public int getState() {
        return state;
    }

    public MainMessage(int eventId, int state) {
        this.eventId = eventId;
        this.state = state;
    }


    public MainMessage(int eventId, TrojanConfig config) {
        this.eventId = eventId;
        this.trojanConfig = config;
    }

    public MainMessage copy(TrojanConfig config) {
        return new MainMessage(this.eventId, config);
    }

}
