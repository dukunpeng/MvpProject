package com.example.newtest.model;

import com.example.newtest.base.BaseModel;
import com.example.newtest.bean.UserData;
import com.example.newtest.contract.MainListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public class MainListModelImp extends BaseModel implements MainListContract.Model {
    @Override
    public List<UserData> getList() {
        List<UserData> list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            UserData userData = new UserData();
            userData.setRealName("name"+i);
            list.add(userData);
        }
        return list;
    }
}
