package com.woyou.zxbrowser.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by lee on 18-1-26.
 */

public class GsonUtil {
    private static Gson mGson
            = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static Gson inst() {
        return mGson;
    }
}
