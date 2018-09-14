package com.example.newtest.presenter;

import android.support.annotation.MainThread;

import com.example.newtest.base.BasePresenter;
import com.example.newtest.contract.MainListContract;
import com.example.newtest.model.MainListModelImp;

import java.util.logging.Handler;

/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public class MainListPresenterImp extends BasePresenter<MainListModelImp,MainListContract.View> implements MainListContract.Presenter {
    @Override
    public void getList() {

        view.showLoading();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideLoading();
                view.showList( module.getList());
            }
        },3000);


    }
}
