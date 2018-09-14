package com.example.newtest.base;

import com.example.newtest.common.APPClientParam;
import com.example.newtest.common.HttpConfig;
import com.example.newtest.request.OkHttpClientManager;
import com.example.newtest.tyjk.TYBaseBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mark on 2018/4/7.
 */

public interface IBaseModelImp {

    void getData(APPClientParam apm, String method, OkHttpClientManager.ResultCallback callback);
    void getData(APPClientParam apm, HttpConfig method, OkHttpClientManager.ResultCallback callback);
    <T> void  doRequest(APPClientParam apm, HttpConfig method ,final OkHttpClientManager.ResultCallback<T> callback);
    <T> void  doRequest(APPClientParam apm, String method ,final OkHttpClientManager.ResultCallback<T> callback);
    rx.Observable<TYBaseBean> getDataObservable(String json);
    Call<TYBaseBean> getDataCall(String json);
}
