package com.example.mark.mvptest.hpcontract;

import com.example.mark.mvptest.LoginInfo;
import com.example.mark.mvptest.UserData;
import com.example.mark.mvptest.base.BaseModel;
import com.example.mark.mvptest.base.IBaseModel;

/**
 * Created by Mark on 2018/4/5.
 */

public interface LoginContract {
    interface Model extends IBaseModel {

        @Override
        void getData();//获取登录信息
    }

    interface View {
    }

    interface Presenter {
    }
}
