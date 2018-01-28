package com.woyou.zxbrowser.browser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.woyou.zxbrowser.http.HttpClient;
import com.woyou.zxbrowser.util.ZxLog;

import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.woyou.zxbrowser.browser.WebViewConst.TIMING_SCRIPT;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.woyou.zxbrowser.browser.ZxWebViewClient.java
 * <p>
 * Creation     : 2017/12/18
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public class ZxWebViewClient extends WebViewClient {
    private IWebEventListener mWebEventListener;

    public void setWebEventListener(IWebEventListener webEventListener) {
        mWebEventListener = webEventListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("http")) {
            return false;
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
            } catch (Exception ignored) {
            }
            return true;
        }
    }

//    private static List<String> mWhiteExt = Arrays.asList("", "css", "js", "jpg", "jpeg", "png");
    private static final boolean USE_OK_HTTP = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (USE_OK_HTTP) {
            String url = request.getUrl().toString();
//        String extension = MimeTypeMap.getFileExtensionFromUrl(url.toLowerCase());
//        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//        if (!mWhiteExt.contains(extension)) {
//            ZxLog.debug("ignore " + request.getUrl());
//            return null;
//        }
            int errorCode = 0;
            if (request.getMethod().equalsIgnoreCase("get")) {
                errorCode |= 1;
                Response response = HttpClient.get(url, request.getRequestHeaders());
                if (response != null) {
                    errorCode |= 2;
                    String mimeType = response.header("content-type", "text/html");
                    if (!TextUtils.isEmpty(mimeType) && mimeType.indexOf(';') > -1) {
                        // remove the 'charset=utf-8' case of 'text/html;charset=utf-8'
                        mimeType = mimeType.substring(0, mimeType.indexOf(';'));
                    }
                    String encoding = response.header("content-encoding", "utf-8");
                    ResponseBody body = response.body();
                    if (body != null) {
                        ZxLog.debug("request " + request.getUrl());
                        return new WebResourceResponse(mimeType, encoding, body.byteStream());
                    }
                }
            }
            ZxLog.debug(errorCode + ":ignore " + request.getUrl());
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        view.getSettings().setBlockNetworkImage(true);
//        view.getSettings().setLoadsImagesAutomatically(false);
        if (mWebEventListener != null) {
            mWebEventListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        view.getSettings().setBlockNetworkImage(false);
//        view.getSettings().setLoadsImagesAutomatically(true);
        if (mWebEventListener != null) {
            mWebEventListener.onPageFinished(view, url);
        }
        view.evaluateJavascript(TIMING_SCRIPT, null);
    }
}
