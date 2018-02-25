package com.woyou.zxbrowser.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.woyou.baseconfig.ConstConfig;
import com.woyou.util.InputMethodUtil;
import com.woyou.zxbrowser.BaseFragment;
import com.woyou.zxbrowser.R;
import com.woyou.zxbrowser.WebViewModel;
import com.woyou.zxbrowser.databinding.ActivityMainBinding;

public class HomeFragment extends BaseFragment {
    private WebViewModel mWebViewModel;
    private ActivityMainBinding mBinding;

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        mWebViewModel = bundle.getParcelable(ConstConfig.BUNDLE_KEY_WEBVIEW_MODEL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false);
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
        enterHomePage();
        mBinding.webview.requestFocus();
        return mBinding.getRoot();
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

    private void enterHomePage() {
        mBinding.webview.loadUrl(ConstConfig.HOME_PAGE_URL);
    }

    private boolean loadUrl() {
        String url = mBinding.addressBar.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            String fixedUrl = mWebViewModel.handUrl(url);
            mBinding.webview.requestFocus();
            mBinding.webview.loadUrl(fixedUrl);
            InputMethodUtil.totleShowSoftInput(getActivity());
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
