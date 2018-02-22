package com.woyou.zxbrowser.browser;

import android.webkit.WebView;

/**
 * Created by lee on 18-1-25.
 */

public interface IWebEventListener {
    void onPageFinished(WebView webView, String url);

    void onPageStarted(WebView webView, String url);

    void onProgressChanged(WebView view, int newProgress);
}
