package com.example.mycooking.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 * Created by apple on 16/8/29.
 */
public class SharePreferenceUtils {

    private static final String SHARE_PREFS_NAME = "com";
    private static SharedPreferences mSharedPreferences;

    public static void putBoolean(Context ctx, String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, defaultValue);
    }
    public static void putInt(Context ctx,String key,int value){
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putInt(key, value).commit();
    }
    public static int getInt(Context ctx,String key,int defValue){
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getInt(key,defValue);
    }
}
