package com.example.newtest.request;

import android.os.Handler;
import android.os.Looper;

import com.example.newtest.base.BasePresenter;
import com.example.newtest.log.XLog;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.mark.request.LimitLinkedHashMap;
import com.mark.request.LimitQueue;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

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
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }


    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, Param... params) throws IOException
    {
        Response response = _post(url, params);
        return response.body().string();
    }

    private void deliveryResult(final ResultCallback callback, Request request)
    {


        callback.onStart();
        Call call = mOkHttpClient.newCall(request);
        callSchedule.addCall(call);
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
                    callback.onComplete();
                    final String string = response.body().string();
                    XLog.json(string);
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
    public static abstract class ResultCallback<T>
    {
         public Type mType;

        BasePresenter presenter;
        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }
        public ResultCallback(BasePresenter presenter)
        {
            this.presenter = presenter;
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

            if (presenter!=null&&presenter.view!=null){
                presenter.view.showLoading();
            }
        }
        /**
         * 请求数据失败，指在请求网络API接口请求方式时，出现无法联网、
         * 缺少权限，内存泄露等原因导致无法连接到请求数据源。
         */
        public void onError(){

        }
        /**
         * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做处理，通常做网络
         * 请求时可以在此处隐藏“正在加载”的等待控件
         */
        public void onComplete(){
            if (presenter!=null&&presenter.view!=null){
                presenter.view.hideLoading();
            }
        }

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
    public static class CallSchedule extends LinkedHashMap<Integer,Call> implements com.mark.request.OkHttpClientManager.CallScheduleDeal {
        private final int MAX_CACHE_COUNT = 4;
      //  private final int MAX_CALL_COUNT = 10;
        LimitLinkedHashMap<Integer,Call> cacheContainer = new LimitLinkedHashMap<Integer,Call>(MAX_CACHE_COUNT);
       // LimitQueue<Call> queueContainer = new LimitQueue<>(MAX_CALL_COUNT);
        private int storingKey = -1;

        private int getAutoSortStoringKey(){
            return ++storingKey;
        }
        /***
         * 将接下来的call打上可以被取消的标签，等待call被加入队列的时候，直接抓取保存在缓存容器cacheContainer里面

         */
        @Override
        public synchronized int setNextCallCanCanceledTag() {
            return getAutoSortStoringKey();
        }


        /**
         * 实际发送请求前，将call对象加入队列
         *
         * @param call
         */
        @Override
        public synchronized void addCall(Call call) {
            if (storingKey>-1){
                cacheContainer.put(storingKey,call);
            }
          //  queueContainer.offer(call);
        }

        /**
         * 找到对应call结束掉请求
         *
         * @param key
         */
        @Override
        public void cancelCallWithKey(int key) {

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

           // queueContainer.clear();
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
}