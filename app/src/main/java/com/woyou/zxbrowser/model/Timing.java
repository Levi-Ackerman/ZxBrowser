package com.woyou.zxbrowser.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lee on 18-1-25.
 */

public class Timing {
    public long getT1() {
        return domInteractive - navigationStart;
    }

    private long navigationStart;
    private long domInteractive;

    public static Timing fromJson(String value) {
        Timing timing = new Timing();
        try {
            if (value.charAt(0) == '"'){
                value = value.substring(1,value.length()-1);
            }
            value = value.replace("\\\"","\"");
            JSONObject jsonObject = new JSONObject(value);
            timing.navigationStart = jsonObject.optLong("navigationStart");
            timing.domInteractive = jsonObject.optLong("domInteractive");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timing;
    }
}
