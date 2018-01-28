package com.woyou.zxbrowser.util;

import android.os.Environment;

import com.woyou.zxbrowser.App;

import java.io.File;

/**
 * Created by lee on 18-1-28.
 */

public class FileUtil {
    public static final String WEBVIEW_CACHE_DIR
            = App.application().getExternalFilesDir("ZxWebViewCache").getAbsolutePath();
}
