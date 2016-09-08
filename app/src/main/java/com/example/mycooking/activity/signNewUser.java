package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.application.Constants;
import com.example.mycooking.bean.Userinfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class signNewUser extends Activity {

    private static final String TAG = "signNewUser";
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText nickname;
    private EditText job;
    private EditText brith;
    private RadioButton isman;
    private RadioButton iswoman;
    private Button submit;
    private Userinfo newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_new_user);
        //初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APPID);
        newUser = new Userinfo();
        getData();
        setData();
    }


    /**
     * 拿到各个要用到的控件，并设为成员变量供其他方法使用，好无聊
     */
    public void getData() {

        username = (EditText) findViewById(R.id.et_sign_username);
        password = (EditText) findViewById(R.id.et_sign_password);
        email = (EditText) findViewById(R.id.et_sign_email);
        nickname = (EditText) findViewById(R.id.et_sign_nickname);
        job = (EditText) findViewById(R.id.et_sign_job);
        brith = (EditText) findViewById(R.id.et_sign_brith);

        isman = (RadioButton) findViewById(R.id.rb_sign_isman);
        iswoman = (RadioButton) findViewById(R.id.rb_sign_iswoman);
        submit = (Button) findViewById(R.id.btn_sign_submit);

    }


    /**
     * 拿到注册页面的数据，并保存到bmob中
     */
    public void setData() {



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().equals("")) {
                    toast("填写你的用户名");
                    return;
                } else {
                    newUser.setUsername(username.getText().toString().trim());

                }
                if (password.getText().toString().trim().equals("")) {
                    toast("填写你的密码");
                    return;
                } else {
                    newUser.setPassword(password.getText().toString().trim());
                }
                newUser.setEmail(email.getText().toString().trim());
                newUser.setNickName(nickname.getText().toString().trim());
                newUser.setJob(job.getText().toString().trim());
                newUser.setBirth(brith.getText().toString().trim());

                if (isman.isChecked()) {
                    newUser.setSex(true);
                }
                if (iswoman.isChecked()) {
                    newUser.setSex(false);
                }
                newUser.signUp(new SaveListener<Userinfo>() {

                    @Override
                    public void done(Userinfo user, BmobException e) {
                        if (e == null) {
                            toast("注册成功");
                            Intent intent = new Intent(signNewUser.this, MyLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            toast("注册失败：");
                            Log.i(TAG, "注册失败: "+e);
                        }
                    }
                });
            }
        });
    }
    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
