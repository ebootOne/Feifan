package com.main.feifan.data.api;


import com.main.feifan.data.bean.BaseResponseBody;
import com.main.feifan.data.repository.source.HttpDataSource;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by hl on 2019/3/26.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private RemoteMeetingService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(RemoteMeetingService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(RemoteMeetingService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<BaseResponseBody> login(String phone, String password) {
        return apiService.loginByPassword(phone,password,"");
    }


}
