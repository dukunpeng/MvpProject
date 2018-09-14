package com.example.mark.mvptest;

import com.example.mark.mvptest.base.BaseCallBack;
import com.example.mark.mvptest.base.BaseModel;
import com.example.mark.mvptest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/4/4.
 */

public class LoginDataModel extends BaseModel<LoginInfo>   {
    @Override
    public void execute(OkHttpClientManager.ResultCallback<LoginInfo> callback) {

        requestPostAPI(callback);
    }
}
