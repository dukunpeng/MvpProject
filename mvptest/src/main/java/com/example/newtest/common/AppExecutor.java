package com.example.newtest.common;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark
 * @create 2018/8/27
 * @Describe
 */

public final class AppExecutor {
    private static ExecutorService EXECUTORS_INSTANCE;
    private static int threadNumber = 2;

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (AppExecutor.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE =new ThreadPoolExecutor(threadNumber, threadNumber+2,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>());;
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }
    public static void runOnThread(Runnable runnable) {
        try{
            getExecutor().execute(runnable);
        }catch (Exception e){
            shutDown();
        }
    }
    public static void shutDown(){
        if (EXECUTORS_INSTANCE!=null){
            EXECUTORS_INSTANCE.shutdown();
        }
    }
}

