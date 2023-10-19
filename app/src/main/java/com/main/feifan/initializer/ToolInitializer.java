package com.main.feifan.initializer;

import android.content.Context;

import com.main.feifan.config.Globals;

/**
 * Initializer that runs in Tools Process.
 */
public class ToolInitializer extends Initializer {

    @Override
    public void init(Context context) {
        Globals.Init(context);
    }

    @Override
    public boolean runsInWorkerThread() {
        return false;
    }
}
