package com.woyou.zxbrowser.util;


import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.woyou.zxbrowser.BuildConfig;
import com.woyou.zxbrowser.model.WebViewPerform;

public class OrmUtil {
    private static final String DB_NAME = "zx.db";
    private static LiteOrm liteOrm;
    private static final int DB_VERSION = 2;

    public static void init(Context context) {
        AssertUtil.assertTrue(liteOrm != null,"Database orm has been inited!");
        DataBaseConfig config = new DataBaseConfig(context, DB_NAME, BuildConfig.DEBUG, DB_VERSION, (sqLiteDatabase, oldVer, newVer) -> {
            if (oldVer == 1 && newVer == 2){
                String sql = String.format("ALTER TABLE %s ADD %s int", WebViewPerform.TABLE_NAME,WebViewPerform.TCP_TIME);
                sqLiteDatabase.execSQL(sql);
            }
        });
        liteOrm = LiteOrm.newSingleInstance(config);
    }

    public static LiteOrm getOrm() {
        return liteOrm;
    }
}
