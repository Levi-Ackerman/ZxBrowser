package com.woyou.zxbrowser.util;

import com.woyou.zxbrowser.App;

/**
 * Created by lee on 18-1-28.
 */

public class FileUtil {
    public static final String WEBVIEW_CACHE_DIR
            = App.application().getExternalFilesDir("ZxWebViewCache").getAbsolutePath();
}
