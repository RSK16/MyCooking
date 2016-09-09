package com.example.mycooking.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.bmob.v3.BmobUser;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private RelativeLayout rl_settingsactivity_version;
    private RelativeLayout rl_settingactivity_clearCache;
    private TextView tv_settingsactivity_cacheSize;
    private PackageManager packageManager;
    private String packageName;
    private String cache;

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what==1){
                String mcache = (String) msg.obj;
                if(cache!=null){
                    tv_settingsactivity_cacheSize.setVisibility(View.VISIBLE);
                    tv_settingsactivity_cacheSize.setText(cache);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        //拿到包管理器
        packageManager = getPackageManager();
        packageName = getPackageName();

        /*版本更新*/
        rl_settingsactivity_version = (RelativeLayout) findViewById(R.id.rl_settingsactivity_version);
        rl_settingsactivity_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "当前版本是最新版本", Toast.LENGTH_SHORT).show();
            }
        });

        getCache();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        Log.i(TAG, "onCreate: 2"+cache);
        tv_settingsactivity_cacheSize = (TextView) findViewById(R.id.tv_settingsactivity_cacheSize);
       /* if(cache!=null){
            tv_settingsactivity_cacheSize.setVisibility(View.VISIBLE);
            tv_settingsactivity_cacheSize.setText(cache);
        }*/

        //清除缓存
        rl_settingactivity_clearCache = (RelativeLayout) findViewById(R.id.rl_settingactivity_clearCache);
        rl_settingactivity_clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("提示")
                        .setMessage("确认清除缓存")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                clearall();
                               /*
                                Toast.makeText(SettingsActivity.this,"清理完毕",Toast.LENGTH_SHORT).show();*/
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

//    @Override
//    protected void onResume() {
//
//        if (cache!=null){
//            tv_settingsactivity_cacheSize.setVisibility(View.VISIBLE);
//            tv_settingsactivity_cacheSize.setText(cache);
//        }
//        super.onResume();
//    }


    @Override
    protected void onDestroy() {
        cache=null;
        super.onDestroy();
    }

    public void backToWodePage(View v){
        finish();
    }

    /*退出登录*/
    public void exit(View v){

        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否要退出")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BmobUser.logOut();   //清除缓存用户对象
                        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了


                        SettingsActivity.this.finish();//退出本页面
                        Toast.makeText(SettingsActivity.this,"您已退出登录",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("否", null)
                .show();

    }

    //拿到缓存
    class MyIPackageStatsObserver extends IPackageStatsObserver.Stub{

        //GetStatsCompleted :当缓存数据计算完毕之后，会调用到这个函数，把计算的缓存结果传递进来
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {

            //成功拿到缓存后
            if(succeeded){
                long cacheSize = pStats.cacheSize;
                String packageName = pStats.packageName;

                Log.i(TAG, "cacheSize:"+cacheSize);
                if(cacheSize>12288){
                    //默认的缓存大小为12KB
                    //将字节转为MB
                    cache = Formatter.formatFileSize(SettingsActivity.this, cacheSize);
                    Log.i(TAG, "cache"+cache);

                    //发消息
                    Message message = new Message();
                    message.what=1;
                    message.obj=cache;
                    handler.sendMessage(message);

                }
            }
        }
    }

    public  void getCache(){

        PackageManager packageManager1 = this.getPackageManager();
        String packageName1 = this.getPackageName();

        //1、拿到字节码文件对象
        Class<PackageManager> packageManagerClass = PackageManager.class;
        Method getPackageSizeInfo = null;

        try {
            //2、拿到方法 getMethod()
            getPackageSizeInfo = packageManagerClass.getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
            //3、调用方法
            getPackageSizeInfo.invoke(packageManager1,packageName1,new MyIPackageStatsObserver());

            Log.i(TAG, "getCache: "+cache);//null
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
   //清除缓存
    class MyIPackageDataObserver extends IPackageDataObserver.Stub{
            //所有缓存都清空之后，会回调这个callback
            @Override
            public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

                //在ui线程修改   UI的listview
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //将cache置为空
                        cache=null;
                        tv_settingsactivity_cacheSize.setVisibility(View.INVISIBLE);
                        Toast.makeText(SettingsActivity.this, "缓存清理完毕", Toast.LENGTH_LONG).show();
                    }
                });
            }
    }

    //清理缓存,系统去把应用的缓存清理掉
    public void clearall() {

        Class<PackageManager> packageManagerClass = PackageManager.class;
        try {
            Method freeStorageAndNotify = packageManagerClass.getMethod("freeStorageAndNotify", long.class, IPackageDataObserver.class);

           freeStorageAndNotify.invoke(packageManager,Long.MAX_VALUE,new MyIPackageDataObserver());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
