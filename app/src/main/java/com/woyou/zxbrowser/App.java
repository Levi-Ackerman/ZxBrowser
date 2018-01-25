package com.woyou.zxbrowser;

import android.app.Application;

import java.io.File;

import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * Created by lee on 18-1-25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        File cacheFile = new File(this.getCacheDir(),"cache_webview");

        CacheWebView.getWebViewCache().init(this,cacheFile,1024*1024*100);
    }
}
