package com.main.feifan.app;

import android.app.Application;
import android.content.Context;

import com.main.feifan.initializer.InitializerHelper;
import com.main.x_base.utils.Utils;

public class FeifanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Utils.init(this);
        InitializerHelper.runInit(this);

    }
}
