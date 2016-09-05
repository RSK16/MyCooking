package com.example.mycooking.activity.shangchuangcaipu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.mycooking.R;

public class EditliaoActivity extends AppCompatActivity {
    private EditText et_liao_num;
    private EditText et_liao_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editliao);
        initView();
    }

    private void initView() {
        et_liao_num = (EditText) findViewById(R.id.et_liao_num);
        et_liao_name = (EditText) findViewById(R.id.et_liao_name);
    }

    public void done(View view) {
        switch (view.getId()) {
            case R.id.yes:
                String liao_name = et_liao_name.getText().toString();
                String liao_num = et_liao_num.getText().toString();
                if (liao_name.equals("") || liao_num.equals("")) {
                    return;
                } else {
                    Intent intent = new Intent(this, LoadTwoActivity.class);
                    intent.putExtra("liao_name", liao_name);
                    intent.putExtra("liao_num", liao_num);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.pre:
                finish();
                break;
        }
    }
}
