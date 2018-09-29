package com.mark.mainapp;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
        PluginManager.getInstance().setContext(this);
    }

    //事件绑定load
    private void load() {
        /**
         * 事先放置到SD卡根目录的plugin.apk
         * 现实场景中是有服务端下发
         */
        File file1 = new File(Environment.getExternalStorageDirectory().getPath()/* + "/plugin_test"*/, "ttt.txt");

        if (!file1.exists()){
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fiel1=="+file1.getAbsolutePath());

        //为了防止apk被清理掉，可以将后缀名自定义，下载的时候存的后缀名和此处读的应保持一致
        File file = new File(Environment.getExternalStorageDirectory().getPath()/* + "/plugin_test"*/, "plugin.apk");
        PluginManager.getInstance().loadPath(file.getAbsoluteFile().getPath());
    }

    //事件绑定click
    private void click() {
        /**
         * 点击跳往插件app的activity，一律跳转到PRoxyActivity
         */
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", PluginManager.getInstance().getEntryName());
        startActivity(intent);

    }
}