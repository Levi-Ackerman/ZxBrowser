package com.woyou.util;

import com.woyou.baseconfig.ConfigHelper;

import java.io.File;

/**
 * Created by lee on 18-2-24.
 */

public class FileUtil {
    public static final File WEBVIEW_CACHE_DIR = ConfigHelper.getApplication().getExternalCacheDir();
}
