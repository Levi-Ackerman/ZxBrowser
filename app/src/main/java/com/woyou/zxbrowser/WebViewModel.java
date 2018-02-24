package com.woyou.zxbrowser;

import android.app.DownloadManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;

import com.woyou.baseconfig.ConstConfig;
import com.woyou.util.MimeTypeUtil;
import com.woyou.util.VideoUtil;
import com.woyou.util.ZxLog;
import com.woyou.zxbrowser.browser.IWebEventListener;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

/**
 * Created by lee on 18-2-22.
 */

public class WebViewModel extends ViewModel implements IWebEventListener {
    private MutableLiveData<String> mUrl = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgress = new MutableLiveData<>();
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    private MutableLiveData<Bitmap> mIcon = new MutableLiveData<>();
    private MutableLiveData<Integer> mVideoSize= new MutableLiveData<>();
    private LinkedHashSet<VideoInfo> mVideos = new LinkedHashSet<>();

    public LiveData<String> getUrl() {
        return mUrl;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        mProgress.postValue(100);
    }

    @Override
    public void onPageStarted(WebView webView, String url) {
        if (ConstConfig.HOME_PAGE_URL.equals(url)) {
            url = ""; //hide the home page url, don't post to View
            webView.addJavascriptInterface(new Object() {
                @JavascriptInterface
                public String handleUrl(String url) {
                    return handUrl(url);
                }
            }, "loader");
        }
        mUrl.postValue(url);
        mProgress.postValue(0);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mProgress.postValue(newProgress);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        url = url.toLowerCase(Locale.US);
        //这个方法只会在主线程回调,所以,不用担心多线程问题
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (VideoUtil.isVideoExtension(extension)) {
            ZxLog.debug("发现视频链接: " + url);
            VideoInfo videoInfo = new VideoInfo(url);
            mVideos.add(videoInfo);
            mVideoSize.setValue(mVideos.size());
//            DownloadManager downloadManager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//            Uri uri = Uri.parse(url);
//            DownloadManager.Request request = new DownloadManager.Request(uri);
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//            request.setTitle("hello.mp4");
//            request.setDestinationInExternalFilesDir(view.getContext(), Environment.DIRECTORY_DOWNLOADS, "mydown");
//            request.setVisibleInDownloadsUi(true);
//            downloadManager.enqueue(request);
        }
    }

    public void onReceiveTitle(WebView view, String title) {
        mTitle.postValue(title);
    }

    @Override
    public void onReceiveIcon(WebView view, Bitmap icon) {
        mIcon.postValue(icon);
    }

    public LiveData<Integer> getProgress() {
        return mProgress;
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }

    public LiveData<Bitmap> getFavicon() {
        return mIcon;
    }

    /**
     * if input a url ,return the url;
     * else return the keyword in search engine url
     *
     * @param url
     * @return
     */
    public String handUrl(@NonNull String url) {
        if (url.matches(ConstConfig.URL_REG) && url.contains(".")) {
            if (!url.contains("://")) {
                url = "http://" + url;
            }
            return url;
        }
        return ConstConfig.BAIDU_SEARCH_PREFIX + url;
    }

    public LiveData<Integer> getVideoSize() {
        return mVideoSize;
    }

    public LinkedHashSet<VideoInfo> getVideos() {
        return mVideos;
    }
}
