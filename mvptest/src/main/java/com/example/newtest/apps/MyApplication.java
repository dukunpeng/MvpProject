package com.example.newtest.apps;

import android.app.Application;

/**
 * @author Mark
 * @create 2018/9/13
 * @Describe
 */

public class MyApplication extends Application {

    public static boolean isNetAvailable;
    private static MyApplication instance;
    public static MyApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
