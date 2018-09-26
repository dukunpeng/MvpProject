package com.example.newtest.design.adapter;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class CN220VImp implements CN220V {
    @Override
    public void connect() {
        System.out.println("220v接通电源，开始工作。。。。。。");
    }
}
