package com.example.newtest.contract;

import android.content.Context;
import android.view.View;

import com.example.newtest.base.IBaseModel;
import com.example.newtest.base.IBasePresenter;
import com.example.newtest.base.IBaseView;

/**
 * @author Mark
 * @create 2018/9/28
 * @Describe
 */

public interface LearnContract {
    interface Model extends IBaseModel{
    }

    interface View extends IBaseView{
        void addView();
        Context context();
    }

    interface Presenter extends IBasePresenter{
        android.view.View[] createViews();
    }
}
