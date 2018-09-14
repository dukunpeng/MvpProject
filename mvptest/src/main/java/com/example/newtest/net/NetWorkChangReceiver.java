package com.example.newtest.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.newtest.apps.MyApplication;
import com.example.newtest.log.LogUtils;


/**
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public class NetWorkChangReceiver extends BroadcastReceiver {

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "数据网络";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            LogUtils.i("NetWorkChangReceiver", "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 请注意这里会有一个版本适配bug，所以请在这里添加非空判断
            if (connectivityManager != null) {

                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                //获取联网状态的NetworkInfo对象

                if (info != null) {
                    //如果当前的网络连接成功并且网络连接可用
                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            MyApplication.isNetAvailable = true;
                            LogUtils.i("NetWorkChangReceiver", getConnectionType(info.getType()) + "连上");
                            NetWorkObservable.getInstance().notify(info,Status.NET_AVAILABLE);
                        }
                    } else {
                        MyApplication.isNetAvailable = false;
                        LogUtils.i("NetWorkChangReceiver", getConnectionType(info.getType()) + "断开");
                        NetWorkObservable.getInstance().notify(info,Status.NET_UNAVAILABLE);
                    }
                }else{
                    MyApplication.isNetAvailable = false;
                    LogUtils.i("NetWorkChangReceiver", "----------" + "断开");
                    NetWorkObservable.getInstance().notify(info,Status.NET_UNAVAILABLE);
                }

            }

        }
    }
}