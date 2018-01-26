package com.woyou.zxbrowser.model;

import com.google.gson.annotations.Expose;

/**
 * Created by lee on 18-1-25.
 */

public class Timing {

    @Expose
    public long navigationStart;
    @Expose
    public long responseEnd;
    @Expose
    public long domInteractive;
    @Expose
    public long domComplete;
}
