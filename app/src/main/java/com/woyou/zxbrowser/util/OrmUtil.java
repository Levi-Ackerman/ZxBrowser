package com.woyou.zxbrowser.util;


import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.woyou.zxbrowser.BuildConfig;

public class OrmUtil {
    private static final String DB_NAME = "zx.db";
    private static LiteOrm liteOrm;
    private static final int DB_VERSION = 2;

    public static void init(Context context) {
        AssertUtil.assertTrue(liteOrm != null,"Database orm has been inited!");
        DataBaseConfig config = new DataBaseConfig(context, DB_NAME, BuildConfig.DEBUG, DB_VERSION, (sqLiteDatabase, oldVer, newVer) -> {
        });
        liteOrm = LiteOrm.newSingleInstance(config);
    }

    public static LiteOrm getOrm() {
        return liteOrm;
    }
}
