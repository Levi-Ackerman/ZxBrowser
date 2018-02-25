package com.woyou.zxbrowser.download;


import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyou.zxbrowser.R;
import com.woyou.zxbrowser.WebViewModel;
import com.woyou.zxbrowser.databinding.FragmentDownloadBinding;

import java.util.ArrayList;

public class DownloadFragment extends Fragment {
    private FragmentDownloadBinding mBinding;
    private WebViewModel mWebViewModel;
    private DownloadAdpter mAdpter;

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
        mAdpter = new DownloadAdpter(new ArrayList<>(mWebViewModel.getVideos()), getContext());
        mBinding.downloadListview.setAdapter(mAdpter);
        mBinding.downloadListview.setEmptyView(mBinding.getRoot().findViewById(android.R.id.empty));
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
