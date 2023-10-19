/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.main.feifan.data.repository;

import android.annotation.SuppressLint;

import com.main.feifan.config.Globals;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.config.TrojanHelper;
import com.main.x_base.domain.request.AsyncTask;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * Create by KunMinX at 19/10/29
 */
public class DataRepository {

    private static final DataRepository S_REQUEST_MANAGER = new DataRepository();

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }


    //TODO tip: 通过 "响应式框架" 往领域层回推数据，
    // 与此相对应，kotlin 下使用 flow{ ... emit(...) }.flowOn(Dispatchers.xx)


    /**
     * TODO：模拟下载任务:
     */
    public Observable<TrojanConfig> downloadFile() {
        return AsyncTask.doIO(emitter -> {
            //在内存中模拟 "数据读写"，假装是在 "文件 IO"，

        });
    }

    public Observable<List<TrojanConfig>> loadServerConfigList() {
        return AsyncTask.doIO(emitter -> {
            List<TrojanConfig>  trojanConfigs = TrojanHelper.readTrojanServerConfigList(Globals.getTrojanConfigListPath());
            emitter.onNext(trojanConfigs);
        });
    }


}
