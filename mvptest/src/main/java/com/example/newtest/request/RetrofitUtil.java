package com.example.newtest.request;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newtest.common.Config.HTTP_UAT_BASE_URL_WITHOUT_METHODNAME;

/**
 * Created by Mark on 2018/7/11.
 */

public class RetrofitUtil {
    private static Retrofit instance;
    public static Retrofit getRetrofit(){
        if (instance==null){

            Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));//线程池
            dispatcher.setMaxRequests(20);//最大的请求数量
            dispatcher.setMaxRequestsPerHost(1);//主机同一个时间，最大的请求数量

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .connectionPool(new ConnectionPool(100,15, TimeUnit.SECONDS))
                    .build();

           instance = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HTTP_UAT_BASE_URL_WITHOUT_METHODNAME)
                    .client(okHttpClient)
                    .build();
        }

        return instance;
    }
}
