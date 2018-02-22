package com.woyou.zxbrowser;

import android.app.DownloadManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;

import com.woyou.util.MimeTypeUtil;
import com.woyou.util.VideoUtil;
import com.woyou.util.ZxLog;
import com.woyou.zxbrowser.browser.IWebEventListener;

/**
 * Created by lee on 18-2-22.
 */

public class WebViewModel extends ViewModel implements IWebEventListener {
    private MutableLiveData<String> mUrl = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgress = new MutableLiveData<>();

    public LiveData<String> getUrl() {
        return mUrl;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        mProgress.postValue(100);
    }

    @Override
    public void onPageStarted(WebView webView, String url) {
        mUrl.postValue(url);
        mProgress.postValue(0);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mProgress.postValue(newProgress);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        //这个方法只会在主线程回调,所以,不用担心多线程问题
        String extension = MimeTypeMap.getFileExtensionFromUrl(url.toLowerCase());
        if (VideoUtil.isVideoExtension(extension)){
            ZxLog.debug("发现视频链接: "+url);
            DownloadManager downloadManager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
            request.setTitle("hello.mp4");
            request.setDestinationInExternalFilesDir(view.getContext(), Environment.DIRECTORY_DOWNLOADS,"mydown");
            request.setVisibleInDownloadsUi(true);
            downloadManager.enqueue(request);
//            mUrl.postValue(url);
        }
    }

    public LiveData<Integer> getProgress() {
        return mProgress;
    }
}
