package com.example.mark.mvptest;

/**
 * Created by Mark on 2018/4/2.
 */

public interface IRequestView<T> {
   void showLoading();
   void hideLoading();
   void showFailureMessage(String msg);
   void showErrorMessage();
   void showData(T t);
}
