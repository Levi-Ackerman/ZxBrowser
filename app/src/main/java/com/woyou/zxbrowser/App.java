package com.woyou.zxbrowser;

import android.app.Application;

import com.idescout.sql.SqlScoutServer;
import com.woyou.zxbrowser.util.OrmUtil;

import java.io.File;

import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * Created by lee on 18-1-25.
 */

public class App extends Application {
    private static App mApp;
    public static Application application(){
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        OrmUtil.init(this);
        if (BuildConfig.DEBUG)
        {
            SqlScoutServer.create(this, getPackageName());
        }
//        File cacheFile = new File(this.getCacheDir(),"cache_webview");
//        CacheWebView.getWebViewCache().init(this,cacheFile,1024*1024*100);
    }
}
