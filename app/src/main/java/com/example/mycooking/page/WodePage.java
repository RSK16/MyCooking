package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.activity.MsgNotifyActivity;
import com.example.mycooking.activity.MyLoginActivity;
import com.example.mycooking.activity.SettingsActivity;
import com.example.mycooking.activity.UpdateUserInfo;
import com.example.mycooking.application.Constants;
import com.example.mycooking.bean.Userinfo;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class WodePage extends BasePage implements View.OnClickListener {

    private static final String TAG = "WodePage";
    private TextView login_sign;
    private ImageButton mine_icon;
    private TextView mine_qianming;
    private String cache;
    private static  RelativeLayout rl_wodeactivity_settings;
    private static Activity activity;

    public WodePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        //初始化BmobSDK
        Bmob.initialize(mActivity, Constants.BMOB_APPID);

        View mine = View.inflate(mActivity, R.layout.mine_activity, null);
        login_sign = (TextView) mine.findViewById(R.id.mine_login);
        mine_icon = (ImageButton) mine.findViewById(R.id.mine_icon);
        mine_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, UpdateUserInfo.class));
            }
        });
        mine_qianming = (TextView) mine.findViewById(R.id.mine_qianming);
        login_sign.setOnClickListener(this);

        final Intent intent = mActivity.getIntent();
        String username = intent.getStringExtra("username");
        String jj = intent.getStringExtra("jj");

        //Log.i(TAG, "initView: "+username);
//        if ("jj".equals(jj)) {
//            login_sign.setText(username);
//        }

        Userinfo userinfo = BmobUser.getCurrentUser(Userinfo.class);
        if (userinfo != null) {
            login_sign.setText(userinfo.getUsername());
            mine_qianming.setText(userinfo.getNickName());
            String icon_url = userinfo.getIcon_url();
            Log.i(TAG, "initView: "+icon_url);
            BitmapUtils bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.display(mine_icon,icon_url);
        }

        //----------------------------------
        RelativeLayout rl_wodeactivity_msgnotify = (RelativeLayout) mine.findViewById(R.id.rl_wodeactivity_msgnotify);
        rl_wodeactivity_msgnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mActivity, MsgNotifyActivity.class);
                mActivity.startActivity(intent1);

            }
        });

        //显示缓存
        rl_wodeactivity_settings = (RelativeLayout) mine.findViewById(R.id.rl_wodeactivity_settings);
        rl_wodeactivity_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(mActivity, SettingsActivity.class);

                mActivity.startActivity(intent2);
            }
        });
        //---------------------------------
        return mine;
    }

    public static void setCache(){
        /*rl_wodeactivity_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(activity, SettingsActivity.class);
                getCache();
                intent2.putExtra("cache",cache);
                activity.startActivity(intent2);
            }
        });*/
    }
    /**
     * 监听服务器数据变化回调
     */
    public void listen() {
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                Log.d("bmob", "连接成功:"+rtd.isConnected());
            }

            @Override
            public void onDataChange(JSONObject data) {
                Log.d("bmob", "("+data.optString("action")+")"+"数据："+data);

                //rtd.subTableUpdate("_User");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login:
                Intent intent = new Intent(mActivity, MyLoginActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();
                break;
        }
    }


}
