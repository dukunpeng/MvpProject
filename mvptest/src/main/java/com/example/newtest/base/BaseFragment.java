package com.example.newtest.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Mark on 2018/7/10.
 */

public abstract class BaseFragment extends Fragment  {
    protected View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterRootViewAndInitViews(inflater);
        loadData();
        return rootView;
    }


    /**
     * 初始化Views
     * @param inflater
     */
    protected void inflaterRootViewAndInitViews(@NonNull LayoutInflater inflater) {
        if (getContentLayoutResourceId() == 0) {
            if (getContentLayoutView() != null) {
                 rootView=getContentLayoutView();
            } else {
                XLog.e(this.getClass().getName()+"没有设置界面资源");
                throw new RuntimeException(this.getClass().getName()+"没有设置界面资源");
            }
        } else {
            rootView = inflater.inflate(getContentLayoutResourceId(),null);;
        }
        //初始化UI
        initViews();
        if (getArguments()!=null){
            onBundleReceived(getArguments());
        }
        setListeners();
    }

    /**
     * 如果上个页面有数据传递，则在这里接收
     * @param bundle
     */
    protected void onBundleReceived(Bundle bundle){

    }
    public void setListeners(){

    }
    /**
     * 设置页面资源
     * @return 资源ID
     */
    public abstract int getContentLayoutResourceId();
    public abstract void loadData();
    protected View getContentLayoutView() {
        return null;
    }
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T extends View> T findViewById(@IdRes int id) {
        return rootView.findViewById(id);
    }
    public abstract void initViews();
    protected void setVisibilityGone(View view) {
        if (view != null)
            view.setVisibility(View.GONE);
    }

    protected void setVisibilityVisible(View view) {
        if (view != null)
            view.setVisibility(View.VISIBLE);
    }

    protected void setVisibilityInVisible(View view) {
        if (view != null)
            view.setVisibility(View.INVISIBLE);
    }
}
