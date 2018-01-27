package com.woyou.zxbrowser.browser;

import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.woyou.zxbrowser.model.WebViewPerform;
import com.woyou.zxbrowser.util.GsonUtil;
import com.woyou.zxbrowser.util.OrmUtil;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2017 UCWeb Inc. All Rights Reserved
 * Description  :  com.woyou.zxbrowser.browser.ZxWebChromeClient.java
 * <p>
 * Creation     : 2017/12/18
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2017 lizx, Create the file
 * *************************************************************
 */

public class ZxWebChromeClient extends WebChromeClient {
    private IWebEventListener mWebEventListener;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mWebEventListener.onProgressChanged(view,newProgress);
    }

    void setWebEventListener(IWebEventListener webEventListener) {
        mWebEventListener = webEventListener;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (WebViewConst.JS_PROMPT_PREFIX.equals(defaultValue)){
            WebViewPerform timing = GsonUtil.inst().fromJson(message,WebViewPerform.class);
            OrmUtil.getOrm().save(timing);
            result.cancel();
            return true;
        }
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }
}
