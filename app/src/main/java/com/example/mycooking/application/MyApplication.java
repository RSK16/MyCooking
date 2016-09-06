package com.example.mycooking.application;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();


                //设置获取缓存的目录:测试使用
                // File cacheDir=StorageUtils.getOwnCacheDirectory(this,"JRKJ/cache");
        /*
        * configuration表示ImageLoader的全局配置信息，创建ImageLoader时使用
        * 可包括图片最大尺寸，线程池，缓存，下载器，解码器等等。
        * 这里可以根据需要自行配置，修改慎重！
        * */
                ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                        .memoryCacheExtraOptions(480, 800)/*解析图片时候使用的最大尺寸，默认为屏幕设备宽高*/
                        .diskCacheExtraOptions(480, 800, null)/*从网络下载图片后保存到磁盘时使用的图片尺寸及压缩方法，如果不设置则保存原始图片*/
                        .threadPoolSize(3)/*线程池的大小，默认值为3,注意不要设置的过大，过大之后会有OOM问题*/
                        .threadPriority(Thread.NORM_PRIORITY - 1)/*设置线程的优先级别：5-1*/
                /*
                * tasksProcessingOrder:设置图片加载和显示队列处理的类型 默认为QueueProcessingType.
                FIFO注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
                */
                        .tasksProcessingOrder(QueueProcessingType.FIFO)
                        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))/*设置内存缓存 默认为一个当前应用可用内存的1/8大小的LruMemoryCache*/
                        .memoryCacheSize(2 * 1024 * 1014)/*设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8    */
                        .memoryCacheSizePercentage(13)/*设置内存缓存最大大小占当前应用可用内存的百分比 默认为一个当前应用可用内存的1/8*/
                        .diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(getApplicationContext())))
                        /*默认为StorageUtils.getCacheDirectory(getApplicationContext()),即/mnt/sdcard/android/data/包名/cache/*/
                        .diskCacheSize(50 * 1024 * 1024)/*设置硬盘缓存的最大大小*/
                        .diskCacheFileCount(100)/*设置硬盘缓存的文件的最多个数*/
                        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())/*设置硬盘缓存文件名生成规范*/
                        .imageDownloader(new BaseImageDownloader(this))/*设置图片下载器*/
                        .imageDecoder(DefaultConfigurationFactory.createImageDecoder(false))/*设置图片解码器*/
                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple())/*设置默认的图片显示选项*/
                        .denyCacheImageMultipleSizesInMemory()/*不缓存图片的多种尺寸在内存中*/
                        .writeDebugLogs()/*打印调试Log,注意上线之前要去掉这句话*/
                        .imageDownloader(/*图片下载器的设置*/
                                new BaseImageDownloader(this, 5 * 1000, 30 * 100)/*（connectTimeout,readTimeout）超时时间*/
                        )
                        .build();
                ImageLoader.getInstance().init(configuration);/*使用基本配置信息初始化ImageLoader*/

            }



}
