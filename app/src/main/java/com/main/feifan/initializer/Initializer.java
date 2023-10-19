package com.main.feifan.initializer;

import android.content.Context;

public abstract class Initializer {

    public abstract void init(Context context);

    public abstract boolean runsInWorkerThread();
}
