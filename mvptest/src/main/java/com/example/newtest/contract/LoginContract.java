package com.example.newtest.contract;

import com.example.newtest.base.IBaseModel;
import com.example.newtest.base.IBasePresenter;
import com.example.newtest.base.IBaseView;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.bean.UserData;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.request.OkHttpClientManager;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mark on 2018/4/7.
 */

public class LoginContract {
    public interface Model extends IBaseModel {
        void doLogin(APPClientParam apm, OkHttpClientManager.ResultCallback<LoginInfo> callback);
        void getUserInfo(APPClientParam apm, OkHttpClientManager.ResultCallback<UserData> callback);
       /* @POST("InvokeWS")
        Observable<LoginInfo> getLoginInfo(@Query("str")String json);*/
    }

    public  interface View extends IBaseView {
        void showRecycleList();
        void showBanner();
        void showData(Object data);
    }

    public interface Presenter extends IBasePresenter {
        void doLogin(APPClientParam apm);
        void getUserInfo(APPClientParam apm);
    }
}
