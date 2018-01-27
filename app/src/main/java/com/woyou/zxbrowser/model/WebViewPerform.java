package com.woyou.zxbrowser.model;


import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.woyou.zxbrowser.BuildConfig;

@Table(WebViewPerform.TABLE_NAME)
public class WebViewPerform {
    public static final String TABLE_NAME = "perform";
    public static final String URL = "url";
    public static final String VER = "ver";
    public static final String T0 = "t0";
    public static final String T1 = "t1";
    public static final String TCP_TIME = "tcpTime";
    public static final String T2 = "t2";
    public static final String NAV_TYPE = "navType";
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;

    @Column(URL)
    public String url;

    @Column(VER)
    public int mVersion = BuildConfig.VERSION_CODE;

    @Column(T0)
    public long t0;

    @Column(T1)
    public long t1;

    @Column(TCP_TIME)
    public long tcpTime;

    @Column(T2)
    public long t2;

    @Column(NAV_TYPE)
    public int navType;

    public WebViewPerform (String url, Timing timing) {
        t0 = timing.responseEnd - timing.navigationStart;
        t1 = timing.domInteractive - timing.navigationStart;
        t2 = timing.domComplete - timing.navigationStart;
        tcpTime = timing.connectEnd - timing.connectStart;
        navType = timing.navType;
        this.url = url;
    }
}
