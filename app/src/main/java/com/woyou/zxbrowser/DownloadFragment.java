package com.woyou.zxbrowser;


import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woyou.zxbrowser.databinding.FragmentDownloadBinding;

public class DownloadFragment extends Fragment {
    private FragmentDownloadBinding mBinding;
    private WebViewModel mWebViewModel;

    public void setWebViewModel(WebViewModel webViewModel) {
        mWebViewModel = webViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false);
        mBinding.downloadPanel.animate().translationY(-1 * getResources().getDimension(R.dimen.download_panel_height)).setDuration(200).start();
        mBinding.exitDownload.setOnClickListener(v -> exitDownload());
        mBinding.downloadBackground.setOnClickListener(v -> exitDownload());
        return mBinding.getRoot();
    }

    private void exitDownload() {
        mBinding.downloadPanel.animate()
                .translationY(getResources().getDimension(R.dimen.download_panel_height))
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }
}
