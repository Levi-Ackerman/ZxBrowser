package com.woyou.zxbrowser.download;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.woyou.zxbrowser.VideoInfo;

import java.util.ArrayList;


public class DownloadAdpter extends BaseAdapter {
    private ArrayList<VideoInfo> mVideoInfos;

    public DownloadAdpter(@NonNull ArrayList<VideoInfo> videoInfos) {
        mVideoInfos = videoInfos;
    }

    @Override
    public int getCount() {
        return mVideoInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
