package com.woyou.util.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.json.Expose.java
 * <p> 注解为Expose，则该属性可以被json化
 * @see cn.uc.gamesdk.background.util.JsonUtil
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Expose {
}
