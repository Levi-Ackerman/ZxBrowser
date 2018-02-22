package com.woyou.baseconfig;

import android.app.Application;

/**
 * Created by lee on 18-2-22.
 */

public class ConfigHelper {
    private static Application app;
    private static boolean debuggable;

    public static void initApp(Application app){
        ConfigHelper.app = app;
    }

    public static void initDebuggable(boolean debuggable){
        ConfigHelper.debuggable = debuggable;
    }

    public static Application getApplication(){
        return app;
    }

    public static boolean debuggable() {
        return debuggable;
    }
}
