package com.woyou.util;

import java.util.Collection;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.CollectionUtil.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class CollectionUtil {
    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> col) {
        return col == null || col.size() == 0;
    }
}
