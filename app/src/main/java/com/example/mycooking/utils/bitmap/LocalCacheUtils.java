package com.example.mycooking.utils.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.mycooking.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * 本地缓存工具类
 * 
 * @author apple
 * 
 */
public class LocalCacheUtils {
	private Activity activity;
	public LocalCacheUtils() {
	}

	private static final String LOCAL_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";

	/**
	 * 从本地读取图片
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromLocal(String url) {
		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(LOCAL_PATH, fileName);

			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				//Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				// 向内存保存图片对象
				MemoryCacheUtils.putBitmapToMemory(url, bitmap);
				return bitmap;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向本地存图片
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void putBitmapToLocal(String url, Bitmap bitmap) {
		//File cacheDir = activity.getCacheDir();
		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(LOCAL_PATH, fileName);
			File parent = file.getParentFile();

			// 创建父文件夹
			if (!parent.exists()) {
				parent.mkdirs();
			}
//压缩图片
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
