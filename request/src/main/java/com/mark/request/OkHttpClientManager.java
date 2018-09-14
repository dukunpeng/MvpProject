package com.mark.request;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 设计模式六大原则1单一职责原则
 设计模式六大原则2里氏替换原则
 设计模式六大原则3依赖倒置原则
 设计模式六大原则4接口隔离原则
 设计模式六大原则5迪米特法则
 设计模式六大原则6开闭原则
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    /******
     * 初始化基本配置
     * ******/
    private OkHttpClientManager()
    {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(5, TimeUnit.SECONDS);
        okHttpClient.readTimeout(5, TimeUnit.SECONDS);

        okHttpClient.cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return  cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        mOkHttpClient=okHttpClient.build();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /****
     * ***获取单例实例***
     * ******/
    public static OkHttpClientManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /***
     * 将map格式数据转成Param数组格式
     * @param params
     * @return
     */
    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries)
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * 对请求结果进行处理
     * @param callback
     * @param request
     */
    final private void deliveryResult(final ResultCallback callback, Request request)
    {
        final Call call =  mOkHttpClient.newCall(request);
        callSchedule.addCall(call);
        callback.onStart();
        call.enqueue(new Callback()
        {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callback,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try
                {
                    final String string = response.body().string();
                    if (callback.mType == String.class)
                    {
                        sendSuccessCallBack(callback,string);
                    } else
                    {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessCallBack(callback,o);
                    }

                } catch (Exception e)
                {
                    sendFailCallback(callback,e);
                }
            }
        });
    }
    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }



    //*************对外公布的方法************

    //异步get请求
    public static void getAsyn(String url, ResultCallback callback)
    {
        getInstance()._getAsyn(url, callback);
    }
    //异步的post请求
    public static void postAsyn(String url, final ResultCallback callback, Param... params)
    {
        getInstance()._postAsyn(url, callback, params);
    }

    //异步的post请求
    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        getInstance()._postAsyn(url, callback, params);
    }


    /**
     * 请求数据失败回调
     * @param callback
     * @param e
     */
    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    /**
     * 请求数据成功回调
     * @param callback
     * @param obj
     */
    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params)
    {
        if (params == null)
        {
            params = new Param[0];
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Param param : params)
        {
            formBodyBuilder.add(param.key, param.value);
        }
        RequestBody requestBody = formBodyBuilder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    /**
     * post请求参数类
     */
    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }
        String key;
        String value;
    }

    public  CallSchedule callSchedule = new CallSchedule();
    public static class CallSchedule extends LinkedHashMap<Integer,Call> implements CallScheduleDeal{
        private final int MAX_CACHE_COUNT =10;
        private final int MAX_CALL_COUNT = 10;
        LimitLinkedHashMap<Integer,Call> cacheContainer = new LimitLinkedHashMap<Integer,Call>(MAX_CACHE_COUNT);
        LimitQueue<Call> queueContainer = new LimitQueue<>(MAX_CALL_COUNT);
        private int storingKey = -1;

        private int getAutoSortStoringKey(){
            return storingKey++;
        }
        /***
         * 将接下来的call打上可以被取消的标签，等待call被加入队列的时候，直接抓取保存在缓存容器cacheContainer里面

         */
        @Override
        public int setNextCallCanCanceledTag() {
            return getAutoSortStoringKey();
        }


        /**
         * 实际发送请求前，将call对象加入队列
         *
         * @param call
         */
        @Override
        public  void addCall(Call call) {
            if (storingKey>-1){
                cacheContainer.put(storingKey,call);
            }
            queueContainer.offer(call);
        }

        /**
         * 找到对应call结束掉请求
         *
         * @param key
         */
        @Override
        public void cancelCallWithKey(int key) {

            //TODO 这里取消完请求以后，已改跟resultCall.onComplete()联系起来
            Call call = cacheContainer.get(key);
            if (call!=null&&call.isExecuted()&&!call.isCanceled()){
                call.cancel();
                cacheContainer.remove(key);
            }

        }

        /**
         * 清除掉调度器里面的calls
         */
        @Override
        public void clear() {

            queueContainer.clear();
        }

        /**
         * 将所有的call取消请求
         */
        @Override
        public void cancelAll() {
            for (Entry<Integer,Call> entry:
                 cacheContainer.entrySet()) {
                entry.getValue().cancel();
            }
        }


}

    public interface CallScheduleDeal{

        /***
         * 将接下来的call打上可以被取消的标签，等待call被加入队列的时候，直接抓取保存在缓存容器cacheContainer里面
         */
        int setNextCallCanCanceledTag();

        /**
         * 实际发送请求前，将call对象加入队列
         * @param call
         */
        void addCall(final Call call);

        /**
         *找到对应call结束掉请求
         * @param key
         */
        void cancelCallWithKey(final  int key);

        /**
         * 清除掉调度器里面的calls
         */
        void clear();

        /**
         * 将所有的call取消请求
         */
        void cancelAll();
    }

}