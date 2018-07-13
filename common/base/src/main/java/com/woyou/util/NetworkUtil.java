package com.woyou.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.woyou.AppContextHelper;


/**
 * Created by guosen.lgs@alibaba-inc.com on 12/27/16.
 */
public class NetworkUtil {
    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager cm = AppContextHelper.getConnectivityManager();
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.getState() == NetworkInfo.State.CONNECTED;
        }catch (Throwable e){
            return false;
        }
    }
}
