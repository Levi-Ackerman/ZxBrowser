package com.woyou.zxbrowser.model;


import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.woyou.zxbrowser.BuildConfig;

@Table("perform")
public class WebViewPerform {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public long id;

    @Column("url")
    public String url;

    @Column("ver")
    public int mVersion = BuildConfig.VERSION_CODE;

    @Column("t0")
    public long t0;

    @Column("t1")
    public long t1;

    @Column("t2")
    public long t2;

    public WebViewPerform (String url, Timing timing) {
        t0 = timing.responseEnd - timing.navigationStart;
        t1 = timing.domInteractive - timing.navigationStart;
        t2 = timing.domComplete - timing.navigationStart;
        this.url = url;
    }
}
