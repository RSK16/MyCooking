package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.mycooking.R;
import com.example.mycooking.activity.shangchuangcaipu.LoadOneActivity;
import com.example.mycooking.page.BasePage;
import com.example.mycooking.page.SuggestPage;
import com.example.mycooking.page.DiscoverPage;
import com.example.mycooking.view.NoScrollViewPager;
import com.example.mycooking.page.WodePage;
import com.example.mycooking.page.topicPage;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private RadioGroup rg_group;
    private NoScrollViewPager vp_main_shoppingmall;
    List<BasePage> pageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Bmob//修改一下
        Bmob.initialize(this,"2f78c11280ce16e4d17e9b7340caba38");

        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        vp_main_shoppingmall =  (NoScrollViewPager) findViewById(R.id.vp_main_shoppingmall);
        pageList.add(new SuggestPage(this));
        pageList.add(new DiscoverPage(this));
        pageList.add(new topicPage(this));
        final WodePage wodePage = new WodePage(this);
        pageList.add(wodePage);

        vp_main_shoppingmall.setAdapter(new MyContentAdatper());
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {

                 switch (checkedId){
                     case R.id.rb_main_recommend:
                        vp_main_shoppingmall.setCurrentItem(0,false);
                        break;
                     case R.id.rb_main_discover:
                        vp_main_shoppingmall.setCurrentItem(1,false);
                        break;
                     case R.id.rb_main_topic:
                        vp_main_shoppingmall.setCurrentItem(2,false);
                        break;
                     case R.id.rb_main_wode:
                        vp_main_shoppingmall.setCurrentItem(3,false);
                        //wodePage.setCache();
                        break;}
             }
        });
        //pageList.get(0).initView();
        rg_group.check(R.id.rb_main_recommend);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            Log.i(TAG, "onActivityResult: ");
//            pageList.get(3).initView();
//            rg_group.check(R.id.rb_main_wode);
//            vp_main_shoppingmall.setCurrentItem(3, false);
//        } else {
//            Log.i(TAG, "onActivityResult: 失败");
//        }
//    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        String jj = intent.getStringExtra("jj");
        if ("jj".equals(jj)) {
            Log.i(TAG, "onActivityResult: ");
            pageList.get(3).initView();
            rg_group.check(R.id.rb_main_wode);
            vp_main_shoppingmall.setCurrentItem(3, false);
        } else {
            Log.i(TAG, "onActivityResult: 失败");
        }

        super.onResume();
    }

    class MyContentAdatper extends PagerAdapter{

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePage basePage =  pageList.get(position);
            container.addView(basePage.mPageView);
            return basePage.mPageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
          container.removeView((View) object);
            //  super.destroyItem(container, position, object);
        }
    }
    public void load(View view) {
        startActivity(new Intent(this,LoadOneActivity.class));
    }

}
