package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.mycooking.R;
import com.example.mycooking.activity.CookMenuDetailActivity;
import com.example.mycooking.activity.FoodChatActivity;
import com.example.mycooking.page.BasePage;
import com.example.mycooking.view.AutoViewPage;

import java.util.ArrayList;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class topicPage extends BasePage {
    public topicPage(Activity activity) {
        super(activity);
    }


    private static final String THENAME = "FoodChatActivity";
    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        View inflate = View.inflate(mActivity, R.layout.activity_food_chat, null);
        Button bt_shihuabutton = (Button) inflate.findViewById(R.id.bt_shihuabutton);
        bt_shihuabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActivity.startActivity(new Intent(mActivity, CookMenuDetailActivity.class));


            }
        });


        initTheHead(inflate );



        // --------------------------------------
        initListView(inflate);



        return inflate;
    }


    //初始化数据

    private void initTheHead(View activity_food_chat) {//初始化 食话 头部
//        View activity_food_chat = View.inflate(this, R.layout.activity_food_chat, null);
        ImageButton ib_foodChat_leftPlus = (ImageButton) activity_food_chat
                .findViewById(R.id.ib_foodChat_leftPlus);
        ImageButton ib_foodChat_search = (ImageButton) activity_food_chat
                .findViewById(R.id.ib_foodChat_search);
        ImageButton ib_foodChat_addFriend = (ImageButton) activity_food_chat
                .findViewById(R.id.ib_foodChat_addFriend);
        ib_foodChat_leftPlus     .setOnClickListener(new MyClickHead());
        ib_foodChat_search       .setOnClickListener(new MyClickHead());
        ib_foodChat_addFriend    .setOnClickListener(new MyClickHead());

    }

    private void initListView(View activity_food_chat) {

        ArrayList<String> images=new ArrayList<String>();
        images.add("http://images.meishij.net/p/20160901/e71cd6bcff2aa33e1f5425837b834e90.jpg");
        images.add("http://images.meishij.net/p/20160901/1bc2e29c668ad20586669e72a2a42a52.jpg");
        images.add("http://images.meishij.net/p/20160902/847783852b26fca93aeb7a9e3fa4c1d5.jpg");

        AutoViewPage autoViewPage = new AutoViewPage(images, mActivity);
        ConvenientBanner view = autoViewPage.getConvenientBanner();


//        TextView textView = new TextView(this);
//        textView.setText("oooooooo");

        ListView lv_food_chat_activity = (ListView) activity_food_chat.findViewById(R.id.lv_food_chat_activity);
        lv_food_chat_activity.setAdapter(new MyAdapter());
        lv_food_chat_activity.addHeaderView(view);

    }





    private class MyClickHead implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){

                case R.id.ib_foodChat_leftPlus:
                    Log.e(THENAME,"1");
                    break;
                case R.id.ib_foodChat_search:
                    Log.e(THENAME,"2");

                    break;
                case R.id.ib_foodChat_addFriend:
                    Log.e(THENAME,"3");
                    break;






            }

        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 4;
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
            ArrayList<String> images=new ArrayList<String>();

            switch (position){
                case 0:

                    images.clear();
                    images.add("http://site.meishij.net/rs/58/07/2939308/n2939308_147242594918240.jpg");
                    images.add("http://site.meishij.net/rs/47/93/1210797/n1210797_147273388001004.jpg");
                    images.add("http://site.meishij.net/rs/184/180/4420184/n4420184_147254677507119.jpg");

                    break;
                case 1:

                    images.clear();
                    images.add("http://site.meishij.net/rs/116/168/6542116/n6542116_147194569350842.jpg");
                    images.add("http://site.meishij.net/rs/69/207/2801819/n2801819_147321103995212.jpg");



                    break;
                case 2:

                    images.clear();
                    images.add("http://images.meishij.net/p/20160906/1e926d6cb6c5c30c1235412e9083e1d6.jpg");
                    images.add("http://site.meishij.net/rs/246/109/4089996/n4089996_146096687777450.jpg");
                    break;

            }


            AutoViewPage autoViewPage = new AutoViewPage(images, mActivity);
            ConvenientBanner view = autoViewPage.getConvenientBanner();






            return view;
        }
    }


}
