package com.woyou.util;

import space.woyou.zxutil.BuildConfig;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.CheckUtil.java
 * <p>
 * Creation     : 2018/6/29
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class CheckUtil {
    public static void mustOk(boolean condition){
        mustOk(condition,"");
    }

    public static void mustOk(boolean condition, String msg){
        if (BuildConfig.DEBUG && !condition)  {
            fail(msg);
        }
    }

    public static void fail(){
        fail("");
    }

    public static void fail(String msg) {
        if (BuildConfig.DEBUG) {
            throw new Error(msg);
        }
    }
}
