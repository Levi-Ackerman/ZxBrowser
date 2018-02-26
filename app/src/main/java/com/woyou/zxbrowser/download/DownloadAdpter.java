package com.woyou.zxbrowser.download;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyou.zxbrowser.R;
import com.woyou.zxbrowser.VideoInfo;
import com.woyou.zxbrowser.databinding.ItemDownloadVideoBinding;

import java.util.ArrayList;


public class DownloadAdpter extends RecyclerView.Adapter<DownloadAdpter.ViewHolder> {
    private final Context mContext;
    private ArrayList<VideoInfo> mVideoInfos;

    public DownloadAdpter(@NonNull ArrayList<VideoInfo> videoInfos, Context context) {
        mVideoInfos = videoInfos;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ItemDownloadVideoBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_download_video,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoInfo videoInfo = mVideoInfos.get(position);
        holder.bind(videoInfo);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mVideoInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemDownloadVideoBinding mBinding;

        public ViewHolder(ItemDownloadVideoBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(VideoInfo videoInfo) {
            mBinding.setVideoInfo(videoInfo);
            mBinding.executePendingBindings();
        }
    }
}
