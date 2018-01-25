package com.woyou.zxbrowser;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.woyou.zxbrowser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.btnLoad.setOnClickListener((view)->{
            mBinding.webview.loadUrl(mBinding.addressBar.getText().toString());
        });
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
