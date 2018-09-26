package com.example.newtest.design.adapter;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public abstract class AbstractDataAdapter<T,O> {

    /**
     * 数据转化
     * @param <T>
     * @return
     */
    public abstract  <T> T oToT(O o);
}
