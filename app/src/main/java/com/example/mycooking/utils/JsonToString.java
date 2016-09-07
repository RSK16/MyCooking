package com.example.mycooking.utils;

import com.google.gson.Gson;

/**
 * Created by apple on 16/9/7.
 */
public class JsonToString {
    /**
     * Java对象和JSON字符串相互转化工具类
     *
     */


    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str,  type);
    }

}