package com.example.mark.mvptest.common;

/**
 * Created by Mark on 2018/4/3.
 */

public interface IModelDataDeal<T> {
    void saveCache(String s);
    T dealData(String s);
    void doException(String s);
    void doFailed(String s);
    void doNormal(String s);
}
