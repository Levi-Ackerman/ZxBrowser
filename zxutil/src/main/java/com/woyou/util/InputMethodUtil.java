package com.woyou.util;


import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtil {

    public static void totleShowSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
