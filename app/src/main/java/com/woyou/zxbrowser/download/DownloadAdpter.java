package com.woyou.zxbrowser.download;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woyou.zxbrowser.R;
import com.woyou.zxbrowser.VideoInfo;

import java.util.ArrayList;


public class DownloadAdpter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<VideoInfo> mVideoInfos;

    public DownloadAdpter(@NonNull ArrayList<VideoInfo> videoInfos, Context context) {
        mVideoInfos = videoInfos;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mVideoInfos.size();
    }

    @Override
    public VideoInfo getItem(int position) {
        return mVideoInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoInfo videoInfo = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_download_video, null);
            viewHolder = new ViewHolder();
            viewHolder.mUrl = convertView.findViewById(R.id.video_item_url);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mUrl.setText(videoInfo.getUrl());
        return convertView;
    }

    class ViewHolder {
        TextView mUrl;
    }
}
