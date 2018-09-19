package com.example.newtest.model;

import com.example.newtest.base.BaseModelImp;
import com.example.newtest.bean.CommonBannerBean;
import com.example.newtest.bean.UserData;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.contract.HomeContract;
import com.example.newtest.request.OkHttpClientManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2018/7/13.
 */

public class HomeModelImp extends BaseModelImp implements HomeContract.Model {
    @Override
    public void getBannerList(APPClientParam apm, OkHttpClientManager.ResultCallback<CommonBannerBean> callback) {

    }

    @Override
    public List<String> getList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            switch (i){
                case 0:
                    list.add("系统可为APP分配的最大内存"+Runtime.getRuntime().maxMemory());
                    break;
                case 1:
                    list.add("APP当前所分配的内存heap空间大小"+Runtime.getRuntime().totalMemory());
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }

        }
        return list;
    }


}
