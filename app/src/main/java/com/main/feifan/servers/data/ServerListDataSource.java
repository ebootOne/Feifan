package com.main.feifan.servers.data;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.main.feifan.config.TrojanConfig;
import com.stealthcopter.networktools.ping.PingStats;

import java.util.Collection;
import java.util.List;

public interface ServerListDataSource {
    @WorkerThread
    List<TrojanConfig> loadServerConfigList();

    @WorkerThread
    void deleteServerConfig(TrojanConfig config);

    @WorkerThread
    void batchDeleteServerConfigs(Collection<TrojanConfig> configs);

    @WorkerThread
    void saveServerConfig(TrojanConfig config);

    @WorkerThread
    void replaceServerConfigs(List<TrojanConfig> list);

    @WorkerThread
    void requestSubscribeServerConfigs(String url, @NonNull Callback callback);

    @WorkerThread
    void pingTrojanConfigServer(TrojanConfigWrapper config, @NonNull PingCallback callback,int position);

    /**
     * Parse trojan configs from {@param fileUri}. Combine with current trojan config list and return
     * the complete list.
     *
     * @param context Context
     * @param fileUri File Uri
     * @return List of all trojan configs.
     */
    @WorkerThread
    List<TrojanConfig> importServersFromFile(Context context, Uri fileUri);

    @WorkerThread
    boolean exportServers(String exportPath);

    interface Callback {
        void onSuccess();

        void onFailed();
    }

    interface PingCallback {
        void onSuccess(TrojanConfigWrapper config, PingStats pingStats,int position);

        void onFailed(TrojanConfigWrapper config,int position);
    }
}
