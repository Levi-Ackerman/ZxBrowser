package com.woyou.util;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.woyou.AppContextHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by guosen.lgs@alibaba-inc.com on 12/26/16.
 */
public class PhoneInfoUtil {

    private static final String TAG = PhoneInfoUtil.class.getSimpleName();

    public static class Delegate {

        public String getIMEI() {
            String imei = "";
            try {
                TelephonyManager telephonyManager = AppContextHelper.telephonyManager();
                imei = telephonyManager.getDeviceId();
            } catch (Exception e) {
//                Log.w(TAG, "get imei exception", e);
            }
            return imei == null ? "" : imei;
        }

        public String getIMSI() {
            String imsi = "";
            try {
                TelephonyManager telephonyManager = AppContextHelper.telephonyManager();
                imsi = telephonyManager.getSubscriberId();
            } catch (Exception e) {
//                Log.w(TAG, "getIMSI", e);
            }
            return imsi == null ? "" : imsi;
        }

        public String getMAC() {
            if (Build.VERSION.SDK_INT < 23) {
                return getMacFromWifi();
            } else {
                return getMacFromNetwork();
            }
        }


        private static String getMacFromWifi() {
            String macAddress = "";
            try {
                WifiManager wifiMgr = AppContextHelper.wifiManager();
                if (wifiMgr != null) {
                    WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                    if (wifiInfo != null) {
                        macAddress = wifiInfo.getMacAddress();
                    }
                }
            } catch (Exception e) {
            }
            return macAddress;
        }

        private static String getMacFromNetwork() {
            String macAddress = "";
            try {
                Enumeration<NetworkInterface> items = NetworkInterface.getNetworkInterfaces();
                if (items != null) {
                    List<NetworkInterface> all = Collections.list(items);
                    for (NetworkInterface nif : all) {
                        if (!"wlan0".equalsIgnoreCase(nif.getName())) {
                            continue;
                        }

                        byte[] macBytes = nif.getHardwareAddress();
                        if (macBytes == null) {
                            return macAddress;
                        }
                        macAddress = getMacInHex(macBytes);
                        break;
                    }
                }
            } catch (Exception ex) {
            }
            return macAddress;
        }

        static final String HEXES = "0123456789ABCDEF";

        private static String getMacInHex(byte[] raw) {
            if (raw == null) {
                return "";
            }

            final StringBuilder hex = new StringBuilder(2 * raw.length);
            for (final byte b : raw) {
                hex.append(HEXES.charAt((b & 0xF0) >> 4))
                        .append(HEXES.charAt((b & 0x0F)))
                        .append(':');
            }

            if (hex.length() > 0) {
                hex.deleteCharAt(hex.length() - 1);
            }
            return hex.toString();
        }


        public String getAndroidID() {
            String androidId = Settings.Secure.getString(AppContextHelper.contentResolver(), Settings.Secure.ANDROID_ID);
            return androidId == null ? "" : androidId;
        }

        public String getInstallID() {
            String installId = "";
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
                try {
                    String pkgName = AppContextHelper.appContext().getPackageName();
                    installId = String.valueOf(AppContextHelper.appContext().getPackageManager().getPackageInfo(pkgName, 0).firstInstallTime);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w(TAG, "getInstallID", e);
                }
            } else {
                File dataPath = AppContextHelper.appContext().getFilesDir().getParentFile();
                if (dataPath != null) {
                    installId = String.valueOf(PhoneInfoUtil.getiNode(dataPath.getAbsolutePath() + File.separator + "lib"));
                }
            }

            return installId;
        }
    }

    /**
     * For easier mocking the phone state data, we use a delegate class here.
     */
    private static Delegate mDelegate = new Delegate();

    private static final String CLASS_NAME = "PhoneInfoUtil";

    /**
     * 获取imei
     *
     * @return
     */
    public static String getIMEI() {
        return mDelegate.getIMEI();
    }

    /**
     * 获取imsi
     *
     * @return
     */
    public static String getIMSI() {
        return mDelegate.getIMSI();
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMAC() {
        return mDelegate.getMAC();
    }

    /**
     * 获取android_id
     *
     * @return
     */
    public static String getAndroidId() {
        return mDelegate.getAndroidID();
    }

    /**
     * 获取文件或文件夹的inode
     *
     * @param path
     * @return
     */
    public static int getiNode(String path) {
        int inode = -1;
        try {
            Class<?> fileUtilClass = Class.forName("android.os.FileUtils");
            Class<?> fileStatusClass = Class.forName("android.os.FileUtils$FileStatus");

            Method getFileStatus = fileUtilClass.getMethod("getFileStatus", String.class, fileStatusClass);

            Object status = fileStatusClass.newInstance();
            boolean invokeSuc = (Boolean) getFileStatus.invoke(fileUtilClass.newInstance(), path, status);

            if (invokeSuc) {
                Field field = fileStatusClass.getField("ino");

                inode = field.getInt(status);
            }
        } catch (Exception e) {
            Log.w(TAG, "getiNode", e);
        }

        return inode;
    }

    /**
     * 获取安装标识
     * 2.3以上获取安装时间 ，2.3以下获取data文件夹下的lib目录的iNode值
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String getInstallId() {
        return mDelegate.getInstallID();
    }

}
