package com.woyou.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.ExceptionUtil.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class ExceptionUtil {
    public static String getStackTrace(Throwable e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        return baos.toString();
    }
}
