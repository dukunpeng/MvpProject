package com.example.newtest.common;

/**
 * @author Mark
 * @create 2018/8/18
 * @Describe
 */
public class MessageEvent {
    /**
     * 头像更换成功后回调
     */
    public static final int ME_UPDATE_AVATAR_CODE = 10;
    /**
     * 登出后的回调
     */
    public static final int LOGIN_OUT_SUCCEED_CODE = 100;
    /**
     * 登出后的回调
     */
    public static final int BLACK_LIST_SUCCEED_CODE = 200;

    /**
     * 刷新网路异常页面
     */
    public static final int UPDATE_NET_VIEW_CODE = 300;
    /**
     * 分组数据同步完成
     */
    public static final int FRINEND_DATA_SYNC_COMPLETE = 10000;

    public int code;
    public Object message;

    public MessageEvent(int code, Object message) {
        this.code = code;
        this.message = message;
    }

    public MessageEvent(int code) {
        this.code = code;
    }

    public MessageEvent setCode(int code) {
        this.code = code;
        return this;
    }

    public MessageEvent setMessage(Object message) {
        this.message = message;
        return this;
    }
}
