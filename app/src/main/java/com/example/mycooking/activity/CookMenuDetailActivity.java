package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.mycooking.R;
import com.example.mycooking.view.AutoViewPage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static android.os.Build.*;

public class CookMenuDetailActivity extends Activity implements View.OnClickListener {

    private  String[] theUrl;
    private ArrayList<String> theHeadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu_detail);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        //给几个键设置响应事件
        setOnclickForCookMenu();

        // Translucent status bar

        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        //------------构建菜谱细节listView
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.lv_stickyListHeaders);
        MyAdapter adapter = new MyAdapter(this);
        stickyList.setAdapter(adapter);

        theHeadUrl=new ArrayList<>();
        theHeadUrl.add("http://site.meishij.net/rs/118/144/6286118/n6286118_147185697042250.jpg");
        theHeadUrl.add("http://site.meishij.net/rs/115/102/588115/n588115_144621532929555.jpg");
        theHeadUrl.add("http://site.meishij.net/rs/115/102/588115/n588115_144621530211710.jpg");

        AutoViewPage autoViewPage = new AutoViewPage(theHeadUrl, this);
        ConvenientBanner convenientBanner = autoViewPage.getConvenientBanner();

        stickyList.addHeaderView(convenientBanner); //第一个headView
//--------------------------------------------------------




    }




    //CookMenuDetailActivity的listview适配器
    class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private String[] countries;
        private String[] theDescription;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            theUrl=new String[]{"http://site.meishij.net/rs/115/102/588115/n588115_144621529830637.jpg",
                    "http://site.meishij.net/rs/115/102/588115/n588115_144621529904084.jpg",
                    "http://site.meishij.net/rs/115/102/588115/n588115_144621529944900.jpg",
                    "http://site.meishij.net/rs/115/102/588115/n588115_144621530076652.jpg",
                    "http://site.meishij.net/rs/115/102/588115/n588115_144621530211710.jpg",
//-------------------------------------------------------------------------
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252648945686.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252649684632.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680138962.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680933892.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252681704495.jpg",



            };

            theDescription=new String[]{
                    "莲藕去皮、切段，泡入清水里以免氧化变黑",
                    "猪腿肉去皮洗净沥干水分，切成均匀薄片，加入奥尔良烤料抓匀腌制1小时",
                    "用莲藕段把猪肉片卷起，加一勺烤料加一勺蜂蜜，搅拌均匀",
                    "华帝烤箱JKD611-01A预热，180度，上下火。将鸡肉卷放入烤箱，烤制25分钟",
                    "烤制过程中均匀涂抹酱汁，滴上柠檬汁即可",


                    "醒好的面擀面杖擀成碗边厚的长方片。2/3刷上油酥。",
                    "煮面条。水开后，放入面条，水继续开后。马上捞出面条。过凉水。再捞出来，拌入提前做好的酱",
                    "煮面条。水开后，放入面条，水继续开后。马上捞出面条。过凉水。再捞出来，拌入提前做好的酱",
                    "把做好的面饼盖在面条上面。送入烤箱，中层，170度，烤30分钟左右。",
                    "把做好的面饼盖在面条上面。送入烤箱，中层，170度，烤30分钟左右."};
        }


        @Override
        public int getCount() {
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
                holder.imageView= (ImageView) convertView.findViewById(R.id.iv_cookMenuDetail_everyStep);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.text.setText(theDescription[position]);


            ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration
                    .Builder(CookMenuDetailActivity.this)
                    .memoryCacheExtraOptions(480, 800)/*解析图片时候使用的最大尺寸，默认为屏幕设备宽高*/
                    .diskCacheExtraOptions(480, 800, null)/*从网络下载图片后保存到磁盘时使用的图片尺寸及压缩方法，如果不设置则保存原始图片*/
                    .threadPoolSize(3)/*线程池的大小，默认值为3,注意不要设置的过大，过大之后会有OOM问题*/
                    .threadPriority(Thread.NORM_PRIORITY - 1)/*设置线程的优先级别：5-1*/

                    .build();
            ImageLoader imageLoader=ImageLoader.getInstance();
            imageLoader.init(imageLoaderConfiguration);
            DisplayImageOptions options=new DisplayImageOptions.Builder()

                    .cacheInMemory(true)/*缓存至内存*/
                    .cacheOnDisk(true)/*缓存值SDcard*/
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            imageLoader.displayImage(theUrl[position],holder.imageView,options);

//            ImageLoader.getInstance().displayImage(theUrl[position],holder.imageView,options);
//            holder.imageView.setImageResource(R.drawable.a22);

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
            String title="— "+"步骤"+(position+1)+" —";
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


    //给几个键设置响应事件
    private void setOnclickForCookMenu() {

        ImageButton ib_cookMenuDetail_back_top_icon = (ImageButton) findViewById(R.id.ib_cookMenuDetail_back_top_icon );
        ImageButton ib_cookMenuDetail_coll_icon     = (ImageButton) findViewById(R.id.ib_cookMenuDetail_coll_icon    );
        ImageButton ib_cookMenuDetail_leftTopBack   = (ImageButton) findViewById(R.id.ib_cookMenuDetail_leftTopBack   );
        ImageButton ib_cookMenuDetail_pl_icon       = (ImageButton) findViewById(R.id.ib_cookMenuDetail_pl_icon       );
        ImageButton ib_cookMenuDetail_share_icon    = (ImageButton) findViewById(R.id.ib_cookMenuDetail_share_icon    );


        ib_cookMenuDetail_back_top_icon.setOnClickListener(this);
        ib_cookMenuDetail_coll_icon    .setOnClickListener(this);
        ib_cookMenuDetail_leftTopBack  .setOnClickListener(this);
        ib_cookMenuDetail_pl_icon      .setOnClickListener(this);
        ib_cookMenuDetail_share_icon   .setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case  R.id.ib_cookMenuDetail_back_top_icon:

                break;
            case  R.id.ib_cookMenuDetail_coll_icon    :

                break;
            case  R.id.ib_cookMenuDetail_leftTopBack  :
                finish();

                break;
            case  R.id.ib_cookMenuDetail_pl_icon      :

//                startActivity(new Intent(this,));

                break;
            case  R.id.ib_cookMenuDetail_share_icon   :

                break;




        }

    }

}
