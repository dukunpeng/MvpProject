package com.example.newtest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;
import com.example.newtest.window.SingleLoadingDialog;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Mark on 2018/4/7.
 */

public abstract class AbstractMvpBaseActivity<P extends BasePresenter> extends AbstratBaseActivity implements IBaseView {


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
    public void showError() {

    }

    @Override
    public void showLoading() {
        SingleLoadingDialog.getInstance().showLoading();
    }

    @Override
    public void hideLoading() {
        SingleLoadingDialog.getInstance().hideLoad();
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
