package com.example.newtest.contract;

import com.example.newtest.base.IBaseModel;
import com.example.newtest.base.IBasePresenter;
import com.example.newtest.base.IBaseView;
import com.example.newtest.bean.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public interface MainListContract {
    interface Model extends IBaseModel {
       List<UserData> getList();
    }

    interface View extends IBaseView{
        void showList(List<UserData> list);
        HashMap<String,String> getParam();
    }

    interface Presenter extends IBasePresenter{
        void getList();
    }
}
