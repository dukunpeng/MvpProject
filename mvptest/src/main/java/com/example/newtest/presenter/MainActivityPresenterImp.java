package com.example.newtest.presenter;

import com.example.newtest.base.BasePresenter;
import com.example.newtest.bean.LoginInfo;
import com.example.newtest.bean.SystemUpdateBean;
import com.example.newtest.contract.MainActivityContract;
import com.example.newtest.model.MainActivityModelImp;
import com.example.newtest.request.OkHttpClientManager;

/**
 * Created by Mark on 2018/7/11.
 */

public class MainActivityPresenterImp extends BasePresenter<MainActivityModelImp,MainActivityContract.View> implements MainActivityContract.Presenter {
    @Override
    public void checkForUpdateSystem() {
        if (module!=null){
            module.getAndroidSystemUpdateData(null, new OkHttpClientManager.ResultCallback<SystemUpdateBean>() {
                @Override
                public void onSuccess(SystemUpdateBean response) {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    @Override
    public void showFragment(int index) {

    }
}
