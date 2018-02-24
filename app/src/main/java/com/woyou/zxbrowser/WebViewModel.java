package com.woyou.zxbrowser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.woyou.baseconfig.ConstConfig;
import com.woyou.zxbrowser.browser.IWebEventListener;

/**
 * Created by lee on 18-2-22.
 */

public class WebViewModel extends ViewModel implements IWebEventListener {
    private MutableLiveData<String> mUrl = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgress = new MutableLiveData<>();
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    private MutableLiveData<Bitmap> mIcon = new MutableLiveData<>();

    public LiveData<String> getUrl() {
        return mUrl;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        mProgress.postValue(100);
    }

    @Override
    public void onPageStarted(WebView webView, String url) {
        if (ConstConfig.HOME_PAGE_URL.equals(url)){
            url = ""; //hide the home page url, don't post to View
            webView.addJavascriptInterface(new Object(){
                @JavascriptInterface
                public String handleUrl(String url){
                    return handUrl(url);
                }
            },"loader");
        }
        mUrl.postValue(url);
        mProgress.postValue(0);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mProgress.postValue(newProgress);
    }

    @Override
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

    public LiveData<Bitmap> getFavicon(){
        return mIcon;
    }

    /**
     * if input a url ,return the url;
     * else return the keyword in search engine url
     * @param url
     * @return
     */
    public String handUrl(@NonNull String url) {
        if(url.matches(ConstConfig.URL_REG)){
            if (!url.contains("://")){
                url = "http://"+url;
            }
            return url;
        }
        return ConstConfig.BAIDU_SEARCH_PREFIX +url;
    }
}
