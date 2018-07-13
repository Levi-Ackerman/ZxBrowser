package com.woyou.exit;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.exeception.crash.IExitObservable.java
 * <p> 被观察者
 * Creation     : 2018/6/27
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public interface IExitObservable {

    void exit();

    void subscribe(IExitSubscriber subscriber);

    void unsubscribe(IExitSubscriber subscriber);

    boolean isSubscribed(IExitSubscriber subscriber);
}
