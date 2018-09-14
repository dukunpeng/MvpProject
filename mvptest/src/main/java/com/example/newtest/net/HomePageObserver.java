package com.example.newtest.net;

import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.newtest.apps.MyApplication;
import com.example.newtest.common.MessageEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public class HomePageObserver implements Observer {

    @Override
    public void netUpdate(NetworkInfo networkInfo, int state) {

        if (state==Status.NET_AVAILABLE){

            EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_NET_VIEW_CODE,1));
          //  Toast.makeText(MyApplication.getInstance(),"有数据了",Toast.LENGTH_SHORT).show();
        }else{
            EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_NET_VIEW_CODE,0));
             Toast.makeText(MyApplication.getInstance(),"没了",Toast.LENGTH_SHORT).show();

        }

    }
}
