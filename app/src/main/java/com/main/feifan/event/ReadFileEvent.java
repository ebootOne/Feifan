package com.main.feifan.event;

import com.main.feifan.config.TrojanConfig;

import java.util.List;

/**
 * Create by KunMinX at 2022/7/4
 */
public class ReadFileEvent {
    public final static int READ_FILE_PROXY = 1;
    public final static int EVENT_DOWNLOAD_GLOBAL = 2;

    public final int eventId;
    public  List<TrojanConfig> trojanConfigs;

    public List<TrojanConfig> getTrojanConfigs() {
        return trojanConfigs;
    }

    public ReadFileEvent(int eventId) {
        this.eventId = eventId;
    }

    public ReadFileEvent(int eventId, List<TrojanConfig> trojanConfigs) {
        this.eventId = eventId;
        this.trojanConfigs = trojanConfigs;
    }

    public ReadFileEvent copy(List<TrojanConfig> trojanConfig) {
        return new ReadFileEvent(this.eventId, trojanConfig);
    }
}
