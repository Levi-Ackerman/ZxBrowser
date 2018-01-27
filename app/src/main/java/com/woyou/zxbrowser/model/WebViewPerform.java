package com.woyou.zxbrowser.model;


import com.google.gson.annotations.Expose;
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

    @Expose
    @Column(URL)
    public String url;

    @Column(VER)
    public int mVersion = BuildConfig.VERSION_CODE;

    @Expose
    @Column(T0)
    public long t0;

    @Expose
    @Column(T1)
    public long t1;

    @Expose
    @Column(TCP_TIME)
    public long tcpTime;

    @Expose
    @Column(T2)
    public long t2;

    @Expose
    @Column(NAV_TYPE)
    public int navType;
}
