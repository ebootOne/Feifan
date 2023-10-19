package com.main.feifan.config;

public class TrojanConfigInfo {
    boolean isSelect;
    int currentPosition;
    TrojanConfig trojanConfig;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TrojanConfig getTrojanConfig() {
        return trojanConfig;
    }

    public void setTrojanConfig(TrojanConfig trojanConfig) {
        this.trojanConfig = trojanConfig;
    }
}
