package com.example.newtest.model;

import com.example.newtest.base.BaseModelImp;
import com.example.newtest.bean.CommonBannerBean;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.contract.HomeContract;
import com.example.newtest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/7/13.
 */

public class HomeModelImp extends BaseModelImp implements HomeContract.Model {
    @Override
    public void getBannerList(APPClientParam apm, OkHttpClientManager.ResultCallback<CommonBannerBean> callback) {

    }
}
