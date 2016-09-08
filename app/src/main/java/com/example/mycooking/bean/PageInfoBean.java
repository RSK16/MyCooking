package com.example.mycooking.bean;

import java.util.ArrayList;

/**
 * Created by yutt on 2016/9/7.
 */
public class PageInfoBean {
//存放Viewpager的listview的数据
    public int mPosition;
    public ArrayList<Recipe> mRecipeList;

    public PageInfoBean(int mPosition, ArrayList<Recipe> mRecipeList) {
        this.mPosition = mPosition;
        this.mRecipeList = mRecipeList;
    }
}
