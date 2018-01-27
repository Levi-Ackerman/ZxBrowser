package com.woyou.zxbrowser;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.woyou.zxbrowser.browser.IWebEventListener;
import com.woyou.zxbrowser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements IWebEventListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.addressBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                String url = mBinding.addressBar.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    mBinding.webview.loadUrl(url);
                    totleShowSoftInput();
                    return true;
                }
            }
            return false;
        });
        mBinding.webview.setWebEventListener(this);
        mBinding.back.setOnClickListener(v -> mBinding.webview.goBack());
        mBinding.forward.setOnClickListener(v -> mBinding.webview.goForward());
        mBinding.refresh.setOnClickListener(v -> mBinding.webview.reload());
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

    public void totleShowSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
