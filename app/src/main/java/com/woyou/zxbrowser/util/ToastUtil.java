package com.woyou.zxbrowser.util;


import android.widget.Toast;

import com.woyou.zxbrowser.App;

public class ToastUtil {
    public static void showLong(String text){
        Toast.makeText(App.application(),text, Toast.LENGTH_LONG).show();
    }
}
