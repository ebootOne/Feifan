package com.main.feifan.common.constants;

import com.main.feifan.BuildConfig;

public abstract class Constants {
    public static final String PREFERENCE_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String PREFERENCE_PATH = "preferences";
    public static final String PREFERENCE_URI = "content://" + PREFERENCE_AUTHORITY + "/" + PREFERENCE_PATH;
    public static final String PREFERENCE_KEY_ENABLE_CLASH = "enable_clash";
    public static final String PREFERENCE_KEY_ALLOW_LAN = "allow_lan";
    public static final String PREFERENCE_KEY_FIRST_START = "first_start";
    public static final String PREFERENCE_KEY_PROXY_IN_ALLOW_MODE = "proxy_allow_mode";
    public static final String PREFERENCE_KEY_EXTRA_DNS = "extra_dns";
    public static final String PREFERENCE_KEY_FIXED_PORT = "fixed_port";
}
