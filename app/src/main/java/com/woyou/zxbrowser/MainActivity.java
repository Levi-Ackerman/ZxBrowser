package com.woyou.zxbrowser;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.woyou.zxbrowser.browser.ZxWebView;
import com.woyou.zxbrowser.databinding.ActivityMainBinding;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.webview.loadUrl("https://www.baidu.com");
        mBinding.webview.stopLoading();
    }

    @Override
    public void onBackPressed() {
        if (mBinding.webview.canGoBack()){
            mBinding.webview.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
