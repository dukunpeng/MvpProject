package com.example.newtest.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.newtest.R;
import com.example.newtest.common.ToolbarBgColor;
import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;
import com.example.newtest.request.OkHttpClientManager;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2018/4/7.
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {


    protected P presenter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initMvp();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化MVP关系
     */
    private void initMvp() {
        if (this instanceof IBaseView) {
            //初始化presenter
            presenter = createNewPresenter();
            //生成Module
            presenter.attachModule();
            //attach View
            presenter.attachWindow(this);
        } else {
            XLog.e("activity:"+this.getClass().getName()+"没有实现IBaseView");
            throw new RuntimeException(this.getClass().getName()+"没有实现IBaseView");
        }
    }

    /**
     * 生成对应的Presenter
     * @return
     */
    P createNewPresenter() {
        ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
        Class <P> entityClass = (Class <P>) pt.getActualTypeArguments()[0];

        return Kits.Reflect.createInstance(entityClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        canceledRequests();
        if (presenter != null) {
            presenter.detachWindow();
        }
    }

}
