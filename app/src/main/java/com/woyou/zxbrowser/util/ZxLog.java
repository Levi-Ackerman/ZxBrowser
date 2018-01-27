package com.woyou.zxbrowser.util;


import android.util.Log;

import com.woyou.zxbrowser.BuildConfig;

public class ZxLog {
    private static final String TAG = "ZxLog";
    public static void debug(String log){
        if (BuildConfig.DEBUG){
            Log.d(TAG,log);
        }
    }
}
