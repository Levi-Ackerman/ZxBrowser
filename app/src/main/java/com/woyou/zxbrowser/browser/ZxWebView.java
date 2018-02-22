package com.woyou.zxbrowser.browser;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import junit.runner.Version;

import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.woyou.zxbrowser.browser.ZxWebView.java
 * <p>
 * Creation     : 2017/12/18
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public class ZxWebView extends WebView {
    private String UA = "Mozilla/5.0 (Linux; Android %s; %s Build/NMF26X; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.107 Mobile Safari/537.36";
    private ZxWebChromeClient mChromeClient;
    private ZxWebViewClient mWebViewClient;
    public ZxWebView(Context context) {
        super(context);
        init();
    }

    public ZxWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZxWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        mChromeClient = new ZxWebChromeClient();
        mWebViewClient = new ZxWebViewClient();
        setWebChromeClient(mChromeClient);
        setWebViewClient(mWebViewClient);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    public void setWebEventListener(IWebEventListener webEventListener) {
        mChromeClient.setWebEventListener(webEventListener);
        mWebViewClient.setWebEventListener(webEventListener);
    }
}
