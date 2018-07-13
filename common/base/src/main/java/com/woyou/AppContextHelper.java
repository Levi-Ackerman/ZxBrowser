package com.woyou;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.ninegame.gamesdk.background.base.AppContextHelper.java
 * <p>
 * Creation     : 2018/6/20
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class AppContextHelper {
    private static Service sMainService;
    private static Context mAppContext;
    private static Application sApplication;

    public static void setMainService(Service service) {
        sMainService = service;
    }

    public static void setContext(Context sContext) {
        AppContextHelper.mAppContext = sContext;
    }

    public static void setApplication(Application sApplication) {
        AppContextHelper.sApplication = sApplication;
    }

    public static Context appContext(){
        return mAppContext;
    }

    public static Application application() {
        return sApplication;
    }

    public static ActivityManager activityManager() {
        return (ActivityManager) appContext().getSystemService(Context.ACTIVITY_SERVICE);
    }

    public static Service mainService() {
        return sMainService;
    }


    public static ConnectivityManager getConnectivityManager() {
        ConnectivityManager cm = (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm;
    }

    public static TelephonyManager telephonyManager() {
        return (TelephonyManager) mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static WifiManager wifiManager() {
        return (WifiManager) mAppContext.getSystemService(Context.WIFI_SERVICE);
    }

    public static ContentResolver contentResolver() {
        return mAppContext.getContentResolver();
    }
}
