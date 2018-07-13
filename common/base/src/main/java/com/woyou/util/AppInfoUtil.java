package com.woyou.util;

import android.app.ActivityManager;
import android.os.Debug;

import com.woyou.AppContextHelper;


/**
 * 应用信息
 * Created by guosen.lgs@alibaba-inc.com on 6/21/18.
 */
public class AppInfoUtil {

    /**
     * 获取当前进程的内存占用情况
     *
     * @return
     */
    public static int meminfo() {
        ActivityManager activityManager = AppContextHelper.activityManager();
        int pid = android.os.Process.myPid();
        Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});
        int totalPrivateDirty = processMemoryInfo[0].getTotalPrivateDirty();//KB
        return totalPrivateDirty;
    }
}
