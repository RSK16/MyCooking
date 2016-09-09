package com.example.mycooking.utils.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.HashMap;

/**
 * Created by sujizhong on 16/6/29.
 */
public class ScreenUtil {

    /**
     * 根据手机的分辨率from dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 屏幕宽度
     */
    public static int getWidth(Context context){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 屏幕高度
     * */
    public static int getHeight(Context context){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 屏幕物理尺寸
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static double getScreenSizeOfDevice2(Context cxt) {
        Point point = new Point();
        WindowManager wm = (WindowManager) cxt.getSystemService(Context.WINDOW_SERVICE);
        //getRealSize()要求minapi 17
        wm.getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    /**
     * 获得屏幕的     像素密度dpi，高度px 宽度px
     * */
    public static HashMap<String, String> getDensity(Context cxt) {
        DisplayMetrics displayMetrics = cxt.getResources().getDisplayMetrics();
        StringBuilder builder = new StringBuilder();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Density", displayMetrics.density + "");
        map.put("densityDpi", displayMetrics.densityDpi + "");
        map.put("heightPixels", displayMetrics.heightPixels + "");
        map.put("widthPixels", displayMetrics.widthPixels + "");
        return map;
    }
}
