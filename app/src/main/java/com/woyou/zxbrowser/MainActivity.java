package com.woyou.zxbrowser;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.woyou.zxbrowser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        WebViewModel webViewModel = ViewModelProviders.of(this).get(WebViewModel.class);
        mBinding.webview.setWebEventListener(webViewModel);
        observe(webViewModel);

        mBinding.addressBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                String url = mBinding.addressBar.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    mBinding.webview.requestFocus();
                    mBinding.webview.loadUrl(url);
                    totleShowSoftInput();
                    return true;
                }
            }
            return false;
        });
        mBinding.addressBar.setOnFocusChangeListener((v, hasFocus) -> {
            String show;
            if (hasFocus) {
                show = webViewModel.getUrl().getValue();
            } else {
                show = webViewModel.getTitle().getValue();
            }
            if (!TextUtils.isEmpty(show)) {
                mBinding.addressBar.setText(show);
            }
        });
        mBinding.back.setOnClickListener(v -> onBackPressed());
        mBinding.forward.setOnClickListener(v -> mBinding.webview.goForward());
        mBinding.refresh.setOnClickListener(v -> mBinding.webview.reload());
        mBinding.home.setOnClickListener(v -> enterHomePage());
        mBinding.refreshTitlebar.setOnClickListener(v -> mBinding.webview.reload());
        enterHomePage();
    }

    private void enterHomePage() {
        mBinding.webview.loadUrl("file:///android_asset/home.html");
    }


    private void observe(WebViewModel webViewModel) {
        webViewModel.getUrl().observe(this, (url) -> {
            if (mBinding.addressBar.isFocused()) {
                mBinding.addressBar.setText(url);
            }
        });
        webViewModel.getProgress().observe(this, (newProgress) -> {
            mBinding.progressBar.setProgress(newProgress);
            if (newProgress == 0) {
                mBinding.progressBar.setVisibility(View.VISIBLE);
            } else if (newProgress == 100) {
                mBinding.progressBar.setVisibility(View.GONE);
            }
        });
        webViewModel.getTitle().observe(this, (title) -> {
            if (!mBinding.addressBar.isFocused()) {
                mBinding.addressBar.setText(title);
            }
        });
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

    public void totleShowSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
//        ZhugeSDK.getInstance().flush(getApplicationContext());
        super.onDestroy();
    }
}
