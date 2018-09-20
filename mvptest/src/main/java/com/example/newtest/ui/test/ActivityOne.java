package com.example.newtest.ui.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.newtest.R;
import com.example.newtest.apps.MyApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author Mark
 * @create 2018/9/19
 * @Describe
 */

public class ActivityOne extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },300000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
