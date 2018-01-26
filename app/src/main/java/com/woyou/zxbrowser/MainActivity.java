package com.woyou.zxbrowser;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.woyou.zxbrowser.browser.IWebEventListener;
import com.woyou.zxbrowser.common.Const;
import com.woyou.zxbrowser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements IWebEventListener {

    public static final String TIMING_SCRIPT = "prompt(JSON.stringify(performance.timing),'"+ Const.JS_PROMPT_PREFIX+"');";
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btnLoad.setOnClickListener((view) -> mBinding.webview.loadUrl(mBinding.addressBar.getText().toString()));
        mBinding.webview.setWebEventListener(this);
        mBinding.floatButton.setOnClickListener((view) -> mBinding.webview.evaluateJavascript(
                TIMING_SCRIPT, null));
    }

    @Override
    public void onBackPressed() {
        if (mBinding.webview.canGoBack()) {
            mBinding.webview.goBack();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("确认退出？")
                    .setPositiveButton("确定", (dialog, which) -> MainActivity.super.onBackPressed())
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        mBinding.addressBar.setText(url);
        mBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageStarted(WebView webView, String url) {
        mBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mBinding.progressBar.setProgress(newProgress);
    }
}
