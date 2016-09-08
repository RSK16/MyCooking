package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.example.mycooking.R;
import com.example.mycooking.bean.Buzhou;
import com.example.mycooking.bean.Recipe;
import com.example.mycooking.utils.SharePreferenceUtils;
import com.example.mycooking.view.AutoViewPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import rx.Subscription;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CookMenuDetailActivity extends Activity implements View.OnClickListener {

    private static final String COOKMENUDETAIL = "CookMenuDetailActivity";


    Activity mActivity;
    private String[] theUrl;
    private ArrayList<String> theHeadUrl;
    private StickyListHeadersListView stickyList;
    private ImageButton ib_cookMenuDetail_coll_icon;
    private TextView tv_cookMenuDetail_topTheCookName;


    private RelativeLayout rl_cookMenuDetail_leftTopBack;
    private ImageButton ib_cookMenuDetail_leftTopBack;


    private static final String MYDBID ="b891dc7e1e";
    private static  boolean GETDATAFROMSERVER  ;

//--------------------------获取到的数据--------------------------------------------

    Recipe recipe;
    String buzhou;
    private String[] theurl0;
    private String[] theDescription0;
    private boolean chengpinstep;
    private ArrayList<String> theheadurl0;
    private String title;
    private String user_name;
    private String avatar;//作者头像图像
    private ImageView iv_cookMenuDetail_the_picOf_author;


    //--------------------------获取到的数据--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu_detail);

        mActivity=this;



        stickyList = (StickyListHeadersListView)mActivity. findViewById(R.id.lv_stickyListHeaders);
//--------------------------获取数据--------------------------------------------



         final Handler handler=new Handler(){
             @Override
             public void handleMessage(Message msg) {

                 if(msg.what==4){
                     Log.e("````````````","gogoogogoog");
                 }
                 Log.e("````````````","wowowowowoo");

                 if(recipe!=null){
                     Log.e("````````````","lolololoo");
                 }

                 buzhou = recipe.getZuofa();
//---------------得到数据后 非常happy----------------------
                 getBuzhou();
                 initTheMainListView();

//------------------设置headView-----------------------------
                 title = recipe.getTitle();//标题
                 String onclick = recipe.getOnclick();
                 String fav_num = recipe.getFav_num();
                 String author = recipe.getAuthor();//作者的json
                 String smalltext = recipe.getSmalltext();


                 try {
                     JSONObject jsonObject = new JSONObject(author);
                     user_name = (String) jsonObject.get("user_name");
                     avatar = (String) jsonObject.get("avatar");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }


                 View inflate = View.inflate(mActivity, R.layout.cook_menu_detail_title, null);
                 TextView tv_cookMenuDetail_bigTitle = (TextView) inflate.findViewById(R.id.tv_cookMenuDetail_bigTitle);
                 tv_cookMenuDetail_bigTitle.setText(title);
                 stickyList.addHeaderView(inflate);//第二个headView 图片下的标题


                 View inflate2 = View.inflate(mActivity, R.layout.cook_menu_detail_how_to_make, null);

                 TextView tv_cookMenuDetail_the_numOf_watch = (TextView) inflate2.findViewById(R.id.tv_cookMenuDetail_the_numOf_watch);
                 String onclickAndFav_num=fav_num+"人收藏 "+onclick+"次浏览";
                 tv_cookMenuDetail_the_numOf_watch.setText(onclickAndFav_num);//有多少人收藏浏览


                 TextView tv_cookMenuDetail_the_nameOf_author = (TextView) inflate2.findViewById(R.id.tv_cookMenuDetail_the_nameOf_author);
                 TextView tv_cookMenuDetail_the_descriptionOf_cook = (TextView) inflate2.findViewById(R.id.tv_cookMenuDetail_the_descriptionOf_cook);

                 iv_cookMenuDetail_the_picOf_author = (ImageView) inflate2.findViewById(R.id.iv_cookMenuDetail_the_picOf_author);

                 tv_cookMenuDetail_the_nameOf_author.setText(user_name);
                 tv_cookMenuDetail_the_descriptionOf_cook.setText(smalltext);




                 stickyList.addHeaderView(inflate2);//第三个headView 标题下的 作者 菜谱等信息


//----------增加footView---------------------------------------------------


                 TextView textView = new TextView(mActivity);
                 textView.setText("3\n3\n3\n3\n3\n3\n3\n");
                 stickyList.addFooterView(textView);  //这边是分享的sdk 从上往下数第一个foot


                 ListView mypinlun_adapter_for_cookmenudetail = (ListView) View.inflate(mActivity, R.layout.mypinlun_adapter_for_cookmenudetail, null);
                 mypinlun_adapter_for_cookmenudetail.setAdapter(new MyPinLunAdapter());
                 View inflate1 = View.inflate(mActivity, R.layout.cook_menu_detail_user_comment_head_view, null);
                 mypinlun_adapter_for_cookmenudetail.addHeaderView(inflate1);

                 stickyList.addFooterView(mypinlun_adapter_for_cookmenudetail);  //评论footView


                 TextView textView1 = new TextView(mActivity);
                 textView1.setText("2\n2\n2\n2\n2\n2\n2\n");
                 stickyList.addFooterView(textView1);//这个是最下面的
//
//
//
//--------------找到headView中的各个控件--------------------------------------------------
                 tv_cookMenuDetail_topTheCookName = (TextView) findViewById(R.id.tv_cookMenuDetail_TopTheCookName);
                 rl_cookMenuDetail_leftTopBack = (RelativeLayout) findViewById(R.id.rl_cookMenuDetail_leftTopBack);


//----------------------添加 主 listView的 滑动事件----------------------------------
                 stickyList.setOnScrollListener(new AbsListView.OnScrollListener() {
                     @Override
                     public void onScrollStateChanged(AbsListView view, int scrollState) {
                         Log.e("oooo", scrollState + "");

                     }

                     @Override
                     public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                         if (firstVisibleItem == 3) {
                             tv_cookMenuDetail_topTheCookName.setVisibility(View.VISIBLE);
                             tv_cookMenuDetail_topTheCookName.setText(title);
//                rl_cookMenuDetail_leftTopBack.setBackgroundColor(Color.WHITE);
                             tv_cookMenuDetail_topTheCookName.setBackgroundResource(R.drawable.the_title_background);

                             ib_cookMenuDetail_leftTopBack.setBackgroundColor(Color.WHITE);
                             ib_cookMenuDetail_leftTopBack.setBackgroundResource(R.drawable.cook_detail_back_red);

                         }
                         if (firstVisibleItem == 1) {
                             tv_cookMenuDetail_topTheCookName.setVisibility(View.INVISIBLE);

                             ib_cookMenuDetail_leftTopBack.setBackgroundColor(Color.WHITE);
                             ib_cookMenuDetail_leftTopBack.setBackgroundResource(R.drawable.cook_detail_back);


                         }

//                Log.e("oooo", "firstVisibleItem=" + firstVisibleItem);
//                Log.e("oooo", "visibleItemCount=" + visibleItemCount);
//                Log.e("oooo", "totalItemCount=" + totalItemCount);
                     }
                 });


             }
         };


                BmobQuery<Recipe> bmobQuery = new BmobQuery<Recipe>();

                bmobQuery.getObject(MYDBID, new QueryListener<Recipe>() {
                    @Override
                    public void done(Recipe object,BmobException e) {
                        if(e==null){
                             recipe=object;
                            GETDATAFROMSERVER=true;
                            buzhou = object.getZuofa();

                            Message message=new Message();
                            message.what=4;
                            handler.sendMessage(message);

                            Log.e("查询成功","h");
                            if(recipe!=null){

                                Log.e("hahhahhhha","成功了");
                            }

                        }else{
                            Log.e("444444444444","获取服务器数据失败");
                        }
                    }
                });




//-----------------查找Person表里面id为MYDBID的数据---------------------------
//        BmobQuery<Recipe> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(MYDBID, new QueryListener<Recipe>() {
//            @Override
//            public void done(Recipe recipe, BmobException e) {
//
//                if (e == null) {
//                    Log.e("5555555555",recipe.getTitle()+"——"+recipe.getTitlepic()+"——"+ buzhou);
//
////                    SharePreferenceUtils.putString(CookMenuDetailActivity.this,"shexieroufeng",recipe.getZuofa());
//
//                    buzhou="00";
//
//                    buzhou = recipe.getZuofa();
//                    getBuzhou();
//
//                    Log.e("55",recipe.getTitle()+"——"+recipe.getTitlepic()+"——"+ buzhou);
//
//
//
////--------------------------获取步骤--------------------------------------------
//
//
//
//
//
//                } else {
//
//                    Log.e("444444444444","获取服务器数据失败");
//
//                }
//            }
//        });





//--------------------------获取数据--------------------------------------------




        //设置是否收藏
        boolean cookMenuLove = SharePreferenceUtils.getBoolean(this, "cookMenuLove", false);
        if (cookMenuLove) {
            ib_cookMenuDetail_coll_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_coll_icon);
            ib_cookMenuDetail_coll_icon.setImageResource(R.drawable.cook_coll_icon_out_red);
            ib_cookMenuDetail_coll_icon.setBackgroundColor(Color.WHITE);
        }

//            getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //给几个键设置响应事件
        setOnclickForCookMenu();

        // Translucent status bar

        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


    }//onCreate 结束onCreate 结束onCreate 结束onCreate 结束onCreate 结束onCreate 结束onCreate 结束

    private void initTheMainListView() {
        //------------构建菜谱细节listView--------------------------------------------
        MyAdapter adapter = new MyAdapter(mActivity);
        stickyList.setAdapter(adapter);

        theHeadUrl = theheadurl0;
//                         new ArrayList<>();
//                 theHeadUrl.add("http://site.meishij.net/rs/118/144/6286118/n6286118_147185697042250.jpg");
//                 theHeadUrl.add("http://site.meishij.net/rs/115/102/588115/n588115_144621532929555.jpg");
//                 theHeadUrl.add("http://site.meishij.net/rs/115/102/588115/n588115_144621530211710.jpg");

        AutoViewPage autoViewPage = new AutoViewPage(theHeadUrl, mActivity);
        ConvenientBanner convenientBanner = autoViewPage.getConvenientBanner();
//----------增加headView-----------------------------------------------------
        stickyList.addHeaderView(convenientBanner); //第一个headView 头部图片
    }





    private void toast(String sr) {

        Toast.makeText(this,sr,Toast.LENGTH_LONG).show();

    }


    //--------------评论list的适配器-----------------------------------------------------
    private class MyPinLunAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
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


            View inflate = View.inflate(CookMenuDetailActivity.this, R.layout.cook_menu_detail_user_comment_adapter, null);


            return inflate;
        }
    }


//----------------获取每一个步骤----------------------

    private void getBuzhou() {

        if(buzhou!=null){

            Gson gson = new Gson();
            String buzhou2="{\"zuofa\":"+buzhou+"}";
            Buzhou buzhou1 = gson.fromJson(buzhou2,Buzhou.class);


            List<Buzhou.ZuofaBean> zuofaBeanList = buzhou1.getZuofa();
            int size = zuofaBeanList.size();//做法总长度 非步骤

            int steplast = zuofaBeanList.get(size - 1).getStep();//获取最后一个的step


            Log.e("getbuzhou",steplast+"");

            //步骤图片网址集合
            theurl0 = new String[steplast+1];

            for(int j=0;j<theurl0.length;j++){
                theurl0[j]="";
            }


            //步骤描述集合
            theDescription0 = new String[steplast+1];

            for(int j=0;j<theDescription0.length;j++){
                theDescription0[j]="";
            }


            theheadurl0 = new ArrayList<>();
            theheadurl0.add("http://b.hiphotos.baidu.com/baike/w%3D26" +
                    "8%3Bg%3D0/sign=2fe679ff0f7b02080cc938e75ae295ee/9d82d158ccbf6c81c1ea7db2b93eb13532fa408e.jpg");


            chengpinstep = false;
            int step=0;
            for(int j=0;j<size-1;j++){

                Buzhou.ZuofaBean zuofaBean = zuofaBeanList.get(j);

                int step1 = zuofaBean.getStep();//当前的步骤标题就是第几部
                String d = zuofaBean.getD();//当前网址或者描述

                if(zuofaBean.getDt().equals("1")){//数组成员是网址

                    if(step1==steplast){
                        if(!chengpinstep){
                            theurl0[step1]=d;
                            chengpinstep =true;
                        }if(chengpinstep) {
                            theheadurl0.add(d);
                        }

                    }//step1=steplast
                    else{
                        theurl0[step1]=d;
                    }
                    step=step1;

                }else {//数组成员是描述的时候


                    if(step<step1){
                        theDescription0[step1]=d;
                    }else {

                        theDescription0[step1]+="\n"+d;
                    }

                    step=step1;

                }


            }//循环结束 结束处理数据


            for (String sr : theheadurl0) {
                Log.e("7777777",sr);

            }

            for(int j=0;j<theurl0.length;j++){

                    Log.e("888888", "第"+j+"个是："+theurl0[j]);

            }

            for(int j=0;j<theDescription0.length;j++){
                Log.e("666666",theDescription0[j]);
            }



//            Log.e("00000000",steplast+"");


//            Log.e(COOKMENUDETAIL,buzhou1.getZuofa().get(0).getD()+"");


            Log.e(COOKMENUDETAIL,buzhou);
        }else {
            Log.e(COOKMENUDETAIL,"hahh");

        }



    }


//----------------CookMenuDetailActivity的菜谱步骤list的适配器----------------------
    class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private String[] countries;
        private String[] theDescription;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            theUrl =theurl0;
//                    new String[]{"http://site.meishij.net/rs/115/102/588115/n588115_144621529830637.jpg",
//                    "http://site.meishij.net/rs/115/102/588115/n588115_144621529904084.jpg",
//                    "http://site.meishij.net/rs/115/102/588115/n588115_144621529944900.jpg",
//                    "http://site.meishij.net/rs/115/102/588115/n588115_144621530076652.jpg",
//                    "http://site.meishij.net/rs/115/102/588115/n588115_144621530211710.jpg",
////-------------------------------------------------------------------------
//                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252648945686.jpg",
//                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252649684632.jpg",
//                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680138962.jpg",
//                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680933892.jpg",
//                    "",
//            };

            theDescription = theDescription0;

//                    new String[]{
//                    "莲藕去皮、切段，泡入清水里以免氧化变黑",
//                    "猪腿肉去皮洗净沥干水分，切成均匀薄片，加入奥尔良烤料抓匀腌制1小时",
//                    "用莲藕段把猪肉片卷起，加一勺烤料加一勺蜂蜜，搅拌均匀",
//                    "华帝烤箱JKD611-01A预热，180度，上下火。将鸡肉卷放入烤箱，烤制25分钟",
//                    "烤制过程中均匀涂抹酱汁，滴上柠檬汁即可",
//
//                    "醒好的面擀面杖擀成碗边厚的长方片。2/3刷上油酥。",
//                    "煮面条。水开后，放入面条，水继续开后。马上捞出面条。过凉水。再捞出来，拌入提前做好的酱",
//                    "煮面条。水开后，放入面条，水继续开后。马上捞出面条。过凉水。再捞出来，拌入提前做好的酱",
//                    "把做好的面饼盖在面条上面。送入烤箱，中层，170度，烤30分钟左右。",
//                    "把做好的面饼盖在面条上面。送入烤箱，中层，170度，烤30分钟左右."};
        }

        @Override
        public int getCount() {

//            return 0;

            return theUrl.length;
        }

        @Override
        public Object getItem(int position) {
            return theUrl[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.cookmenudetail_itemview, parent, false);

                holder.text = (TextView) convertView.findViewById(R.id.tv_cookMenuDetail_everyStep);
                holder.imageView = (ImageView) convertView.findViewById(R.id.iv_cookMenuDetail_everyStep);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(theDescription[position]);


//            if(theUrl[position].equals("")){  //判断是否要图片
//
//                holder.imageView.setVisibility(View.GONE);
//
//            }else {

                ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
                        .Builder(CookMenuDetailActivity.this)
                        .memoryCacheExtraOptions(480, 800)/*解析图片时候使用的最大尺寸，默认为屏幕设备宽高*/
                        .diskCacheExtraOptions(480, 800, null)/*从网络下载图片后保存到磁盘时使用的图片尺寸及压缩方法，如果不设置则保存原始图片*/
                        .threadPoolSize(3)/*线程池的大小，默认值为3,注意不要设置的过大，过大之后会有OOM问题*/
                        .threadPriority(Thread.NORM_PRIORITY - 1)/*设置线程的优先级别：5-1*/

                        .build();
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(imageLoaderConfiguration);
                DisplayImageOptions options = new DisplayImageOptions.Builder()

                        .showImageOnLoading(R.drawable.about_bg)/*加载图片的时候显示正在加载的图*/
                        .showImageOnFail(R.drawable.box_def_icon)/*加载图片失败后显示这个张图*/
                        .cacheInMemory(true)/*缓存至内存*/
                        .cacheOnDisk(true)/*缓存值SDcard*/
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();


              if(position==0){
                imageLoader.displayImage(avatar, iv_cookMenuDetail_the_picOf_author, options);
              }
                imageLoader.displayImage(theUrl[position], holder.imageView, options);

//            }
//            ImageLoader.getInstance().displayImage(theUrl[position],holder.imageView,options);
//            holder.imageView.setImageResource(R.drawable.a22);

//            if(theUrl[position].equals("8")){  //判断是否要图片
////
//                holder.imageView.setVisibility(View.GONE);
//
//            }
            return convertView;
        }

        @Override  //获取标题view
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            if (convertView == null) {
                holder = new HeaderViewHolder();
                convertView = inflater.inflate(R.layout.cookmenudetail_itemviewhead, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.tv_cookMenuDetail_everyStep_title);
                convertView.setTag(holder);
            } else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            //set header text as first char in name
            //String headerText = "" + countries[position].subSequence(0, 1).charAt(0);
            String title = "— " + "步骤" + (position + 1) + " —";
            holder.text.setText(title);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //return the first character of the country as ID because this is what headers are based upon
            return position;
        }

        class HeaderViewHolder {
            TextView text;
        }

        class ViewHolder {
            ImageView imageView;
            TextView text;
        }
    }

//-------------给几个键设置响应事件------------------------------------------------
    private void setOnclickForCookMenu() {

        ImageButton ib_cookMenuDetail_back_top_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_back_top_icon);
        ib_cookMenuDetail_coll_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_coll_icon);
        ib_cookMenuDetail_leftTopBack = (ImageButton) findViewById(R.id.ib_cookMenuDetail_leftTopBack);
        ImageButton ib_cookMenuDetail_pl_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_pl_icon);
        ImageButton ib_cookMenuDetail_share_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_share_icon);

        ib_cookMenuDetail_back_top_icon.setOnClickListener(this);
        ib_cookMenuDetail_coll_icon.setOnClickListener(this);
        ib_cookMenuDetail_leftTopBack.setOnClickListener(this);
        ib_cookMenuDetail_pl_icon.setOnClickListener(this);
        ib_cookMenuDetail_share_icon.setOnClickListener(this);

    }


//-----------页面几个固定button的点击事件--------------------------------------------
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ib_cookMenuDetail_back_top_icon://回到顶部
                stickyList.setSelection(0);

                break;
            case R.id.ib_cookMenuDetail_coll_icon://收藏按钮
                boolean cookMenuLove = SharePreferenceUtils.getBoolean(this, "cookMenuLove", false);
                if (cookMenuLove) {
                    ib_cookMenuDetail_coll_icon.setImageResource(R.drawable.cook_coll_icon_out);
                    ib_cookMenuDetail_coll_icon.setBackgroundColor(Color.WHITE);
                    SharePreferenceUtils.putBoolean(this, "cookMenuLove", false);

                } else {
                    ib_cookMenuDetail_coll_icon.setImageResource(R.drawable.cook_coll_icon_out_red);
                    ib_cookMenuDetail_coll_icon.setBackgroundColor(Color.WHITE);
                    SharePreferenceUtils.putBoolean(this, "cookMenuLove", true);
                }

                break;
            case R.id.ib_cookMenuDetail_leftTopBack://回退
                finish();

                break;
            case R.id.ib_cookMenuDetail_pl_icon:





//                startActivity(new Intent(this,));

                break;
            case R.id.ib_cookMenuDetail_share_icon:

                break;


        }

    }


}
