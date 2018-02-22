package com.woyou.util;

import com.woyou.baseconfig.ConfigHelper;

public class AssertUtil {
    public static void assertTrue(boolean expres, String message){
        if (ConfigHelper.debuggable() && expres) {
            throw new Error(message);
        }
    }
}
