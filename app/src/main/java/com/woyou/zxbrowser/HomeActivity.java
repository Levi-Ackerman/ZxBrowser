package com.woyou.zxbrowser;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.woyou.baseconfig.ConstConfig;
import com.woyou.zxbrowser.home.HomeFragment;

public class HomeActivity extends AppCompatActivity {
    private WebViewModel mWebViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebViewModel = ViewModelProviders.of(this).get(WebViewModel.class);

        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstConfig.BUNDLE_KEY_WEBVIEW_MODEL, mWebViewModel);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("确认退出？")
                    .setPositiveButton("确定", (dialog, which) -> HomeActivity.super.onBackPressed())
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            BaseFragment topFragment = (BaseFragment) getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount() - 1);
            if (!topFragment.onBackPressed()) {
                super.onBackPressed();
            }
        }
    }
}
