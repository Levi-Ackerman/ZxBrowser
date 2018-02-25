package com.woyou.zxbrowser.browser;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import java.util.Map;

/**
 * Created by lee on 18-1-25.
 */

public interface IWebEventListener {
    void onPageFinished(WebView webView, String url);

    void onPageStarted(WebView webView, String url);

    void onProgressChanged(WebView view, int newProgress);

    void onReceiveTitle(WebView view, String title);

    void onReceiveIcon(WebView view, Bitmap icon);

    void onLoadResource(WebView view, String s, Map<String, String> requestHeaders);
}
