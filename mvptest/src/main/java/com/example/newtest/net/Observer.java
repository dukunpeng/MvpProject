package com.example.newtest.net;

import android.net.NetworkInfo;

/**
 * 网络观察者抽象
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public interface Observer {
    /**
     * 网络变化
     */
    void netUpdate(NetworkInfo networkInfo, int state);
}
