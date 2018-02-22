package com.woyou.util;


import android.util.Log;

public class ZxLog {
    private static final String TAG = "ZxLog";
    public static void debug(String log){
        if (DebugUtil.isDebugable()){
            Log.d(TAG,log);
        }
    }
    public static void debug(Object object){
        if (object!=null){
            debug(object.toString());
        }
    }
}
