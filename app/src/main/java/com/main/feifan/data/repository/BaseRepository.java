package com.main.feifan.data.repository;


import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.main.feifan.data.bean.BaseResponseBody;
import com.main.feifan.data.bean.user.UserBody;
import com.main.feifan.data.repository.source.HttpDataSource;
import com.main.feifan.data.repository.source.LocalDataSource;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by hl on 2019/3/26.
 */
public class BaseRepository implements HttpDataSource, LocalDataSource {
    private volatile static BaseRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private BaseRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static BaseRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (BaseRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<BaseResponseBody> login(String phone, String password) {
        return mHttpDataSource.login(phone,password);
    }

    /*线程调度*/
    public static <T> Observable<T> rxSchedulerHelper( Observable<T> observable) {    //compose简化线程
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());  //UI线程
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public void savePhone(String phone) {
        mLocalDataSource.savePhone(phone);
    }

    @Override
    public void savaBool(@NotNull String key, boolean value) {
        mLocalDataSource.savaBool(key,value);
    }

    @Override
    public boolean getBool(@NotNull String key) {
        return mLocalDataSource.getBool(key);
    }

    @Override
    public String getPhone() {
        return mLocalDataSource.getPhone();
    }

    @Override
    public void saveSysId(String sysId) {
        mLocalDataSource.saveSysId(sysId);
    }

    @Override
    public String getSysId() {
        return mLocalDataSource.getSysId();
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public void saveLoginState(boolean state) {
        mLocalDataSource.saveLoginState(state);
    }

    @Override
    public boolean getLoginState() {
        return mLocalDataSource.getLoginState();
    }

    @Override
    public void AddUser(UserBody userBody) {
        mLocalDataSource.AddUser(userBody);
    }

    @Override
    public UserBody getUser() {
        return mLocalDataSource.getUser();
    }

    @Override
    public void updateDataAsync(@NotNull String key, String value) {
        mLocalDataSource.updateDataAsync(key,value);
    }

    @Override
    public String getStringData(@NotNull String key) {
        return mLocalDataSource.getStringData(key);
    }


}
