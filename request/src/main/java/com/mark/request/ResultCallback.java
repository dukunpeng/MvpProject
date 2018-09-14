package com.mark.request;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Mark on 2018/7/5.
 */

public  abstract class ResultCallback<T>
{
    Type mType;//返回的数据类型
    CallBackRealLife callBackRealLife;
    public ResultCallback(CallBackRealLife callBackRealLife)
    {
        this.callBackRealLife = callBackRealLife;
        mType = getSuperclassTypeParameter(getClass());
    }

    public ResultCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 请求成功回调
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败回调
     * @param e
     */
    public abstract void onFailure(Exception e);

    /**
     * 开始请求
     */
    public void onStart(){

        if (callBackRealLife!=null)
            callBackRealLife.onStart();
    }
    /**
     * 请求数据失败，指在请求网络API接口请求方式时，出现无法联网、
     * 缺少权限，内存泄露等原因导致无法连接到请求数据源。
     */
    public void onError(){

        if (callBackRealLife!=null)
            callBackRealLife.onError();
    }
    /**
     * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做处理，通常做网络
     * 请求时可以在此处隐藏“正在加载”的等待控件
     */
    public void onComplete(){
        if (callBackRealLife!=null)
            callBackRealLife.onComplete();

    }

}
