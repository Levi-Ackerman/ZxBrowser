package com.woyou.util;

import android.text.TextUtils;
import android.util.Log;

import space.woyou.zxutil.BuildConfig;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.log.UCLog.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class ZxLog {

    public static void error(Throwable th, Object... obj){
        error(ExceptionUtil.getStackTrace(th),obj);
    }

    public static void error(Object... obj){
        if (!BuildConfig.DEBUG) {
            return ;
        }

        String log = format(obj);
        if (TextUtils.isEmpty(log)){
            return ;
        }

        Log.e(getTag(),log);
    }

    public static void info(Object... obj){
        if (!BuildConfig.DEBUG) {
            return ;
        }

        String log = format(obj);
        if (TextUtils.isEmpty(log)){
            return ;
        }

        Log.i(getTag(),log);
    }

    public static void debug(Object... obj){
        if (!BuildConfig.DEBUG) {
            return ;
        }

        String log = format(obj);
        if (TextUtils.isEmpty(log)){
            return ;
        }

        Log.d(getTag(),log);
    }

    public static void warn(Throwable th, Object... obj){
        warn(ExceptionUtil.getStackTrace(th),obj);
    }

    public static void warn(Object... obj){
        if (!BuildConfig.DEBUG) {
            return ;
        }

        String log = format(obj);
        if (TextUtils.isEmpty(log)){
            return ;
        }

        Log.w(getTag(),log);
    }

    private static String format(Object[] obj) {
        if (!CollectionUtil.isEmpty(obj)){
            StringBuilder sb = new StringBuilder();
            for (Object o : obj) {
                sb.append(o);
            }
            return sb.toString();
        }
        return null;
    }

    private static String getTag() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        if (stackTraces.length > 0) {
            StackTraceElement stackTrace;
            if (stackTraces.length >= 4) {
                stackTrace = stackTraces[4];
            } else {
                stackTrace = stackTraces[stackTraces.length - 1];
            }
            return stackTrace.getClassName() +' '+
                    stackTrace.getMethodName() +' '+
                    stackTrace.getLineNumber() + " #";
        }
        return "";
    }
}
