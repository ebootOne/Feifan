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

import static com.main.feifan.ui.adapter.ConnectItemAdapter.Notify.CANCEL_SELECT;
import static com.main.feifan.ui.adapter.ConnectItemAdapter.Notify.SELECT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.main.feifan.R;
import com.main.feifan.config.LogHelper;
import com.main.feifan.config.TrojanConfig;
import com.main.feifan.databinding.ItemServerItemBinding;
import com.main.feifan.recycle.SimpleDataBindingAdapter;
import com.main.feifan.servers.data.ServerListDataManager;
import com.main.feifan.servers.data.TrojanConfigWrapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Create by KunMinX at 20/4/19
 */
public class ConnectItemAdapter extends SimpleDataBindingAdapter<TrojanConfigWrapper, ItemServerItemBinding> {
    final String TAG = "ConnectItemAdapter";
    TrojanConfig config ;
    public static enum Notify{
        SELECT,
        CANCEL_SELECT
    }

    public ConnectItemAdapter(Context context) {
        super(context, R.layout.item_server_item, DiffUtils.getInstance().getLibraryInfoItemCallback());
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindItem(ItemServerItemBinding binding, TrojanConfigWrapper item, RecyclerView.ViewHolder holder) {
        binding.setConfig(item);
        if (item.getPingDelayTime() == ServerListDataManager.SERVER_STATUS_INIT) {
            binding.mPingDelayTimeView.setText(R.string.time_out_txt);
            binding.mPingDelayTimeView.setTextColor(Color.RED);
        } else if (item.getPingDelayTime() == ServerListDataManager.SERVER_UNABLE_TO_REACH) {
            binding.mPingDelayTimeView.setText(R.string.trojan_service_not_available);
            binding.mPingDelayTimeView.setTextColor(Color.RED);
        } else {
            binding.mPingDelayTimeView.setVisibility(View.VISIBLE);
            if (item.getPingDelayTime() > ServerListDataManager.SLOW_SPEED_NETWORK) {
                BigDecimal b = BigDecimal.valueOf(item.getPingDelayTime() / 1000);
                float pintTime = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                binding.mPingDelayTimeView.setText(pintTime + " s");
                binding.mPingDelayTimeView.setTextColor(Color.RED);
            } else {
                binding.mPingDelayTimeView.setText(item.getPingDelayTime() + " ms");
                if (item.getPingDelayTime() < ServerListDataManager.HIGH_SPEED_NETWORK) {
                    binding.mPingDelayTimeView.setTextColor(R.color.black);
                }
            }
        }
        /*if (config.equals(item.getIdentifier())) {
        }*/

    }

    public void setPingServerDelayTime(TrojanConfigWrapper configWrapper, float timeout,int position) {
        LogHelper.i(TAG,"setPingServerDelayTime--"+position+"--timeout:"+timeout);
        getCurrentList().get(position).setPingDelayTime(timeout); //替换对象
        notifyItemChanged(position);//更新
    }

    /*@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        ItemServerItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
        if(payloads != null){
            Notify notify = (Notify) payloads.get(0);
            if(notify == SELECT){
                binding.setIsSelect(true);
            }else if(notify == CANCEL_SELECT){
                binding.setIsSelect(false);
            }
        }
    }*/
}
