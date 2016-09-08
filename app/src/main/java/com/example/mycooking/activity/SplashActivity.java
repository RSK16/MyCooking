package com.example.mycooking.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mycooking.R;
import com.example.mycooking.utils.CopyJsonUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

//        Bmob.initialize(this, "2f78c11280ce16e4d17e9b7340caba38");

        BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        .setApplicationId("2f78c11280ce16e4d17e9b7340caba38")
        ////请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(100)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        .build();
        Bmob.initialize(config);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }).start();

        CopyJsonUtils.copyJson(this,"category.json");
    }
}
