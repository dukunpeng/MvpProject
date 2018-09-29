package com.example.newtest.presenter;

import android.view.View;

import com.example.newtest.base.BasePresenter;
import com.example.newtest.bean.UserData;
import com.example.newtest.contract.LearnContract;
import com.example.newtest.model.LearnModelImp;
import com.example.newtest.test.mmkv.MmkvUse;
import com.example.newtest.view.ItemTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Mark
 * @create 2018/9/28
 * @Describe
 */

public class LearnPresenterImp extends BasePresenter<LearnModelImp,LearnContract.View> implements LearnContract.Presenter {
    UserData userData = new UserData();
    @Override
    public View[] createViews() {
        View[] views = new View[5];
        views[0] = new ItemTextView(view.context(), "mmkv测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MmkvUse().useMmkv();
            }
        });
        views[1] = new ItemTextView(view.context(), "SharedPrefrence测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MmkvUse().useSharedPrefrence();
            }
        });

        views[2] = new ItemTextView(view.context(), "HashTable测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData = new UserData();
                userData.setRealName("mark");
                Hashtable hashTable = new Hashtable();
                hashTable.put("key",userData);
                userData.setRealName("mark2");

                userData =new UserData();
                userData.setRealName("mark3");

                System.out.println("key:"+((UserData)hashTable.get("key")).getRealName());
            }
        });
        views[3] = new ItemTextView(view.context(), "hashMap测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData = new UserData();
                userData.setRealName("mark");
                HashMap hashMap = new HashMap();
                hashMap.put("key",userData);
                userData.setRealName("mark2");

                userData =new UserData();
                userData.setRealName("mark3");

                System.out.println("key:"+((UserData)hashMap.get("key")).getRealName());
            }
        });
        views[4] = new ItemTextView(view.context(), "List测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData = new UserData();
                userData.setRealName("mark");
                List list = new ArrayList();
                list.add(userData);
                userData.setRealName("mark2");

                userData =new UserData();
                userData.setRealName("mark3");

                System.out.println("key:"+((UserData)list.get(0)).getRealName());
            }
        });
        return views;
    }
}
