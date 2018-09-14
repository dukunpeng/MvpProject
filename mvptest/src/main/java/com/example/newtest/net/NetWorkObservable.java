package com.example.newtest.net;

import android.net.Network;
import android.net.NetworkInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public class NetWorkObservable implements Observable {

    private NetWorkObservable(){}
    private static NetWorkObservable instance;
    public static NetWorkObservable getInstance(){
        if (instance==null){
            synchronized (NetWorkObservable.class){
                if (instance==null){}
                instance = new NetWorkObservable();
            }
        }
        return instance;
    }

    private Set<Observer> observerSet ;

    @Override
    public void register(Observer observer) {

        if (observerSet==null){
            observerSet = new HashSet<>();
        }
        observerSet.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {

        observerSet.remove(observer);
    }

    @Override
    public void notify(NetworkInfo networkInfo, int state) {

        if (observerSet!=null){
            for (Observer observer:observerSet) {
                observer.netUpdate(networkInfo,state);
            }
        }
    }
    public void recycle(){
        observerSet.clear();
        observerSet = null;
    }
}
