package com.example.mycooking.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mycooking.R;

public class Shopping_NewNotifaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping__new_notifaction);
    }
    //回退
    public void back_notification(View v){
       finish();
    }
}
