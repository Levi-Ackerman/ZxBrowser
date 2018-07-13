package cn.uc.gamesdk.background.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import cn.uc.gamesdk.background.log.Tags;
import cn.uc.gamesdk.background.log.UCLog;
import lee.top.taskpool.BuildConfig;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.task.BGThreadFactory.java
 * 后台线程池规范命名和设置统一UncaughtException
 * <p> 包内可见，不要修改可见性
 * Creation     : 2018/6/29
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
/*包内可见*/class BGThreadFactory implements ThreadFactory {
    private static final String TAG = Tags.TAG_MODULE_TASK_POOL;
    private final AtomicInteger mCount = new AtomicInteger(1);
    private String mThreadNamePrefix;
    private Thread.UncaughtExceptionHandler mCrashHandler;

    BGThreadFactory(String threadNamePrefix, Thread.UncaughtExceptionHandler crashHandler) {
        mThreadNamePrefix = threadNamePrefix;
        mCrashHandler = crashHandler;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, mThreadNamePrefix + "#" + mCount.getAndIncrement());
        thread.setUncaughtExceptionHandler(mCrashHandler);
        if (BuildConfig.DEBUG) {
            UCLog.debug(TAG, " new thread in thread pool :", thread.getName());
        }
        return thread;
    }
}
