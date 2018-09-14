package com.example.newtest.base;


import com.example.newtest.common.APPClientParam;
import com.example.newtest.kit.Kits;
import com.example.newtest.log.XLog;
import com.example.newtest.request.OkHttpClientManager;
import com.example.newtest.tyjk.Builder;
import com.example.newtest.tyjk.Director;
import com.example.newtest.tyjk.UploadBeanBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.example.newtest.common.Config.HTTP_PARAM_KEY;
import static com.example.newtest.common.Config.HTTP_UAT_BASE_URL;

/**
 * Created by Mark on 2018/4/4.
 */

public  class BaseModel<T>  {
    //数据请求参数
    protected Map<String,String> mParams;
    //名义上的接口名
    protected String code;
    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public  BaseModel params(Map args){
        mParams = args;
        return this;
    }
    public BaseModel<T> method(String method){
        code = method;
        return this;
    }
    /**
     * 设置数据请求参数
     */
    public  BaseModel<T> params(APPClientParam apm){
        Map<String,String> map = new HashMap<String,String>();
        String p = Kits.Gsons.instance().toJson(Director.createBuilder(new UploadBeanBuilder()
                .initDefaultBean(Kits.Gsons.instance().toJson(apm),code)
                .fromType(Builder.TYPE_NORMAL))
                .builder());
        XLog.json(p);
        map.put(HTTP_PARAM_KEY,p );
        mParams = map;
        return this;
    }
    // 添加Callback并执行数据请求
    // 具体的数据请求由子类实现
    public  void execute(OkHttpClientManager.ResultCallback<T> callback){
        requestPostAPI(callback);
    };
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
       requestPostAPI(HTTP_UAT_BASE_URL,callback);
    }
}