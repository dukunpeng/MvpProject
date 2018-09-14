package com.example.newtest.net;

/**
 * @author Mark
 * @create 2018/8/30
 * @Describe
 */
public class Status {
    /**
     * 网络可用的回调
     */
    public static final int NET_AVAILABLE = 10;
    /**
     * 网络丢失的回调
     */
    public static final int NET_LOST = 11;
    /**
     * 在网络失去连接的时候回调，但是如果是一个生硬的断开，他可能不回调
     */
    public static final int NET_LOSING = 12;
    /**
     * 没有找到可用的网络时进行回调
     */
    public static final int NET_UNAVAILABLE = 13;
}
