package com.example.newtest.presenter;

import com.example.newtest.base.BasePresenter;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.bean.UserData;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.contract.LoginContract;
import com.example.newtest.model.LoginModelImp;
import com.example.newtest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/4/7.
 */

public class LoginPresenterImp extends BasePresenter<LoginModelImp,LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenterImp(){
    }
/*    public LoginPresenterImp(LoginView loginView) {
        super(loginView);
        attachWindow( new LoginModelImp(),loginView);
    }*/


    @Override
    public void doLogin(APPClientParam apm) {

        if (module!=null)
        module.doLogin(apm, new OkHttpClientManager.ResultCallback<LoginInfo>(this) {
            @Override
            public void onSuccess(LoginInfo response) {

                if (isViewAttached())
                view.showData(response);
            }

            @Override
            public void onFailure(Exception e) {
                if (isViewAttached())
                view.showError();
            }

            @Override
            public void onStart() {
                if (isViewAttached())
                view.showLoading();
            }

            @Override
            public void onComplete() {
                if (isViewAttached())
                view.hideLoading();
            }
        });

    }

    @Override
    public void getUserInfo(APPClientParam apm) {
        if (module!=null)
            module.getUserInfo(apm, new OkHttpClientManager.ResultCallback<UserData>() {
                @Override
                public void onSuccess(UserData response) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
    }
}
