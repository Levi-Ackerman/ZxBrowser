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

import com.woyou.baseconfig.ConstConfig;
import com.woyou.zxbrowser.databinding.ActivityMainBinding;
import com.woyou.zxbrowser.download.DownloadFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private WebViewModel mWebViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mWebViewModel = ViewModelProviders.of(this).get(WebViewModel.class);
        mBinding.webview.setWebEventListener(mWebViewModel);
        observe(mWebViewModel);

        mBinding.addressBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                if (loadUrl()) return true;
            }
            return false;
        });
        mBinding.addressBar.setOnFocusChangeListener((v, hasFocus) -> {
            String show;
            if (hasFocus) {
                show = mWebViewModel.getUrl().getValue();
                mBinding.refreshTitlebar.setVisibility(View.GONE);
                mBinding.loadTitlebar.setVisibility(View.VISIBLE);
            } else {
                show = mWebViewModel.getTitle().getValue();
                mBinding.refreshTitlebar.setVisibility(View.VISIBLE);
                mBinding.loadTitlebar.setVisibility(View.GONE);
            }
            mBinding.addressBar.setText(show);
        });
        mBinding.back.setOnClickListener(v -> onBackPressed());
        mBinding.forward.setOnClickListener(v -> mBinding.webview.goForward());
        mBinding.refresh.setOnClickListener(v -> mBinding.webview.reload());
        mBinding.home.setOnClickListener(v -> enterHomePage());
        mBinding.refreshTitlebar.setOnClickListener(v -> mBinding.webview.reload());
        mBinding.loadTitlebar.setOnClickListener(v -> loadUrl());
        mBinding.download.setOnClickListener(v -> {
            DownloadFragment downloadFragment = new DownloadFragment();
            downloadFragment.setWebViewModel(mWebViewModel);
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(android.R.id.content, downloadFragment).commit();

        });
        enterHomePage();
        mBinding.webview.requestFocus();
    }

    private boolean loadUrl() {
        String url = mBinding.addressBar.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            String fixedUrl = mWebViewModel.handUrl(url);
            mBinding.webview.requestFocus();
            mBinding.webview.loadUrl(fixedUrl);
            totleShowSoftInput();
            return true;
        }
        return false;
    }

    private void enterHomePage() {
        mBinding.webview.loadUrl(ConstConfig.HOME_PAGE_URL);
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
        webViewModel.getFavicon().observe(this, (icon) -> mBinding.favicon.setImageBitmap(icon));
        webViewModel.getVideoSize().observe(this, size -> {
            if (size == null || size == 0) {
                mBinding.videoSize.setVisibility(View.INVISIBLE);
            } else {
                mBinding.videoSize.setText(String.valueOf(size));
                mBinding.videoSize.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        }
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
