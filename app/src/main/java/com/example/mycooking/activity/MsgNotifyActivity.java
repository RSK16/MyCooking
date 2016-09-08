package com.example.mycooking.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mycooking.R;

public class MsgNotifyActivity extends AppCompatActivity {

    private static final String TAG = "MsgNotifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_notify);


        RelativeLayout rl_msgnotifyactivity_news = (RelativeLayout) findViewById(R.id.rl_msgnotifyactivity_news);
        RelativeLayout rl_msgnotifyactivity_comment = (RelativeLayout) findViewById(R.id.rl_msgnotifyactivity_comment);
        RelativeLayout rl_msgnotifyactivity_praise = (RelativeLayout) findViewById(R.id.rl_msgnotifyactivity_praise);
        RelativeLayout rl_msgnotifyactivity_collection = (RelativeLayout) findViewById(R.id.rl_msgnotifyactivity_collection);
        RelativeLayout rl_msgnotifyactivity_feedback = (RelativeLayout) findViewById(R.id.rl_msgnotifyactivity_feedback);

        rl_msgnotifyactivity_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_msgnotifyactivity_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_msgnotifyactivity_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_msgnotifyactivity_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rl_msgnotifyactivity_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //忽略
    private void ignore(View v){

        Log.i(TAG,"ignore");
    }


}
