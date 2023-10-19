package com.main.feifan.data.repository.source;

import com.main.feifan.data.bean.BaseResponseBody;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by hl on 2019/3/26.
 */
public interface HttpDataSource {
    //模拟登录
    Observable<BaseResponseBody> login(String phone, String password);
    //自动登录


}
