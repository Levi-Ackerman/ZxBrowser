package com.woyou.zxbrowser.util;


import android.content.Context;

import com.litesuits.orm.LiteOrm;

public class OrmUtil {
    private static LiteOrm liteOrm;

    public static void init(Context context) {
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(context, "zx.db");
        }
        liteOrm.setDebugged(true);
    }
    public static LiteOrm getOrm(){
        return liteOrm;
    }
}
