package cn.uc.gamesdk.background.task;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.thread.TaskMode.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public enum TaskMode {
    UI, //主线程，UI线程
    BACKGROUND, //串行处理不耗时的后台操作，如简单的计算任务（只操作内存数据）
    NETWORK, //并行执行耗时很长的IO任务，比如网络
    FILE, //串行执行耗时不那么长的IO任务，比如文件读写
}
