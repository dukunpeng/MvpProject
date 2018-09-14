package com.example.newtest.base;

import com.example.newtest.kit.Kits;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Mark on 2018/4/9.
 * 创建这个基类是为了在BaseActivity拿到newP()的构造对象
 */

public class BasePresenter<M extends IBaseModel, V extends IBaseView> {

    public M module;

    /**
     * 生成对应的Model
     * @return
     */
    M createNewModel() {
        Class<M> entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return Kits.Reflect.createInstance(entityClass);
    }

    public V view;

    final protected void attachModule() {
        module = createNewModel();
    }

    final protected void attachWindow(V v) {
        this.view = v;
    }

    final protected void detachWindow() {
        this.module = null;
        this.view = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return view != null;
    }
}
