package com.woyou.zxbrowser;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woyou.zxbrowser.databinding.FragmentDownloadBinding;

public class DownloadFragment  extends Fragment{
    private FragmentDownloadBinding mBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentDownloadBinding mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_download,container,false);

        return mBinding.getRoot();
    }
}
