package com.woyou.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.woyou.AppContextHelper;


/**
 * Created by guosen.lgs@alibaba-inc.com on 6/21/18.
 */
public class Prefs {
    private static final String PREF = "cn.uc.gamesdk.background";

    @SuppressLint("InlinedApi")
    public static SharedPreferences pref(String name) {

        int mode = Context.MODE_PRIVATE;
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            mode = Context.MODE_MULTI_PROCESS;
        }

        return AppContextHelper.appContext().getSharedPreferences(name, mode);
    }

    private static SharedPreferences pref(){
        return pref(PREF);
    }

    public static void put(String prefKey, int val) {
        pref().edit().putInt(prefKey, val).commit();
    }

    public static void put(String prefKey, long val) {
        pref().edit().putLong(prefKey, val).commit();
    }

    public static void put(String prefKey, boolean val) {
        pref().edit().putBoolean(prefKey, val).commit();
    }

    public static void put(String prefKey, String val) {
        pref().edit().putString(prefKey, val).commit();
    }

    public static void put(String prefKey, float val) {
        pref().edit().putFloat(prefKey, val).commit();
    }

    public static int get(String prefKey, int defVal) {
        return pref().getInt(prefKey, defVal);
    }

    public static long get(String prefKey, long defVal) {
        return pref().getLong(prefKey, defVal);
    }

    public static boolean get(String prefKey, boolean defVal) {
        return pref().getBoolean(prefKey, defVal);
    }

    public static String get(String prefKey, String defVal) {
        return pref().getString(prefKey, defVal);
    }

    public static float get(String prefKey, float defVal) {
        return pref().getFloat(prefKey, defVal);
    }
}
