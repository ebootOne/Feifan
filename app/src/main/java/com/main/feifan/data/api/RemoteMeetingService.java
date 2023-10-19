package com.main.feifan.data.api;


import com.main.feifan.data.bean.BaseResponseBody;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 */
public interface RemoteMeetingService {


    /**
     * 手机密码登录
     *
     * @param phone           手机号码
     * @param password        密码
     * @return 返回token
     */
    @FormUrlEncoded
    @POST("liveUserLogin.html")
    Observable<BaseResponseBody> loginByPassword(@Field("phone") String phone,
                                                 @Field("password") String password, @Field("facilityMessage") String facilityMessage);


}
