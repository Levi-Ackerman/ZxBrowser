package cn.uc.gamesdk.background.task;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 专门执行周期任务
 * 由于CycleThreadPool只负责周期调度，实事都是交给其他线程去做的。所以1个core线程完全可以满足调度需要
 */
/*包内可见*/class CycleThreadPool extends ScheduledThreadPoolExecutor {
    CycleThreadPool(Thread.UncaughtExceptionHandler crashHandler) {
        super(1, new BGThreadFactory("BGService Cycle Thread", crashHandler));
    }
}
