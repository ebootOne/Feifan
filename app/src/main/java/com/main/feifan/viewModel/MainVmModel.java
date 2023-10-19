package com.main.feifan.viewModel;

import android.database.Observable;

import androidx.databinding.ObservableField;

import com.main.feifan.config.TrojanConfig;
import com.main.feifan.event.MainMessage;
import com.main.x_base.dispatch.MviDispatcher;
import com.main.x_base.message.MutableResult;

public class MainVmModel extends MviDispatcher<MainMessage> {
    MutableResult<TrojanConfig> configMutableResult = new MutableResult<>();
    MutableResult<Integer> state = new MutableResult<>();
    public ObservableField<TrojanConfig> currentTrojanConfig = new ObservableField<>();

    public MutableResult<Integer> getState() {
        return state;
    }

    public MutableResult<TrojanConfig> getConfigMutableResult() {
        return configMutableResult;
    }

    @Override
    protected void onHandle(MainMessage event) {
        sendResult(event);
    }
}
