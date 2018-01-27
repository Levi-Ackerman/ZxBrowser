package com.woyou.zxbrowser.util;


import com.woyou.zxbrowser.BuildConfig;

public class AssertUtil {
    public static void assertTrue(boolean expres, String message){
        if (BuildConfig.DEBUG && expres) {
            throw new Error(message);
        }
    }
}
