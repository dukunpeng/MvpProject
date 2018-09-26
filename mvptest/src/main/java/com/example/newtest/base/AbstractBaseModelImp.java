package com.example.newtest.base;

import com.example.newtest.apps.MyApplication;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.common.HttpConfig;
import com.example.newtest.kit.Kits;
import com.example.newtest.log.LogUtils;
import com.example.newtest.log.XLog;
import com.example.newtest.request.OkHttpClientManager;
import com.example.newtest.request.RetrofitUtil;
import com.example.newtest.tyjk.ApmToJsonUtil;
import com.example.newtest.tyjk.TYBaseBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mark on 2018/7/11.
 */

public abstract class AbstractBaseModelImp implements IBaseModelImp
{
    /**
     * 传统方式请求网络
     * @param apm
     * @param method
     * @param callback
     */
    @Override
    public void getData(APPClientParam apm, String method , OkHttpClientManager.ResultCallback callback) {
        try {
            BaseModel.class.newInstance().method(method).params(apm).execute(callback);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传统方式请求，重载方法支持枚举类型的方法名
     * @param apm
     * @param method
     * @param callback
     */
    @Override
    public void getData(APPClientParam apm, HttpConfig method , OkHttpClientManager.ResultCallback callback) {
        getData(apm,method.getMethodName(),callback);
    }

    @Override
    public <T> void  doRequest(APPClientParam apm, HttpConfig method ,final OkHttpClientManager.ResultCallback<T> callback){
       doRequest(apm,method.getMethodName(),callback);
    }
    @Override
    public <T> void  doRequest(APPClientParam apm, String method ,final OkHttpClientManager.ResultCallback<T> callback){
        if (!MyApplication.isNetAvailable){
            LogUtils.e("AbstractBaseModelImp","net is unavilable");
            return;
        }
        callback.onStart();
        getDataObservable(ApmToJsonUtil.apmToJsonParam(apm, method))
                .subscribeOn(Schedulers.io()) // 指定被观察者的操作在io线程中完成
                .observeOn(AndroidSchedulers.mainThread()) // 指定观察者接收到数据，然后在Main线程中完成
                .map(new Func1<TYBaseBean,T >() {
                    @Override
                    public T call(TYBaseBean bean) {
                        if (bean!=null&&bean.getDoStatu()==1){
                            return Kits.Gsons.instance().fromJson(bean.getDoObject(),callback.mType);
                        }else{
                            callback.onError();
                            return null;
                        }
                    }
                }).subscribe(new Action1<T>() {
            @Override
            public void call(T t) {
                callback.onComplete();
                if (t!=null){
                    callback.onSuccess(t);
                }
            }
        });
    }

    /**
     *
     * @param json
     * @return
     */
    @Override
    public rx.Observable<TYBaseBean> getDataObservable(String json) {
        IBaseApiService iBaseApiService = RetrofitUtil.getRetrofit().create(IBaseApiService.class);
        rx.Observable<TYBaseBean> observable = iBaseApiService.getDataObservable(json);
        return observable;
    }

    /**
     *
     * @param json
     * @return
     */
    @Override
    public Call<TYBaseBean> getDataCall(final String json) {

        IBaseApiService iBaseApiService = RetrofitUtil.getRetrofit().create(IBaseApiService.class);
        Call call = iBaseApiService.getDataCall(json);
        if (!MyApplication.isNetAvailable){
            LogUtils.e("AbstractBaseModelImp","net is unavilable");
            return call ;
        }
        call.enqueue(new Callback<TYBaseBean>() {
            @Override
            public void onResponse(Call<TYBaseBean> call, Response<TYBaseBean> response) {
                XLog.json(Kits.Gsons.instance().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<TYBaseBean> call, Throwable t) {

            }
        });


        return call;
    }

}
