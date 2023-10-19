package com.main.feifan.initializer;

import android.content.Context;

import com.main.feifan.config.Globals;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.config.TrojanHelper;


public class ProxyInitializer extends Initializer {
    private static final String TAG = "ProxyInitializer";

    @Override
    public void init(Context context) {
        Globals.Init(context);
        TrojanConfig cacheConfig = TrojanHelper.readTrojanConfig(Globals.getTrojanConfigPath());
        if (cacheConfig == null) {
            LogHelper.e(TAG, "read null trojan config");
        } else {
            Globals.setTrojanConfigInstance(cacheConfig);
        }
        if (!Globals.getTrojanConfigInstance().isValidRunningConfig()) {
            LogHelper.e(TAG, "Invalid trojan config!");
        }
    }

    @Override
    public boolean runsInWorkerThread() {
        return false;
    }
}
