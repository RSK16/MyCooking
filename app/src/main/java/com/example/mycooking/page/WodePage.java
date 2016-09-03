package com.example.mycooking.page;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.mycooking.page.BasePage;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class WodePage extends BasePage {
    public WodePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("我的");
        return textView;
    }
}
