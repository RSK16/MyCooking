package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.activity.BBVideoPlayer;
import com.example.mycooking.bean.UserUploadInfo;
import com.example.mycooking.utils.bitmap.LocalCacheUtils;
import com.example.mycooking.utils.bitmap.MemoryCacheUtils;
import com.example.mycooking.utils.bitmap.NetCacheUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class DiscoverPage extends BasePage {
    private static final String TAG = "DiscoverPage";
    private String result;
    private ArrayList<UserUploadInfo.FaxianListBean.VideoListBean> videoListBeen;
    LocalCacheUtils mLocalCacheUtils;
    MemoryCacheUtils mMemoryCacheUtils;
    String[] urlImage={ "http://admin.meishi.cc/article/upload/video_img/20160720/20160720100735_713.jpg"
            ,"http://site.meishij.net/article/video_img/20160726/20160726104613_450.jpg"
            ,"http://images.meishij.net/p/20160518/94f00cf4d0d80dc8c917760f62588fdc.jpg"
            ,"http://bmob-cdn-6039.b0.upaiyun.com/2016/09/02/7b3bd386406168c580fc408d60d26a70.jpg"};
    String[] nameImage={"麻辣烫","最美的味道","当美食遇见520","1"};
    int playtime=0;

    public DiscoverPage(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        //获取sdcard路径
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i(TAG, "=========" + absolutePath);
        File filepath = new File(absolutePath, "temp.txt");
        Log.i(TAG, "initData: " + filepath);
        //把asset里面的temp.txt文件保存到sdcard
        saveToSdcard(filepath);
        //获取sdcard中的temp.txt文件
        String fromSdcard = getFromSdcard(filepath);
        Log.i(TAG, "initData: " + fromSdcard.toString());
        //解析temp.txt文件
        Gson gson = new Gson();
        UserUploadInfo user_upload_info = gson.fromJson(fromSdcard.toString(), UserUploadInfo.class);
        Log.i(TAG, "initData: " + user_upload_info.getFaxian_list().size());
        //新建集合
        videoListBeen = new ArrayList<>();
        for (int i = 0; i < user_upload_info.getFaxian_list().size(); i++) {
            UserUploadInfo.FaxianListBean.VideoListBean video_list = user_upload_info.getFaxian_list().get(0).getVideo_list();
            videoListBeen.add(video_list);
        }
        Log.i(TAG, "initData: ============" + videoListBeen.size());

    }

    public void saveToSdcard(File filepath) {

        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            Log.i(TAG, "saveToSdcard: ======" + filepath);
            AssetManager assets = mActivity.getAssets();
            InputStream open = assets.open("temp.txt");
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = open.read(bytes, 0, 1024)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getFromSdcard(File filepath) {
        String str;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            StringBuffer stringBuffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
    //初始化发现的页面
    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.discover_page, null);
        ListView lv_discover_video_cooking = (ListView) inflate.findViewById(R.id.lv_discover_video_cooking);
        lv_discover_video_cooking.setAdapter(new MyAdapter());
        return inflate;
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
//             Log.i(TAG, "getCount: "+videoListBeen.size());
//             return videoListBeen.size();
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(mActivity, R.layout.discover_video_detail, null);
            TextView tv_discover_video_detail = (TextView) inflate.findViewById(R.id.tv_discover_video_detail);
            ImageView iv_discover_video_detail = (ImageView) inflate.findViewById(R.id.iv_discover_video_detail);
//             final String vurl = "http://10.0.2.2:8080/myphoto.mp4";
//            final String vurl = "http://10.0.2.2:8080/myphoto2.avi";
            final String vurl = "http://bmob-cdn-6039.b0.upaiyun.com/2016/09/06/e618866c405db19880b64bff34775150.mp4";
            NetCacheUtils netCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
            netCacheUtils.getBitmapFromNet(iv_discover_video_detail,urlImage[position]);
            String name = videoListBeen.get(position).getName();
            tv_discover_video_detail.setText(nameImage[position]);
            iv_discover_video_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playtime=playtime+1;
                    Intent intent = new Intent();
                    intent.setClass(mActivity, BBVideoPlayer.class);
                    intent.putExtra("url", vurl);
                    intent.putExtra("playtime",playtime+"");
                    intent.putExtra("cache",
                            Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/VideoCache/" + System.currentTimeMillis() + ".mp4");
                    mActivity.startActivity(intent);
                }
            });
            return inflate;
        }
    }

}
