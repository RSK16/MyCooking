package com.example.mycooking.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mycooking.R;



public class UpdateUserInfo extends AppCompatActivity implements View.OnClickListener {

    private ImageButton update_ib_icon;
    private TextView update_tv_username;
    private TextView update_tv_sex;
    private TextView update_tv_birth;
    private TextView update_tvƒ_address;
    private TextView update_tv_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        initView();
        initDate();
    }

    private void initDate() {

    }

    private void initView() {
        update_ib_icon = (ImageButton) findViewById(R.id.update_ib_icon);
        update_ib_icon.setOnClickListener(this);
        update_tv_username = (TextView) findViewById(R.id.update_tv_username);
        update_tv_username.setOnClickListener(this);
        update_tv_sex = (TextView) findViewById(R.id.update_tv_sex);
        update_tv_sex.setOnClickListener(this);
        update_tv_birth = (TextView) findViewById(R.id.update_tv_birth);
        update_tv_birth.setOnClickListener(this);
        update_tvƒ_address = (TextView) findViewById(R.id.update_tv_address);
        update_tvƒ_address.setOnClickListener(this);
        update_tv_job = (TextView) findViewById(R.id.update_tv_job);
        update_tv_job.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_ib_icon:
                break;
            case R.id.update_tv_username:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final View dialogView = View.inflate(this, R.layout.edit_username, null);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.update_tv_sex:
                break;
            case R.id.update_tv_birth:
                break;
            case R.id.update_tv_address:
                break;
            case R.id.update_tv_job:
                break;

        }
    }
}
