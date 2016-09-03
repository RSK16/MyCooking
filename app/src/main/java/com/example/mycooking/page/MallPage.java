package com.example.mycooking.page;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.mycooking.R;
import com.example.mycooking.activity.MainActivity;
import com.example.mycooking.activity.Shopping_NewNotifaction;
import com.example.mycooking.activity.Shopping_cart;

/**
 * Created by liaozhihua on 2016/9/3.
 */
public class MallPage extends BasePage {
    // 声明PopupWindow对象的引用
    private PopupWindow popupWindow;

    //模拟actv这个控件要显示的数据
    private String[] COUNTRIES = new String[]{"loafang", ";laooli", "apwanh", "cai"};
    private Button shopping_cart;
    private ImageButton news_notification;


    public MallPage(Activity activity) {
        super(activity);


    }

    @Override
    public void initData() {

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.shopping_mall, null);
        shopping_cart = (Button) view.findViewById(R.id.shopping_cart);
        news_notification = (ImageButton) view.findViewById(R.id.news_notification);
        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,Shopping_cart.class));
            }
        });
        news_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,Shopping_NewNotifaction.class));
            }
        });

        // 点击按钮弹出菜单
        Button my_order_icon = (Button) view.findViewById(R.id.my_order_icon);

        my_order_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击弹出左侧菜单的显示方式
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow.showAsDropDown(v, 0,0);
            }
        });
        return view;
    }
//         * 创建PopupWindow

    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        View popupWindow_view = View.inflate(mActivity, R.layout.my_order_icon_detail, null);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 200, ActionBar.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    //        **
//         * 获取PopupWindow实例
//
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

}