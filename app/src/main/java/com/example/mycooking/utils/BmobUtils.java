package com.example.mycooking.utils;

import android.util.Log;

import com.example.mycooking.bean.Recipe;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by apple on 16/9/3.
 */
public class BmobUtils {

    private static String TAG="BmobUtils";

    /**
     * 查询数据
     * @param key
     * @param value
     */
    public static void select(String key,String value) {
        //初始化Bmob
        //Bmob.initialize(context,APP_ID);

        BmobQuery<Recipe> bmobQuery = new BmobQuery<Recipe>();
        //查询条件 两种
        bmobQuery.addWhereEqualTo(key, value);
        //bmobQuery.addWhereNotEqualTo(key,value);
        //模糊查询
//查询username字段的值含有“sm”的数据
        //query.addWhereContains("username", "sm");

//查询username字段的值是以“sm“字开头的数据
        //query.whereStartsWith("username", "sm");

// 查询username字段的值是以“ile“字结尾的数据
        //query.whereEndsWith("username", "ile");
        //返回数据条数 默认10条
        bmobQuery.setLimit(20);
        //查询方法
        bmobQuery.findObjects(new FindListener<Recipe>() {
            @Override
            public void done(List<Recipe> list, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 查询数据成功,共" + list.size() + "条数据。");
                    //查询后的业务逻辑
                } else {
                    Log.i(TAG, "done: 查询数据失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 增加数据
     * @param recipe
     */
    public static void add(Recipe recipe) {
        recipe.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 增加数据成功:" + s);
                    //增加数据后的业务逻辑
                } else {
                    Log.i(TAG, "done: 增加数据失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 删除数据 只支持通过ObjectId来删除
     * @param recipe
     */
    public static void delete(Recipe recipe) {
        recipe.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done: 删除数据成功:");
                    //增加数据后的业务逻辑
                } else {
                    Log.i(TAG, "done: 删除数据失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 只支持通过ObjectId来改
     * @param recipe
     */
    public static void update(Recipe recipe,String key,String value,String ObjectId) {
        recipe.setValue(key, value);
        recipe.update(ObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i(TAG, "done:  更改数据成功:");
                    //更改数据后的业务逻辑
                } else {
                    Log.i(TAG, "done: 更改数据失败" + e.getMessage());
                }
            }
        });
    }

}
