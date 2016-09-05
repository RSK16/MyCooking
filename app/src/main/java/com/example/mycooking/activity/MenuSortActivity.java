package com.example.mycooking.activity;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mycooking.R;
import com.example.mycooking.bean.Categories;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MenuSortActivity extends AppCompatActivity {

    private static final String TAG = "MenuSortActivity";
    private Categories categories;

    private ListView lv_menusortactivity_list;
    private GridView gv_menuSortactivity_gridview;
    int[] menuSortImageList = new int[]{
            R.drawable.cate_fenlei_5,R.drawable.cate_changjing_5, R.drawable.cate_sancan_5,
            R.drawable.cate_jiachang_5,R.drawable.cate_renqun_5, R.drawable.cate_zhushi_5,
            R.drawable.cate_hongbei_5,R.drawable.cate_xiaochi_5, R.drawable.cate_waiguo_5,
            R.drawable.cate_zhonghua_5,R.drawable.cate_shicai_5, R.drawable.cate_jibing_5,
            R.drawable.cate_gongneng_5,R.drawable.cate_zhangfu_5, R.drawable.cate_cuju_5,
            R.drawable.cate_gongyi_5,R.drawable.cate_kouwei_5,R.drawable.cate_shijian_5};
    //要显示的数据列表
    private List<Categories.CategoryBean> categoryBeanList;
    private MenuSortGridviewAdapter menuSortGridviewAdapter;
    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sort);

        getSupportActionBar().hide();
        lv_menusortactivity_list     = (ListView) findViewById(R.id.lv_menusortactivity_list);
        gv_menuSortactivity_gridview = (GridView) findViewById(R.id.gv_menuSortactivity_gridview);

        //将本地的json 解析放入java bean
        parseJsonFromLocal(this,"category.json");
        categoryBeanList = categories.getCategory();//不同分类 的集合

        //Log.i(TAG,categoryBean.getT()+"----"+categoryBean.getD().get(0).getT());

        lv_menusortactivity_list.setAdapter(new MenusortListviewAdapter());
        menuSortGridviewAdapter = new MenuSortGridviewAdapter();
        gv_menuSortactivity_gridview.setAdapter(menuSortGridviewAdapter);

        //-------给listview的item设点击事件
        lv_menusortactivity_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //切换 gridview的数据集
                currentPosition = position;
                menuSortGridviewAdapter.notifyDataSetChanged();
               // gv_menuSortactivity_gridview.setAdapter(menuSortGridviewAdapter);
            }
        });

        //给GridView设item的点击事件，查看不同分类下的详细内容
        gv_menuSortactivity_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void back(View v){
        finish();
    }

    public void parseJsonFromLocal(Context context, String jsonfileName) {

        //将本地的json文件 解析放入Categories  java bean 中
        File file = new File("/data/data/" + context.getPackageName() + "/" + jsonfileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line="";
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }

            Log.i(TAG,stringBuilder.toString());

            Gson gson = new Gson();
            categories = gson.fromJson(stringBuilder.toString(), Categories.class);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }



    }

    //listView的适配器
    class MenusortListviewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return categoryBeanList.size();
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

            View inflate = View.inflate(MenuSortActivity.this, R.layout.item_menusortlistview, null);
            ImageView iv_menusort_listview = (ImageView) inflate.findViewById(R.id.iv_menusort_listview);
            TextView tv_menusort_listview = (TextView) inflate.findViewById(R.id.tv_menusort_listview);

            iv_menusort_listview.setImageResource(menuSortImageList[position]);
            tv_menusort_listview.setText(categoryBeanList.get(position).getT());
            return inflate;
        }
    }
    //GridView的适配器
    class MenuSortGridviewAdapter extends BaseAdapter{

       //用Bitmaputil显示图片
        BitmapUtils bitmapUtils;
        //构造
        public MenuSortGridviewAdapter() {
            bitmapUtils = new BitmapUtils(MenuSortActivity.this);
        }

        //每个分类下的所有菜谱的集合
        private List<Categories.CategoryBean.DBean> d;

        @Override
        public int getCount() {
            d = categoryBeanList.get(currentPosition).getD();
            return categoryBeanList.get(currentPosition).getD().size();
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

            View inflate = View.inflate(MenuSortActivity.this, R.layout.item_menusortgridview, null);

            ImageView iv_menusort_gridview = (ImageView) inflate.findViewById(R.id.iv_menusort_gridview);
            TextView tv_menusort_gridview = (TextView) inflate.findViewById(R.id.tv_menusort_gridview);

            String url = d.get(position).getI();
            String title = d.get(position).getT();
            if (url!=null){
                Log.i(TAG,title);
                Log.i(TAG,url);
                bitmapUtils.display(iv_menusort_gridview,url);
            }else{
                //如果没有要显示的图片，就将ImageView去掉
                //动态设置图片的高度和宽度:使用LayoutParams的对象来进行设置
                ViewGroup.LayoutParams params = iv_menusort_gridview.getLayoutParams();
                params.height = 0;
            }

            tv_menusort_gridview.setText(title);

            return inflate;
        }
    }
}
