package com.example.mycooking.view;

import android.content.Context;
import android.util.AttributeSet;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class HeadListViewForScrollView extends StickyListHeadersListView {
    public HeadListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HeadListViewForScrollView(Context context) {
        super(context);
    }

    public HeadListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
