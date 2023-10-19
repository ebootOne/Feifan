package com.main.feifan.ui.my;

import com.main.feifan.BR;
import com.main.feifan.R;
import com.main.feifan.ui.home.HomeFragment;
import com.main.feifan.viewModel.MainVmModel;
import com.main.x_base.BaseFragment;
import com.main.x_base.BaseVmFragment;
import com.main.x_base.binding.DataBindingConfig;

public class MyFragment extends BaseFragment {

    @Override
    protected void initViewModel() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip 2: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 View 实例 Null 安全一致性问题，
        // 如此，View 实例 Null 安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样背景下，用于为 base 页面 DataBinding 提供绑定项。

        // 如这么说无体会，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

        return new DataBindingConfig(R.layout.fragment_my, BR.vm, null).addBindingParam(BR.clickProxy,new ClickProxy());
    }

    public class ClickProxy{
        /**
         * 隐私政策
         * */
        public void onPrivacyPolicy(){
            startActivity(ProtocolAc.class);
        }

        /**
         * 关于
         * */
        public void onAbout(){
            startActivity(AboutAc.class);
        }
    }
}