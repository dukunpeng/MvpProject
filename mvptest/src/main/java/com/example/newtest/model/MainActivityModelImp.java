package com.example.newtest.model;

import com.example.newtest.base.AbstractBaseModelImp;
import com.example.newtest.bean.SystemUpdateBean;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.common.HttpConfig;
import com.example.newtest.contract.MainActivityContract;
import com.example.newtest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/7/11.
 */

public class MainActivityModelImp extends AbstractBaseModelImp implements MainActivityContract.Model {
    @Override
    public void getAndroidSystemUpdateData(APPClientParam apm, OkHttpClientManager.ResultCallback<SystemUpdateBean> callback) {
        doRequest(apm, HttpConfig.METHOD_ANDROID_SYS_UPDATE_M,callback);
    }
}
