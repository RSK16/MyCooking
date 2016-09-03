package com.example.mycooking.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.mycooking.R;
import com.example.mycooking.page.BasePage;
import com.example.mycooking.page.MallPage;
import com.example.mycooking.page.CommendPage;
import com.example.mycooking.page.DiscoverPage;
import com.example.mycooking.view.NoScrollViewPager;
import com.example.mycooking.page.WodePage;
import com.example.mycooking.page.topicPage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
//ghjg
    private RadioGroup rg_group;
    private NoScrollViewPager vp_main_shoppingmall;
    List<BasePage> pageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg_group = (RadioGroup) findViewById(R.id.rg_group);
        vp_main_shoppingmall =  (NoScrollViewPager) findViewById(R.id.vp_main_shoppingmall);
        pageList.add(new CommendPage(this));
        pageList.add(new DiscoverPage(this));
        pageList.add(new MallPage(this));
        pageList.add(new topicPage(this));
        pageList.add(new WodePage(this));

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
                case R.id.rb_main_shopping:
                    vp_main_shoppingmall.setCurrentItem(2,false);
                    break;
                case R.id.rb_main_topic:
                    vp_main_shoppingmall.setCurrentItem(3,false);
                    break;
                case R.id.rb_main_wode:
                    vp_main_shoppingmall.setCurrentItem(4,false);
                    break;
            }
    }
});

    }

    class MyContentAdatper extends PagerAdapter{

        @Override
        public int getCount() {
            return 5;
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


}
