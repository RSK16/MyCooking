package com.example.mycooking.page;

import android.app.Activity;
import android.view.View;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public abstract  class BasePage {
    public View mPageView; //就是viewpager里面的每一个page
    public Activity mActivity;
    public BasePage(Activity activity ) {
        mActivity =activity;
        mPageView=initView();
        initData(); //去修改当前这个page的tv_pageview_pageTitle等信息。
    }
    public abstract void initData() ;
    public abstract View initView() ;
}
