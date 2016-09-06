package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycooking.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CookMenuDetailActivity extends Activity {

    private  String[] theUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu_detail);










        //------------构建listView
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.lv_stickyListHeaders);
        MyAdapter adapter = new MyAdapter(this);
        stickyList.setAdapter(adapter);


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.bottom_tab_bg);

        stickyList.addHeaderView(imageView);


    }






    //CookMenuDetailActivity的listview适配器
    class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private String[] countries;
        private String[] theDescription;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            theUrl=new String[]{"http://site.meishij.net/rs/131/154/4351131/n4351131_147252645278699.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252646514219.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252646694469.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252646859303.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252648031511.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252648945686.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252649684632.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680138962.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252680933892.jpg",
                    "http://site.meishij.net/rs/131/154/4351131/n4351131_147252681704495.jpg",



            };

            theDescription=new String[]{"先来自制一个酱。面条好不好吃，全看这个酱了。先准备2根杭椒切成圈，番茄用料理机打成汁。",
                    "炒锅，适量油，小火把花椒爆香。待香味出来，花椒变黑。把花椒捞出不用。趁热把炸好的花椒油直接浇在杭椒圈上面。这样能去除杭椒的生涩味。使辣味更加温和点。还带点花椒的香味。",
                    "倒入番茄汁，番茄酱，甜面酱提味。再来一点点酱油。适量的糖和盐。搅均匀了。这样酱就做好了",
                    "接下来准备做饼。先来做一个油酥。用30克面粉，倒入25克热油，迅速搅开，稀稠度就像澥开的芝麻酱似的。再根据自己口味调入适量的椒盐。你也可以直接加盐就好。",
                    "来和面，250克面粉，加入130克左右的水，10克食用油。和成较光滑的面团。放一边醒5分钟。",
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


}
