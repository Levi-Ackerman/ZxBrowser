package com.woyou.zxbrowser.http;


import com.woyou.util.FileUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS) //连接超时阈值
            .writeTimeout(10, TimeUnit.SECONDS) //写超时阈值
            .readTimeout(10, TimeUnit.SECONDS)  //读超时阈值
            .retryOnConnectionFailure(true) //当失败后重试
            .cache(new Cache(FileUtil.WEBVIEW_CACHE_DIR,100*1024*1024))
            .build();
    public static Response get(String url, Map<String, String> headers){
        Request.Builder builder = new Request.Builder().url(url);//.cacheControl(CacheControl.FORCE_CACHE);
        if (headers != null){
            for (String headKey : headers.keySet()) {
                builder.addHeader(headKey,headers.get(headKey));
            }
        }
        Call call = sOkHttpClient.newCall(builder.build());
        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
