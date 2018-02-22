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
                    mBinding.webview.loadUrl(url);
                    totleShowSoftInput();
                    return true;
                }
            }
            return false;
        });
        mBinding.back.setOnClickListener(v -> onBackPressed());
        mBinding.forward.setOnClickListener(v -> mBinding.webview.goForward());
        mBinding.refresh.setOnClickListener(v -> mBinding.webview.reload());
    }

    private void observe(WebViewModel webViewModel) {
        webViewModel.getUrl().observe(this,(url)-> mBinding.addressBar.setText(url));
        webViewModel.getProgress().observe(this, (newProgress) ->{
            mBinding.progressBar.setProgress(newProgress);
            if (newProgress == 0){
                mBinding.progressBar.setVisibility(View.VISIBLE);
            }else if (newProgress == 100) {
                mBinding.progressBar.setVisibility(View.GONE);
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
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    @Override
    protected void onDestroy() {
//        ZhugeSDK.getInstance().flush(getApplicationContext());
        super.onDestroy();
    }
}
