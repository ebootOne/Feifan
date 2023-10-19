package com.main.feifan.servers.base;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
