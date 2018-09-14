package com.example.newtest.contract;

import com.example.newtest.base.IBaseModel;
import com.example.newtest.base.IBasePresenter;
import com.example.newtest.base.IBaseView;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.bean.SystemUpdateBean;
import com.example.newtest.common.APPClientParam;
import com.example.newtest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/7/11.
 */

public interface MainActivityContract {
    interface Model extends IBaseModel {
        void getAndroidSystemUpdateData(APPClientParam apm, OkHttpClientManager.ResultCallback<SystemUpdateBean> callback);
    }

    interface View extends IBaseView {


        void initNavigation();

    }

    interface Presenter extends IBasePresenter{

        void checkForUpdateSystem();

        void showFragment(int index);
    }
}
