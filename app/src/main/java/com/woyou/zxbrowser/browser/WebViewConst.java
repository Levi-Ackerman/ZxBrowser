package com.woyou.zxbrowser.browser;


public class WebViewConst {
    public static final String JS_PROMPT_PREFIX = "zx_prompt:";
    public static final String JS_BRIDGE_PRIFIX = "javascript:";
    public static final String TIMING_SCRIPT =JS_BRIDGE_PRIFIX +
            "performance.timing.navType = performance.navigation.type" +
            "prompt(JSON.stringify(performance.timing),'"+ JS_PROMPT_PREFIX+"');";
}
