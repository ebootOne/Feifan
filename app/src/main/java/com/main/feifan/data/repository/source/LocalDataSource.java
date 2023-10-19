package com.main.feifan.data.repository.source;

import androidx.annotation.NonNull;

import com.main.feifan.data.bean.user.UserBody;

/**
 * Created by helu on 2019/3/26.
 */
public interface LocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();

    /**
     * 保存手机号
     * */
    void savePhone(String phone);

    /**
     * 获取手机号
     *
     * @return*/
    String getPhone();

    /**
     * 保存手机号
     * */
    void saveSysId(String sysId);

    /**
     * 获取手机号
     *
     * @return*/
    String getSysId();

    /**
     * 用户信息保存
     * */
    void AddUser(UserBody userBody);

    /**
     * 用户信息取出
     * */
    UserBody getUser();

    /**
     *字段保存
     */
    void updateDataAsync(@NonNull final String key, String value);

    String getStringData(@NonNull String key);

    /**
     * 保存状态
     * */
    void saveLoginState(boolean state);
    boolean getLoginState();

    /**
     * 保存boolean
     * */
    void savaBool(@NonNull final String key, boolean value);

    boolean getBool(@NonNull String key);
}
