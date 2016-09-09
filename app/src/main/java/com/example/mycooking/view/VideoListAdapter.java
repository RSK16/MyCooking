package com.example.mycooking.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mycooking.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sujizhong on 16/6/30.
 */
public class VideoListAdapter extends BaseAdapter {

    private class ViewHodler {
        TextView mText_VideoTitle;
    }

    private List<VideoInfor> mLists = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public VideoListAdapter(Context cxt) {
        mLayoutInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<VideoInfor> data) {
        mLists.clear();
        String[] str = {"热门菜谱1", "热门菜谱2","热门菜谱3","热门菜谱4","热门菜谱5"
                ,"热门菜谱6","热门菜谱7","热门菜谱8","热门菜谱9","热门菜谱10","热门菜谱11","热门菜谱12","热门菜谱13"
                ,"热门菜谱14","热门菜谱15","热门菜谱16","热门菜谱17","热门菜谱18","热门菜谱19","热门菜谱20"};
        String[] path = {"http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4",
                "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/08/5b26c8fc4058564a80bc21aa802f246e.mp4"
        };
        for (int i = 0; i < 20; i++) {
            VideoInfor videoInfor = new VideoInfor();
            videoInfor.videoPath = path[i];
            videoInfor.videoTitle = str[i];
            mLists.add(videoInfor);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLists != null ? mLists.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mLists != null ? mLists.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.video_item, null);
            viewHodler = new ViewHodler();
            viewHodler.mText_VideoTitle = (TextView) convertView.findViewById(R.id.text_video_item_title);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        VideoInfor infor = mLists.get(position);
        viewHodler.mText_VideoTitle.setText(infor.videoTitle);
        return convertView;
    }

}
