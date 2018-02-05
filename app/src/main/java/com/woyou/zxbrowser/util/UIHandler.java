package com.woyou.zxbrowser.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by lee on 18-2-5.
 */

public class UIHandler {
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());
    public static void post(@NonNull Runnable runnable){
        sMainHandler.post(runnable);
    }
    public static void postDelay(@NonNull Runnable runnable, long delay){
        sMainHandler.postDelayed(runnable,delay);
    }
}
