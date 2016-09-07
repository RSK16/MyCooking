package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.activity.MyLoginActivity;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class WodePage extends BasePage implements View.OnClickListener {
    public WodePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        View mine = View.inflate(mActivity, R.layout.mine_activity, null);
        TextView login_sign = (TextView) mine.findViewById(R.id.mine_login);
        login_sign.setOnClickListener(this);

        return mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login:
                Intent intent = new Intent(mActivity, MyLoginActivity.class);
                mActivity.startActivity(intent);
                break;
        }
    }
}
