package com.example.newtest.net;

import android.net.Network;
import android.net.NetworkInfo;

/**
 * 抽象被观察网络
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public interface Observable {


    void register(Observer observer);

    void unRegister(Observer observer);

    void notify(NetworkInfo networkInfo, int state);

}
