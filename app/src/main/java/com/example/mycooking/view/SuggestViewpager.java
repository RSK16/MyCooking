package com.example.mycooking.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/9/4.
 */
public class SuggestViewpager extends ViewPager {

    private static final String TAG = "SuggestViewpager";

    public SuggestViewpager(Context context) {
        super(context);
    }

    public SuggestViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float startx;
    private float starty;
    private float endx;
    private float endy;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //解决ScrollView和Viewpager 滑动事件冲突
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                //Log.i(TAG,"DOWN");

                startx = ev.getRawX();
                starty = ev.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:

                //Log.i(TAG,"MOVE");

                endx = ev.getRawX();
                endy = ev.getRawY();

                float dx = Math.abs(endx - startx);
                float dy = Math.abs(endy - starty);

                //判断是上下滑动 还是左右滑动
                if(dx>dy){
                    //水平滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    //上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

            case MotionEvent.ACTION_UP:

                //Log.i(TAG,"UP");

               /*
               startx = 0;
                starty = 0;*/
                break;
        }


        return super.dispatchTouchEvent(ev);
    }
}
