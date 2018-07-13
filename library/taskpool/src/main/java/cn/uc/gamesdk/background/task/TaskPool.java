package cn.uc.gamesdk.background.task;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import cn.uc.gamesdk.background.exit.IExitObservable;
import cn.uc.gamesdk.background.exit.IExitSubscriber;
import cn.uc.gamesdk.background.log.Tags;
import cn.uc.gamesdk.background.log.UCLog;
import lee.top.taskpool.BuildConfig;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.thread.TaskPool.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class TaskPool {
    public static final String TAG = Tags.TAG_MODULE_TASK_POOL;
    /**
     * 跑主线程Task的looper
     */
    private static Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    /**
     * 跑后台Task的looper
     */
    private static final Handler BACK_HANDLER;

    static {
        HandlerThread handlerThread = new HandlerThread("bgservice-back");
        handlerThread.start();
        BACK_HANDLER = new Handler(handlerThread.getLooper());
    }

    /**

     * 有些IO任务比较耗时，但是必须同步执行，不在乎被阻塞，那就用这个来执行吧
     */
    private static final Handler FILE_HANDLER;

    static {
        HandlerThread handlerThread = new HandlerThread("bgservice-file");
        handlerThread.start();
        FILE_HANDLER = new Handler(handlerThread.getLooper());
    }

    /**
     * Network 线程池
     */
    private static NetworkThreadPool NETWORK_THREAD_POOL;

    /**
     * 周期任务线程池
     */
    private static CycleThreadPool CYCLE_THREAD_POOL;

    /**
     * 立即执行一个task
     * @param mode 执行线程
     * @param runnable
     * @param delay
     */
    public static void execute(TaskMode mode, Runnable runnable, long delay) {
        if (isShutdown) {
            if(BuildConfig.DEBUG){
                UCLog.warn(TAG," Task pool is shutdown, don't put task to here .");
            }
            return;
        }
        if (BuildConfig.DEBUG){
            UCLog.debug(TAG," Start task on thread :", mode.name());
        }
        switch (mode) {
            case UI:
                MAIN_HANDLER.postDelayed(runnable, delay);
                break;
            case BACKGROUND:
                BACK_HANDLER.postDelayed(runnable, delay);
                break;
            case FILE:
                FILE_HANDLER.postDelayed(runnable, delay);
                break;
            case NETWORK:
                NETWORK_THREAD_POOL.schedule(runnable,delay, TimeUnit.MILLISECONDS);
                break;
        }
    }

    /**
     * 判断当前线程是否为入参所示线程
     * @param mode
     * @return
     */
    public static boolean isOnTaskMode(TaskMode mode){
        Thread curThread = Thread.currentThread();

        if (curThread instanceof HandlerThread){
            return Looper.myLooper() == ((HandlerThread) curThread).getLooper();
        }

        if (mode == TaskMode.NETWORK){
            return curThread.getName() != null && curThread.getName().startsWith(NetworkThreadPool.NETWORK_THREAD_PREFIX);
        }

        return false;
    }

    /**
     * 按周期执行Task
     * @param mode
     * @param runnable
     * @param delay 首次执行的延时，为0时立即执行首次。单位 毫秒
     * @param cyclePeriod 周期间隔，不大于0时表示单次执行。单位 毫秒
     */
    public static void execute(final TaskMode mode, final Runnable runnable, final long delay, long cyclePeriod){
        if (cyclePeriod <= 0){
            execute(mode,runnable,delay);
            return ;
        }

        UCLog.debug(TAG," Start a cycle task ");
        CYCLE_THREAD_POOL.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                execute(mode,runnable,delay);
            }
        }, delay, cyclePeriod, TimeUnit.MILLISECONDS);
    }

    public static void execute(TaskMode mode, Runnable runnable) {
        execute(mode, runnable, 0);
    }


    private static boolean isShutdown = false;
    private static Thread.UncaughtExceptionHandler sCrashHandler;

    public static void init(Thread.UncaughtExceptionHandler crashHandler, IExitObservable exitHandler) {
        sCrashHandler = crashHandler;

        NETWORK_THREAD_POOL = new NetworkThreadPool(sCrashHandler);
        CYCLE_THREAD_POOL = new CycleThreadPool(sCrashHandler);

        MAIN_HANDLER.getLooper().getThread().setUncaughtExceptionHandler(sCrashHandler);
        BACK_HANDLER.getLooper().getThread().setUncaughtExceptionHandler(sCrashHandler);
        FILE_HANDLER.getLooper().getThread().setUncaughtExceptionHandler(sCrashHandler);

        exitHandler.subscribe(new IExitSubscriber() {
            @Override
            public void onExiting() {
                //崩溃时停用所有线程
                // 以下两套api的区别是
                // quit safely 执行所有非延迟任务，抛弃所有延迟任务，停止
                // quit 直接抛弃所有任务，停止
                isShutdown = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    //主线程不允许退出，所以不要退出主线程
                    quitHandler(BACK_HANDLER);
                    quitHandler(FILE_HANDLER);
                } else {
                    //主线程不允许退出，所以不要退出主线程
                    quitHandler(BACK_HANDLER);
                    quitHandler(FILE_HANDLER);
                }
                NETWORK_THREAD_POOL.shutdown();
                CYCLE_THREAD_POOL.shutdown();
            }
        });
    }

    private static void quitHandler(Handler handler) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                handler.getLooper().quitSafely();
            } else {
                handler.getLooper().quit();
            }
        } catch (Exception e) {
            UCLog.error(e);
        }
    }

}
