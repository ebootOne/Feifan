package com.main.feifan.ui.my;

import android.os.Bundle;

import com.main.feifan.BR;
import com.main.feifan.R;
import com.main.x_base.BaseActivity;
import com.main.x_base.binding.DataBindingConfig;

/**
 * @author Planck
 */
public class ProtocolAc extends BaseActivity {

    @Override
    protected void initViewModel() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.ac_protocol, BR.vm,null).addBindingParam(BR.clickProxy,new ClickProxy());
    }

    public class ClickProxy{
        /**
         * 隐私政策
         * */
        public void onBack(){
            finish();
        }
    }
}