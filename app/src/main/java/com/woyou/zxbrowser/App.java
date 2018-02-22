package com.woyou.zxbrowser;

import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

import com.woyou.baseconfig.ConfigHelper;
import com.zhuge.analysis.stat.ZhugeSDK;


/**
 * Created by lee on 18-1-25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConfigHelper.initApp(this);
        ConfigHelper.initDebuggable(BuildConfig.DEBUG);
//        ZhugeSDK.getInstance().init(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
//        File cacheFile = new File(this.getCacheDir(),"cache_webview");
//        CacheWebView.getWebViewCache().initApp(this,cacheFile,1024*1024*100);
    }
}
