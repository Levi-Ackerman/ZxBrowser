package com.woyou.util;


import android.widget.Toast;

import com.woyou.baseconfig.ConfigHelper;

public class ToastUtil {
    public static void showLong(String text){
        Toast.makeText(ConfigHelper.getApplication(),text, Toast.LENGTH_LONG).show();
    }
}
