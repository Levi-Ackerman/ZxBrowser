package com.woyou.zxbrowser;

/**
 * Created by lee on 18-2-24.
 */

public class VideoInfo {
    public VideoInfo(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    private String mUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoInfo videoInfo = (VideoInfo) o;

        return mUrl != null ? mUrl.equals(videoInfo.mUrl) : videoInfo.mUrl == null;
    }

    @Override
    public int hashCode() {
        return mUrl != null ? mUrl.hashCode() : 0;
    }
}
