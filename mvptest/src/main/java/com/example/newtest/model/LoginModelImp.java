package com.example.newtest.model;

import com.example.newtest.base.AbstractBaseModelImp;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.bean.UserData;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.common.HttpConfig;
import com.example.newtest.contract.LoginContract;
import com.example.newtest.request.OkHttpClientManager;
import com.example.newtest.tyjk.ApmToJsonUtil;
import com.example.newtest.tyjk.TYBaseBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mark on 2018/4/7.
 */

public class LoginModelImp extends AbstractBaseModelImp implements LoginContract.Model {

    @Override
    public void doLogin(APPClientParam apm, final OkHttpClientManager.ResultCallback<LoginInfo> callback) {
        // getData(apm, HttpConfig.METHOD_UNIFY_USER_LOGIN,callback);
        doRequest(apm,HttpConfig.METHOD_UNIFY_USER_LOGIN,callback);
    }

    @Override
    public void getUserInfo(APPClientParam apm, OkHttpClientManager.ResultCallback<UserData> callback) {
        //  getData(apm,HttpConfig.METHOD_UNIFY_USER_LOGIN,callback);
        getDataObservable(ApmToJsonUtil.apmToJsonParam(apm, HttpConfig.METHOD_UNIFY_USER_LOGIN))
                .subscribeOn(Schedulers.io()) // 指定被观察者的操作在io线程中完成
                .observeOn(AndroidSchedulers.mainThread()) // 指定观察者接收到数据，然后在Main线程中完成
                .map(new Func1<TYBaseBean, UserData>() {
                    @Override
                    public UserData call(TYBaseBean bean) {
                        return null;
                    }
                }).subscribe(new Action1<UserData>() {
            @Override
            public void call(UserData userData) {

            }
        });


    }
   /* @Override
    public void getData(APPClientParam apm,String method ,OkHttpClientManager.ResultCallback callback) {
//        String p = Kits.Gsons.instance().toJson(Director.createBuilder(new UploadBeanBuilder()
//                .initDefaultBean(Kits.Gsons.instance().toJson(apm),method)
//                .fromType(Builder.TYPE_NORMAL))
//                .builder());
//        getDataCall(p);
        try {
            BaseModel.class.newInstance().method(method).params(apm).execute(callback);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public rx.Observable<TYBaseBean> getDataObservable(String json) {
        return null;
    }

    @Override
    public Call<TYBaseBean> getDataCall(final String json) {
        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(20));//线程池
        dispatcher.setMaxRequests(20);//最大的请求数量
        dispatcher.setMaxRequestsPerHost(1);//主机同一个时间，最大的请求数量

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(100,30, TimeUnit.SECONDS))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://mp.hepandai.com/Base/")
                .client(okHttpClient)
                .build();


        IBaseModel iBaseModel = retrofit.create(IBaseModel.class);
        Call call = iBaseModel.getDataCall(json);
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
    }*/


   /* @POST("InvokeWS")
    Observable<LoginInfo> getLoginInfo(@Query("str")String json);*/
}
