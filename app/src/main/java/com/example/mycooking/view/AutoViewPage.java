package com.example.mycooking.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;


import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.mycooking.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class AutoViewPage implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {

    private ConvenientBanner convenientBanner;
    ArrayList<String> url;
    Context context;
    private List<String> networkImages;






    public AutoViewPage(ArrayList<String> url, Context context){
        super();



        this.context=  context;
        networkImages=url;
        this.url=url;
        initViews();
        init();
    }

    public ConvenientBanner getConvenientBanner() {
        return convenientBanner;
    }

    private void initViews() {

        View inflate = View.inflate(context, R.layout.auto_viewpage, null);
        convenientBanner = (ConvenientBanner) inflate.findViewById(R.id.convenientBanner);


    }



    private void init() {

            initImageLoader();
            loadTestDatas();

//网络加载例子
//        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);


        convenientBanner.startTurning(5000);


        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms.CubeOutTransformer");
            ABaseTransformer transforemer=(ABaseTransformer)cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true,transforemer);
            convenientBanner.setScrollDuration(1200);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void loadTestDatas() {

    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

    }

//    // 开始自动翻页
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //开始自动翻页
//        convenientBanner.startTurning(5000);
//    }
//
//    // 停止自动翻页
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //停止翻页
//        convenientBanner.stopTurning();
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {

    }
}
