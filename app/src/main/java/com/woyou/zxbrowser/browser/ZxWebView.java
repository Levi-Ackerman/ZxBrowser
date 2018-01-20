package com.woyou.zxbrowser.browser;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import junit.runner.Version;

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

    private String UA = "Mozilla/5.0 (Linux; Android %s; %s Build/NMF26X; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.107 Mobile Safari/537.36";

    private void init() {
        getSettings().setJavaScriptEnabled(true);
//        getSettings().setUseWideViewPort(false);
//        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUserAgentString(String.format(UA, Build.VERSION.RELEASE, Build.MODEL));
        setWebChromeClient(new ZxWebChromeClient());
        setWebViewClient(new ZxWebViewClient());
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }
}
