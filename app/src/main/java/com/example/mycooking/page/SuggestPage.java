package com.example.mycooking.page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.activity.MsgNotifyActivity;
import com.example.mycooking.activity.shangchuangcaipu.LoadOneActivity;
import com.example.mycooking.view.RefreshScLinearLayout;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class SuggestPage extends BasePage {

    private static final String TAG = "SuggestPage";
    public View suggestPageView;
    private ScrollView scrollView_suggestpage;
    private ImageButton bt_suggestPagetitle_left;

    public SuggestPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {

        //测试
        /*TextView textView = new TextView(mActivity);
        textView.setText("推荐");
        return textView;*/

        suggestPageView = View.inflate(mActivity, R.layout.suggest_page_view, null);

        ImageButton bt_suggestpage_totop = (ImageButton) suggestPageView.findViewById(R.id.bt_suggestpage_totop);
        bt_suggestpage_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshScLinearLayout.fun(); //回到顶部
            }
        });

        RefreshScLinearLayout ll_refreshactivity_refresh = (RefreshScLinearLayout) suggestPageView.findViewById(R.id.ll_refreshactivity_refresh);
        bt_suggestPagetitle_left = (ImageButton) suggestPageView.findViewById(R.id.bt_suggestPagetitle_left);
        bt_suggestPagetitle_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 上传菜谱
                 * @param view
                 */
                mActivity.startActivity(new Intent(mActivity, LoadOneActivity.class));
            }
        });

        ImageButton bt_suggestPagetitle_right = (ImageButton) suggestPageView.findViewById(R.id.bt_suggestPagetitle_right);
        bt_suggestPagetitle_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, MsgNotifyActivity.class));
            }
        });

        ll_refreshactivity_refresh.setRefreshListener(new RefreshScLinearLayout.RefreshListener() {
            public static final String TAG = "RefreshCallBack";

            @Override
            public void doInBackground() {

                //刷新：在子线程
                //Log.i(TAG,"doInBackground");
            }

            @Override
            public void complete() {

                //Log.i(TAG,"complete");
            }
        });

        /*scrollView_suggestpage = (ScrollView) suggestPageView.findViewById(R.id.scrollView_suggestpage);
        Button bt_suggestpage_sort = (Button) suggestPageView.findViewById(R.id.bt_suggestpage_sort);
        Button bt_suggestpage_Rank = (Button) suggestPageView.findViewById(R.id.bt_suggestpage_Rank);

        CirclePageIndicator indicator_suggestmeal = (CirclePageIndicator) suggestPageView.findViewById(R.id.indicator_suggestmeal);
        ViewPager vp_suggestPage_meal = (ViewPager) suggestPageView.findViewById(R.id.vp_suggestPage_meal);
        vp_suggestPage_meal.setAdapter(new MysuggestmealPagerAdapter());

        indicator_suggestmeal.setViewPager(vp_suggestPage_meal);
        Log.i(TAG,indicator_suggestmeal.toString());


        //菜谱分类
        bt_suggestpage_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MenuSortActivity.class);
                mActivity.startActivity(intent);
            }
        });
        //排行榜
        bt_suggestpage_Rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MenuRankActivity.class);
                mActivity.startActivity(intent);
            }
        });*/

        return suggestPageView;
    }

    class MysuggestmealPagerAdapter extends PagerAdapter {

        String[] whichMeal = new String[]{"早餐","午餐","下午茶","晚餐","夜宵"};

        String[] mealInfo = new String[]{"早餐早餐早餐","午餐午餐午餐","下午茶下午茶下午茶","晚餐晚餐晚餐","夜宵夜宵夜宵"};
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

            View page = View.inflate(mActivity, R.layout.viewpager_meal, null);
            TextView tv_vpMeal_meal = (TextView) page.findViewById(R.id.tv_vpMeal_meal);
            TextView tv_vpMeal_info = (TextView) page.findViewById(R.id.tv_vpMeal_info);

            tv_vpMeal_meal.setText(whichMeal[position]);
            tv_vpMeal_info.setText(mealInfo[position]);
            //将view放入容器中
            container.addView(page);
            //将object返回
            return page;//super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {


            container.removeView((View) object);

        }
    }



}
