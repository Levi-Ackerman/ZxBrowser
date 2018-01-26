package com.woyou.zxbrowser.util;

import com.google.gson.Gson;

/**
 * Created by lee on 18-1-26.
 */

public class GsonUtil {
    private static Gson mGson = new Gson();
    public static Gson inst(){
        return mGson;
    }
}
