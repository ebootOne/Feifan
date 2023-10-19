package com.main.feifan.data.local;


import com.google.gson.Gson;
import com.main.feifan.data.bean.user.UserBody;
import com.main.feifan.data.repository.source.LocalDataSource;
import com.main.feifan.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 本地数据源，可配合Room框架使用
 * Created by hl on 2019/3/26.
 */
public class LocalDataSourceImpl implements LocalDataSource {


   /* public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }*/

    private LocalDataSourceImpl() {
    }

    /**
     * 初始化
     * */
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void saveUserName(String userName) {

        SPUtils.getInstance().put("UserName", userName);
    }

    @Override
    public void savePhone(String phone) {
        SPUtils.getInstance().put("phone", phone);
    }

    @Override
    public String getPhone() {
        return SPUtils.getInstance().getString("phone");
    }

    @Override
    public void saveSysId(String sysId) {
        SPUtils.getInstance().put("sysId", sysId);
    }

    @Override
    public String getSysId() {
        return SPUtils.getInstance().getString("sysId");
    }

    @Override
    public void saveLoginState(boolean state) {
        SPUtils.getInstance().put("login_state", state);
    }

    @Override
    public boolean getLoginState() {
        return SPUtils.getInstance().getBoolean("login_state");
    }

    /**
    * 保存密码
    * */
    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("password", password);
    }

    /**
     * 用户名
     * */
    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    /**
     * 存储用户信息
     * */

    @Override
    public void AddUser(UserBody userBody) {
        Gson gson = new Gson();
        SPUtils.getInstance().put("userbody", gson.toJson(userBody));
    }

    /**
     * 获取用户信息
     * */
    @Override
    public UserBody getUser() {
        String userInfo = SPUtils.getInstance().getString("userbody");
        if(!"".equals(userInfo)){
            Gson gson = new Gson();
            return gson.fromJson(userInfo,UserBody.class);
        }else
            return null;
    }

    /*存字段*/
    @Override
    public void updateDataAsync(@NotNull String key, String value) {
        SPUtils.getInstance().put(key,value);
    }

    /*
    * 获取字段
    * */
    @Override
    public String getStringData(@NotNull final String key) {
        return SPUtils.getInstance().getString(key);
    }

    @Override
    public void savaBool(@NotNull String key, boolean value) {
        SPUtils.getInstance().put(key,value);
    }

    @Override
    public boolean getBool(@NotNull String key) {
        return  SPUtils.getInstance().getBoolean(key);
    }
}
