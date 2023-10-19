// ITrojanServiceCallback.aidl
package com.main.feifan;

// Declare any non-default types here with import statements

interface ITrojanServiceCallback {
    void onStateChanged(int state, String msg);
    void onTestResult(String testUrl, boolean connected, long delay, String error);
}
