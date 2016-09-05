package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.mycooking.R;
import com.example.mycooking.activity.shangchuangcaipu.LoadOneActivity;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class MallPage extends BasePage {
    public MallPage(Activity activity) {
        super(activity);
    }
    @Override
    public void initData() {}

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.shopping_mall, null);
        Button btn_load1 = (Button) view.findViewById(R.id.btn_load1);
        btn_load1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,LoadOneActivity.class));
            }
        });
        return view;
    }
}