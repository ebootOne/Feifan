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

package com.main.feifan.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.main.feifan.config.TrojanConfig;
import com.main.feifan.servers.data.TrojanConfigWrapper;


/**
 * Create by KunMinX at 2020/7/19
 */
public class DiffUtils {


    private DiffUtil.ItemCallback<TrojanConfigWrapper> trojanConfigItemCallback;

    private DiffUtils() {
    }

    private static final DiffUtils S_DIFF_UTILS = new DiffUtils();

    public static DiffUtils getInstance() {
        return S_DIFF_UTILS;
    }

    public DiffUtil.ItemCallback<TrojanConfigWrapper> getLibraryInfoItemCallback() {
        if (trojanConfigItemCallback == null) {
            trojanConfigItemCallback = new DiffUtil.ItemCallback<TrojanConfigWrapper>() {
                @Override
                public boolean areItemsTheSame(@NonNull TrojanConfigWrapper oldItem, @NonNull TrojanConfigWrapper newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull TrojanConfigWrapper oldItem, @NonNull TrojanConfigWrapper newItem) {
                    return oldItem.getRemoteAddr().equals(newItem.getRemoteAddr());
                }
            };
        }
        return trojanConfigItemCallback;
    }

}
