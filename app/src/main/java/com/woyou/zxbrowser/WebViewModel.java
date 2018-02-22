package com.woyou.zxbrowser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.webkit.WebView;

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

    public LiveData<Integer> getProgress() {
        return mProgress;
    }
}
