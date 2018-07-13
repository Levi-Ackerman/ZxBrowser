package cn.uc.gamesdk.background.task;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 并行执行NetWork任务
 *
 */
/*包内可见*/class NetworkThreadPool extends ScheduledThreadPoolExecutor {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    public static final String NETWORK_THREAD_PREFIX = "BGService Network Thread";

    NetworkThreadPool(Thread.UncaughtExceptionHandler crashHandler) {
        super(CORE_POOL_SIZE, new BGThreadFactory(NETWORK_THREAD_PREFIX, crashHandler));
    }
}
