package com.example.mark.mvptest.base;

import com.example.mark.mvptest.request.OkHttpClientManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mark on 2018/4/4.
 */

public abstract class BaseModel<T>  {
    //数据请求参数
    protected Map<String,String> mParams;
    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public  BaseModel params(Map args){
        mParams = args;
        return this;
    }
    /**
     * 设置数据请求参数
     */
    public  BaseModel<T> params(APPClientParam apm){
        Map<String,String> map = new HashMap<String,String>();
        map.put("str", new Gson().toJson(apm));
        mParams = map;
        return this;
    }
    // 添加Callback并执行数据请求
    // 具体的数据请求由子类实现
    public abstract void execute(OkHttpClientManager.ResultCallback<T> callback);
    // 执行Get网络请求，此类看需求由自己选择写与不写
    protected void requestGetAPI(String url,OkHttpClientManager.ResultCallback<T> callback){
        //这里写具体的网络请求
        OkHttpClientManager.getInstance().getAsyn(url,callback);
    }
    // 执行Post网络请求，此类看需求由自己选择写与不写
    protected void requestPostAPI(String url,  OkHttpClientManager.ResultCallback<T> callback){
        //这里写具体的网络请求
        OkHttpClientManager.getInstance().postAsyn(url,callback,mParams);
    }
    // 执行Post网络请求，此类看需求由自己选择写与不写
    protected void requestPostAPI( OkHttpClientManager.ResultCallback<T> callback){
        //这里写具体的网络请求
       requestPostAPI("https://mp.hepandai.com/Base/InvokeWS",callback);
    }
}