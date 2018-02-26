package com.woyou.zxbrowser;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.woyou.baseconfig.ConfigHelper;
import com.woyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by lee on 18-2-24.
 */

public class VideoInfo {
    public VideoInfo(String url, String extension) {
        mUrl = url;
        mExtension = extension;
        uuid = UUID.randomUUID();
    }

    public String getUrl() {
        return mUrl;
    }

    private UUID uuid;
    private String mUrl;
    private String mExtension;

    public void onClick(View view) {
        if(downloadVideo()){
            ToastUtil.showLong("下载开始,请在通知栏查看下载进度");
        }
    }

    private boolean downloadVideo() {
        DownloadManager downloadManager = (DownloadManager) ConfigHelper.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(mUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        String fileName = uuid + "." + mExtension;
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir("Videos", fileName);
        request.setVisibleInDownloadsUi(true);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            return true;
        }else{
            return false;
        }
    }

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
