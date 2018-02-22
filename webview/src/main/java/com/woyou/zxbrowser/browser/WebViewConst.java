package com.woyou.zxbrowser.browser;


public class WebViewConst {
    public static final String JS_PROMPT_PREFIX = "zx_prompt:";
    public static final String JS_BRIDGE_PRIFIX = "javascript:";
    public static final String TIMING_SCRIPT = "var timing = new Object;" +
            "timing.t0 = performance.timing.responseEnd - performance.timing.navigationStart;" +
            "timing.t1 = performance.timing.domInteractive - performance.timing.navigationStart;" +
            "timing.t2 = performance.timing.domComplete - performance.timing.navigationStart;" +
            "timing.tcpTime = performance.timing.connectEnd - performance.timing.connectStart;" +
            "timing.url = location.href;" +
            "timing.navType = performance.navigation.type;" +
            "prompt(JSON.stringify(timing),'" + JS_PROMPT_PREFIX + "');";
    //{"navigationStart":1517036309557,"unloadEventStart":1517036309717,"unloadEventEnd":1517036309717,"redirectStart":0,"redirectEnd":0,"fetchStart":1517036309576,"domainLookupStart":1517036309580,"domainLookupEnd":1517036309581,"connectStart":1517036309581,"connectEnd":1517036309625,"secureConnectionStart":0,"requestStart":1517036309625,"responseStart":1517036309706,"responseEnd":1517036309716,"domLoading":1517036309758,"domInteractive":1517036309839,"domContentLoadedEventStart":1517036309839,"domContentLoadedEventEnd":1517036309839,"domComplete":1517036309994,"loadEventStart":1517036309995,"loadEventEnd":1517036309998}
}
