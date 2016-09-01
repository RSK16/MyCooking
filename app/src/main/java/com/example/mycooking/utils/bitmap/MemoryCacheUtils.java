package com.example.mycooking.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存工具类
 * 
 * @author apple
 * 
 */
public class MemoryCacheUtils {
	// HashMap会出现OOM
	// HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<String,
	// SoftReference<Bitmap>>();
	static LruCache<String, Bitmap> mMemoryCache;

	public MemoryCacheUtils() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();// 当前手机分配给app进程的最大内存,虚拟机默认16M

		System.out.println("maxMemory:" + maxMemory);
		mMemoryCache = new LruCache<String, Bitmap>(maxMemory / 8) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int size = value.getRowBytes() * value.getHeight();// 返回bitmap占用的内存大小
				System.out.println("sizeof:" + size);
				return size;
			}
		};
	}

	/**
	 * 从内存读取图片
	 * 
	 * @param url
	 * @return
	 */
	public  Bitmap getBitmapFromMemory(String url) {
		// SoftReference<Bitmap> softBitmap = mMemoryCache.get(url);
		// System.out.println("读取内存图片。。。" + softBitmap);
		// if (softBitmap != null) {
		// Bitmap bitmap = softBitmap.get();
		// System.out.println("读取内存图片成功。。。" + bitmap);
		// return bitmap;
		// }

		Bitmap bitmap = mMemoryCache.get(url);
		return bitmap;
	}

	/**
	 * 向内存存图片
	 * 
	 * @param url
	 * @param bitmap
	 */
	public static void putBitmapToMemory(String url, Bitmap bitmap) {
		// System.out.println("设置内存图片。。。");
		// SoftReference<Bitmap> softBitmap = new
		// SoftReference<Bitmap>(bitmap);// 通过软引用对对象包装
		// mMemoryCache.put(url, softBitmap);
		mMemoryCache.put(url, bitmap);
	}
}
