package com.example.mark.mvptest.hpmodel;

import com.example.mark.mvptest.LoginInfo;
import com.example.mark.mvptest.UserData;
import com.example.mark.mvptest.base.BaseModel;
import com.example.mark.mvptest.hpcontract.LoginContract;
import com.example.mark.mvptest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/4/5.
 */

public class LoginModel extends BaseModel<LoginInfo> implements LoginContract.Model {







    @Override
    public void getData() {

    }

    @Override
    public void execute(OkHttpClientManager.ResultCallback<LoginInfo> callback) {
        requestPostAPI(callback);
    }
}
