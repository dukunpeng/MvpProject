package com.example.newtest.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;
import com.example.newtest.window.SingleLoadingDialog;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Mark on 2018/7/10.
 */

public abstract class AbstractMvpBaseFragment<P extends BasePresenter>  extends AbstractBaseFragment implements IBaseView {
    protected P presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMvp();
        return super.onCreateView(inflater,container,savedInstanceState);
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
            XLog.e("fragment:"+this.getClass().getName()+"没有实现IBaseView");
            throw new RuntimeException(this.getClass().getName()+"没有实现IBaseView");
        }
    }

    /**
     * 生成对应的Presenter
     * @return
     */
    P createNewPresenter() {
        Class <P> entityClass = (Class <P>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachWindow();
        }
    }
}
