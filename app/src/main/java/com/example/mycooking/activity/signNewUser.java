package com.example.mycooking.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.Userinfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class signNewUser extends Activity {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText nickname;
    private EditText job;
    private EditText brith;
    private RadioButton isman;
    private RadioButton iswoman;
    private Button login;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_new_user);


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
        login = (Button) findViewById(R.id.btn_sign_login);

    }


    /**
     * 拿到注册页面的数据，并保存到bmob中
     */
    public void setData() {
        final Userinfo newUser = new Userinfo();
        newUser.setUsername(username.getText().toString().trim());
        newUser.setPassword(password.getText().toString().trim());
        newUser.setEmail(email.getText().toString().trim());
        newUser.setNickName(nickname.getText().toString().trim());
        newUser.setJob(job.getText().toString().trim());
        newUser.setBrith(brith.getText().toString().trim());

        if (isman.isChecked()) {
            newUser.setSex(true);
        }
        if (iswoman.isChecked()) {
            newUser.setSex(false);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newUser.getUsername() != null || newUser.getPassword() != null) {
                    newUser.signUp(new SaveListener<Userinfo>() {
                        @Override
                        public void done(Userinfo userinfo, BmobException e) {
                            Toast.makeText(signNewUser.this, "注册成功！！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(signNewUser.this, "至少把用户名跟密码填上", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /**
         * 如果点击登陆的话，就弹出一个对话框来输入用户名和密码
         */
        login.setOnClickListener(new View.OnClickListener() {
                                     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                                     @Override
                                     public void onClick(View v) {
                                         final AlertDialog.Builder builder = new AlertDialog.Builder(signNewUser.this);
                                         View view = View.inflate(getApplicationContext(), R.layout.activity_login, null);
                                         builder.setView(view);
                                         Button submit = (Button) view.findViewById(R.id.btn_pswinput_submit);
                                         Button cancel = (Button) view.findViewById(R.id.btn_pswinput_cancel);
                                         final EditText username = (EditText) view.findViewById(R.id.et_login_username);
                                         final EditText password = (EditText) view.findViewById(R.id.et_login_password);
                                         final AlertDialog show = builder.show();

                                         submit.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String login_username = username.getText().toString().trim();
                                                 String login_password = password.getText().toString().trim();

                                                 Userinfo login_info = new Userinfo();
                                                 login_info.setUsername(login_username);
                                                 login_info.setPassword(login_password);

                                                 login_info.login(new SaveListener<Userinfo>() {
                                                     @Override
                                                     public void done(Userinfo userinfo, BmobException e) {
                                                         if (e == null) {
                                                             show.dismiss();
                                                             finish();
                                                             //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                                         } else {
                                                             Toast.makeText(signNewUser.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 });

                                             }
                                         });

                                         cancel.setOnClickListener(new View.OnClickListener() {
                                                                       @Override
                                                                       public void onClick(View v) {
                                                                           show.dismiss();
                                                                       }
                                                                   }
                                         );
                                     }
                                 }
        );

    }
}
