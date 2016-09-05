package com.example.mycooking.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yutt on 2016/9/2.
 */
//自定义Textview控件 可以初始化显示几行，点击后全部显示，再点击隐藏
public class HideAndShowTextView extends TextView {

    private static final String TAG = "HideAndShowTextView";
    public boolean flag=true;

    public HideAndShowTextView(Context context) {
        super(context);
    }

    public HideAndShowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag){

                    //当flag是true
                    flag=false;
                    setEllipsize(null);//展开
                    setSingleLine(flag);

                    //Log.i(TAG,getLineCount()+"---"+getHeight());

                }else{

                    //当flag是false
                    flag=true;
                    setLines(2);//只显示两行行
                    setEllipsize(TextUtils.TruncateAt.END);//收缩

                   // Log.i(TAG,getLineCount()+"----"+getHeight());
                }
            }
        });
    }


}
