// ITrojanService.aidl
package com.main.feifan;
import com.main.feifan.ITrojanServiceCallback;
// Declare any non-default types here with import statements

interface ITrojanService {
    int getState();
    void testConnection(String testUrl);
    void showDevelopInfoInLogcat();
    String getProxyHost();
    long getProxyPort();
    oneway void registerCallback(in ITrojanServiceCallback callback);
    oneway void unregisterCallback(in ITrojanServiceCallback callback);
}
