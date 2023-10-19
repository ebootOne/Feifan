package com.main.feifan.initializer;

import android.content.Context;

import com.main.feifan.common.sp.CommonSP;
import com.main.feifan.config.Globals;

/**
 * Initializer that runs in Main Process (Default process).
 */
public class MainInitializer extends Initializer {

    @Override
    public void init(Context context) {
        Globals.Init(context);
        //CommonSP.init(context);
    }

    @Override
    public boolean runsInWorkerThread() {
        return false;
    }
}
