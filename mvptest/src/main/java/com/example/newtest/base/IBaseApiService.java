package com.example.newtest.base;

import com.example.newtest.tyjk.TYBaseBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mark on 2018/7/11.
 */

public interface IBaseApiService {
    @POST("InvokeWS")
    rx.Observable<TYBaseBean> getDataObservable(@Query("str")String json);
    @POST("InvokeWS")
    Call<TYBaseBean> getDataCall(@Query("str")String json);
}
