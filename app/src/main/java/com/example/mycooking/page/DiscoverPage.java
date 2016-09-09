package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.ImageButton;

import com.example.mycooking.R;
import com.example.mycooking.activity.VideoActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class DiscoverPage extends BasePage {

    private String result;

    public DiscoverPage(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {

    }

    public void saveToSdcard(File filepath) {

        try {
            FileOutputStream fos = new FileOutputStream(filepath);
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
        ImageButton shiping = (ImageButton) inflate.findViewById(R.id.shiping);
        shiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, VideoActivity.class));
            }
        });
        return inflate;
    }


}
