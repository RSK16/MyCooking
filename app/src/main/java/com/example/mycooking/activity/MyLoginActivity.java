package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.application.Constants;
import com.example.mycooking.page.WodePage;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MyLoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MyLoginActivity";
    private ImageButton btn_qq;
    private ImageButton btn_weixin;
    private Button btn_login;
    private Button btn_register;
    private EditText et_account;
    private EditText et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        //初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APPID);
        initView();
//        BmobUser user = BmobUser.getCurrentUser();
//        if (user != null) {//用户已经登录
//            Intent intent = new Intent(MyLoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        } else {//调整到注册页面
//            startActivity(new Intent(MyLoginActivity.this,signNewUser.class));
//        }
    }

    private void initView() {
        et_account = (EditText)findViewById(R.id.et_account);
        et_pwd = (EditText)findViewById(R.id.et_pwd);

        btn_qq = (ImageButton)findViewById(R.id.btn_qq);
        btn_weixin = (ImageButton)findViewById(R.id.btn_weixin);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);

    }

    String account,pwd;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登陆
                account = et_account.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if(account.equals("")){
                    toast("填写你的用户名");
                    return;
                }

                if(pwd.equals("")){
                    toast("填写你的密码");
                    return;
                }

                BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setPassword(pwd);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            Intent intent = new Intent(MyLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            toast("登陆失败："+e);
                        }
                    }
                });
                break;
            case R.id.btn_register://注册
                startActivity(new Intent(MyLoginActivity.this,signNewUser.class));
                finish();
                break;
            case R.id.btn_qq://QQ授权登录
                //qqAuthorize();
                break;
            case R.id.btn_weixin:
                //微信登陆，文档可查看：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&lang=zh_CN&token=0ba3e6d1a13e26f864ead7c8d3e90b15a3c6c34c
                //发起微信登陆授权的请求
                break;
            default:
                break;
        }
    }
    private void toast(String msg){
        Toast.makeText(MyLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
