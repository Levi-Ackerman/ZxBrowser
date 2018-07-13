package cn.uc.gamesdk.background.exeception.crash;

import android.os.HandlerThread;

import cn.uc.gamesdk.background.exit.IExitObservable;
import cn.uc.gamesdk.background.log.UCLog;
import cn.uc.gamesdk.background.stat.Stat;
import cn.uc.gamesdk.background.stat.bean.ContentNodeFactory;
import cn.uc.gamesdk.background.stat.bean.childnode.crash.CrashChildNode;
import cn.uc.gamesdk.background.stat.common.ContentNode;
import cn.uc.gamesdk.background.util.ExceptionUtil;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.ninegame.gamesdk.background.crash.BgUncaughtExceptionHandler.java
 * <p>
 * Creation     : 2018/6/20
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class BgUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler  {
    private IExitObservable mExitObservable;

    public static BgUncaughtExceptionHandler instance() {
        return Holder.INSTANCE;
    }

    private BgUncaughtExceptionHandler() {
    }

    private static class Holder {
        private static final BgUncaughtExceptionHandler INSTANCE = new BgUncaughtExceptionHandler();
    }

    public void init(IExitObservable exitObservable){
        this.mExitObservable = exitObservable;
    }

    /**
     * 是否正在处理crash
     * 处理crash过程中是不能再处理crash的
     */
    private boolean mIsHandleCrash = false;
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (mIsHandleCrash){
            return ;
        }
        mIsHandleCrash = true;

        String threadName = t.getName();
        boolean needExit = true; //暂时要求所有崩溃都退出后台，看各线程的崩溃率，再决定

        UCLog.warn("Uncaught exception received from thread :" + threadName);

        final String stackMsg = ExceptionUtil.getStackTrace(e);
        UCLog.error(stackMsg);

        upload(threadName,stackMsg,needExit);

//        //todo lizx3 8.0.1 崩溃，停用Service。这里可不可以优化成，重新初始化一遍？
        if (needExit) {
            //后台进程，理论上，等多久都没太大问题
            UCLog.warn("Uncaught exception received in main thread, stop service after 6 seconds .");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (mExitObservable != null) {
                mExitObservable.exit();
            }
        } else {
            if (t instanceof HandlerThread) {
                //todo lizx3 8.0.1 非主线程崩溃，如果是HandlerThread，已经跳出Looper了，线程模型被破坏了，在这里想办法重建
            }
        }
        mIsHandleCrash = false;
    }

    /**
     * 统计崩溃日志到服务器
     * @return
     */
    private void upload(String name, String msg, boolean needExit) {
        ContentNode crash = ContentNodeFactory.create(new CrashChildNode(name, msg, needExit));
        Stat.commit(crash);
    }
}
