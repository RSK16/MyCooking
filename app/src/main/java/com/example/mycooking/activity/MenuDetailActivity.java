package com.example.mycooking.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.bean.PageInfoBean;
import com.example.mycooking.bean.Recipe;
import com.example.mycooking.utils.BmobUtils;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MenuDetailActivity extends AppCompatActivity {

    private static final String TAG = "MenuDetailActivity";
    private String sort;//查询时的key
    private String key;//查询时的value

    private String[] pagetitlelist;
    private ViewPager vp_menudetailactivity_dishlist;
    private TabPageIndicator indicator_menudetailList_sort;

    //listview的列表
    ArrayList<ListView> listViewlist;

    ArrayList<List> recipeListList = new ArrayList<>();
    public Handler myhandler = new Handler(){

        public void handleMessage(Message msg){
            if(msg.what==1){
                PageInfoBean pageInfobean= (PageInfoBean) msg.obj;
                int mPosition = pageInfobean.mPosition;
                Log.i(TAG,"mPosition"+mPosition);
                ArrayList<Recipe> mRecipeList = pageInfobean.mRecipeList;
                //

                recipeListList.add(mRecipeList);
                Log.i(TAG,"mRecipeList.size()="+mRecipeList.size());

                ListView  listView = listViewlist.get(mPosition);
                DishlistAdapter dishlistAdapter = new DishlistAdapter(mPosition);
                listView.setAdapter(dishlistAdapter);
                //给listview设置Adpater
                //lv_menudetailactivity_dishlist.setAdapter(dishlistAdapter);


              //  indicator_menudetailList_sort.setViewPager(vp_menudetailactivity_dishlist);

            }
        }
    };
    private TextView tv_menudetailactivity_title;
    private BmobQuery<Recipe> bmobQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        getSupportActionBar().hide();

        //初始化Bmob ：2f78c11280ce16e4d17e9b7340caba38
        Bmob.initialize(this,"2f78c11280ce16e4d17e9b7340caba38");

        Intent intent = getIntent();
        sort = intent.getStringExtra("sort");//类别
        key = intent.getStringExtra("key");//要查询的字段
        pagetitlelist = intent.getStringArrayExtra("pagetitlelist");//indicator的内容

        //listview列表
        listViewlist = new ArrayList<>();

        //Log.i(TAG,"pagetitlelist:"+pagetitlelist[0]+pagetitlelist[1]+pagetitlelist[2]+"---");

        //标题
        tv_menudetailactivity_title = (TextView) findViewById(R.id.tv_menudetailactivity_title);
        tv_menudetailactivity_title.setText(sort);
        //lv_menudetailactivity_dishlist = (ListView) findViewById(R.id.lv_menudetailactivity_dishlist);

        //viewpager
        indicator_menudetailList_sort = (TabPageIndicator) findViewById(R.id.indicator_menudetailList_sort);
        vp_menudetailactivity_dishlist = (ViewPager) findViewById(R.id.vp_menudetailactivity_dishlist);

        vp_menudetailactivity_dishlist.setAdapter(new MenudetailPagerAdapter());
        indicator_menudetailList_sort.setViewPager(vp_menudetailactivity_dishlist);

        //查询数据
        //BmobUtils.select(key, sort);
       // selectrecipe(key, sort);
    }

    public  void selectrecipe(String key, String value, final int which) {

        Log.i(TAG,which+"=which,selectrecipe");
        //初始化Bmob
        //Bmob.initialize(context,APP_ID);
        bmobQuery = new BmobQuery<Recipe>();
        //查询条件 两种
        bmobQuery.addWhereEqualTo(key, value);
        bmobQuery.setLimit(100);

        //查询方法
        bmobQuery.findObjects(new FindListener<Recipe>() {
            @Override
            public void done(List<Recipe> list, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 查询数据成功,共" + list.size() + "条数据。");
                    //查询后的业务逻辑

                    //将当前page的位置和要显示的数据封装在一个类中
                    PageInfoBean pageInfoBean = new PageInfoBean(which, (ArrayList<Recipe>) list);

                    //子线程，执行完发消息给主线程去处理
                    Message message = new Message();
                    message.what=1;
                    message.obj=pageInfoBean;
                    myhandler.sendMessage(message);

                } else {
                    Log.i(TAG, "done: 查询数据失败" + e.getMessage());
                    return;
                }
            }
        });
        bmobQuery =null;

    }

    //返回
    public void back2menusort(View v){
        finish();
    }

    class MenudetailPagerAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {

            return pagetitlelist[position];//super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            //从上个页面带过来的所有分类的 数据
            return pagetitlelist.length;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object==view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

          /*  //测试
           TextView textView = new TextView(MenuDetailActivity.this);
            container.addView(textView);oh
            textView.setText(pagetitlelist[position]);
            return textView;*/

            //pagetitlelist
            //每个page对应不同的标题
            tv_menudetailactivity_title.setText(pagetitlelist[position]);

            Log.i(TAG,position+"=position");
            //1、用一个列表存放每个page对应的ListView
            View inflate = View.inflate(MenuDetailActivity.this, R.layout.menudetail_vp_page, null);
            ListView lv_menudetailactivity_dishlist = (ListView) inflate.findViewById(R.id.lv_menudetailactivity_dishlist);
            listViewlist.add(lv_menudetailactivity_dishlist);
            //2、查询数据列表，//3、发消息给主线程，根据改变的pagetitle,给listview设置Adapter

            selectrecipe(key, pagetitlelist[position],position);//将当前是第几个page传进去

            container.addView(inflate);
            return inflate;//super.instantiateItem(container, position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);//super.destroyItem(container, position, object);
        }
    }


    class DishlistAdapter extends BaseAdapter{

        BitmapUtils bitmapUtils;
        int listViewPosition;
        List<Recipe> list;
        //构造
        /*public DishlistAdapter() {
            bitmapUtils = new BitmapUtils(MenuDetailActivity.this);
        }*/

        public DishlistAdapter(int listViewPosition) {
            this.listViewPosition = listViewPosition;
            bitmapUtils = new BitmapUtils(MenuDetailActivity.this);
            list = recipeListList.get(listViewPosition);//获取数据recipelist的集合中的一个集合
        }

        @Override
        public int getCount() {
//            listView;
//            recipeList;
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(MenuDetailActivity.this, R.layout.item_menudetail_dishlist, null);

            ImageView iv_menudetailList_dishImage = (ImageView) inflate.findViewById(R.id.iv_menudetailList_dishImage);
            TextView tv_menudetailList_dishname = (TextView) inflate.findViewById(R.id.tv_menudetailList_dishname);
            ImageView iv_menudetailList_star1 = (ImageView) inflate.findViewById(R.id.iv_menudetailList_star1);
            ImageView iv_menudetailList_star2 = (ImageView) inflate.findViewById(R.id.iv_menudetailList_star2);
            ImageView iv_menudetailList_star3 = (ImageView) inflate.findViewById(R.id.iv_menudetailList_star3);
            ImageView iv_menudetailList_star4 = (ImageView) inflate.findViewById(R.id.iv_menudetailList_star4);
            ImageView iv_menudetailList_star5 = (ImageView) inflate.findViewById(R.id.iv_menudetailList_star5);
            TextView tv_menudetaillist_dishtime = (TextView) inflate.findViewById(R.id.tv_menudetaillist_dishtime);
            TextView tv_menudetaillist_dishinfo = (TextView) inflate.findViewById(R.id.tv_menudetaillist_dishinfo);

            Recipe recipe = list.get(position);
            String picUrl = recipe.getTitlepic();
            String title = recipe.getTitle();
            String make_time = recipe.getMake_time();
            String gongyi = recipe.getGongyi();
            String kouwei = recipe.getKouwei();
            String rate = recipe.getRate();
//            Log.i("Listview","title"+title+"make_time"+make_time);

            bitmapUtils.display(iv_menudetailList_dishImage,picUrl);
            tv_menudetailList_dishname.setText(title);
            tv_menudetaillist_dishtime.setText(make_time);
            tv_menudetaillist_dishinfo.setText(kouwei+"/"+gongyi);

            //五颗星
            if(rate.equals("5")){
                iv_menudetailList_star1.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star2.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star3.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star4.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star5.setImageResource(R.drawable.dish_rate_full_fill);
            }else if(rate.equals("4")){
                iv_menudetailList_star1.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star2.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star3.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star4.setImageResource(R.drawable.dish_rate_full_fill);
            }else if(rate.equals("3")){
                iv_menudetailList_star1.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star2.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star3.setImageResource(R.drawable.dish_rate_full_fill);
            }else if(rate.equals("2")){
                iv_menudetailList_star1.setImageResource(R.drawable.dish_rate_full_fill);
                iv_menudetailList_star2.setImageResource(R.drawable.dish_rate_full_fill);
            }else if(rate.equals("1")){
                iv_menudetailList_star1.setImageResource(R.drawable.dish_rate_full_fill);
            }

            return inflate;
        }
    }
}
