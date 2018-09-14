package com.example.mark.mvptest.router;

import android.app.Activity;

/**
 * Created by Mark on 2018/4/2.
 */

public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void onNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
