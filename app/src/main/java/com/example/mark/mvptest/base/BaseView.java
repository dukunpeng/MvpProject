package com.example.mark.mvptest.base;

import android.content.Context;

/**
 * Created by Mark on 2018/4/2.
 */

public interface BaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();
    /**
     * 关闭正在加载view
     */
    void hideLoading();
    /**
     * 显示显示
     * @param data
     */
    void showData(Object data);
    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);
    /**
     * 显示提示
     * @param msg
     */
    void showDialog(String msg);
    /**
     * 显示请求错误提示
     */
    void showErr();
    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();

}
