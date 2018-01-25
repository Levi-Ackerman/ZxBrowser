package com.woyou.zxbrowser.browser;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

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
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    public void setWebEventListener(IWebEventListener webEventListener) {
        mWebEventListener = webEventListener;
    }
}
