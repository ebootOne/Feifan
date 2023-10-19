package com.main.x_base;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import com.main.x_base.binding.SimpleActivity;
import com.main.x_base.binding.DataBindingConfig;
import com.main.x_base.manager.NetworkStateManager;
import com.main.x_base.page.StateHolder;
import com.main.x_base.scope.ViewModelScope;
import com.main.x_base.utils.AdaptScreenUtils;
import com.main.x_base.utils.BarUtils;
import com.main.x_base.utils.ScreenUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseVmActivity<VM extends StateHolder> extends SimpleActivity {
    VM mViewModel;
    private final ViewModelScope mViewModelScope = new ViewModelScope();

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView();

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();
    @Override
    protected DataBindingConfig getDataBindingConfig() {

        //TODO tip 2: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 View 实例 Null 安全一致性问题，
        // 如此，View 实例 Null 安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样背景下，用于为 base 页面 DataBinding 提供绑定项。

        // 如这么说无体会，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = StateHolder.class;
            }
            mViewModel = (VM) mViewModelScope.getActivityScopeViewModel(this, modelClass);
            //让ViewModel拥有View的生命周期感应
            //getLifecycle().addObserver(mViewModel);
            //TODO tip 3：绑定跟随视图控制器生命周期、可叫停、单独放在 UseCase 中处理的业务
        }
        return new DataBindingConfig(initContentView(), initVariableId(), mViewModel);
    }


    /**
     * 初始化参数=====================================================================
     **/
    public void initParam() {

    }


    //页面数据初始化方法
    public void initData() {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);
        //页面接受的参数方法
        initParam();
        super.onCreate(savedInstanceState);
        //页面数据初始化方法
        initData();
        getLifecycle().addObserver(NetworkStateManager.getInstance());

        //TODO tip 1: DataBinding 严格模式（详见 DataBindingActivity - - - - - ）：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这方式，彻底解决 View 实例 Null 安全一致性问题，
        // 如此，View 实例 Null 安全性将和基于函数式编程思想的 Jetpack Compose 持平。

        // 如这么说无体会，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
    }

    //TODO tip 2: Jetpack 通过 "工厂模式" 实现 ViewModel 作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域 Provider 获得 ViewModel 实例非同一个，
    //故若 ViewModel 状态信息保留不符合预期，可从该角度出发排查 是否眼前 ViewModel 实例非目标实例所致。

    //如这么说无体会，详见 https://xiaozhuanlan.com/topic/6257931840
    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }


}
