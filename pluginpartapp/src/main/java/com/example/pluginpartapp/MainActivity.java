package com.example.pluginpartapp;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

/**
 * @author Mark
 * @create 2018/9/29
 * @Describe
 */

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that,"插桩成功",0).show();
            }
        });
    }
}
