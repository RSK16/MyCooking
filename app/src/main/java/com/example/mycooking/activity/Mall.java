package com.example.mycooking.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.mycooking.R;

public class Mall extends AppCompatActivity {
    // 声明PopupWindow对象的引用
    private PopupWindow popupWindow;

    //模拟actv这个控件要显示的数据
    private String[] COUNTRIES=new String[]{"loafang",";laooli","apwanh","cai"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        getSupportActionBar().hide();
        //1,找到控件
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.actv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        actv.setAdapter(adapter);
        // 点击按钮弹出菜单
        Button My_order_icon = (Button) findViewById(R.id.My_order_icon);
        My_order_icon.setOnClickListener(My_order_icon_click);
    }
    // 点击弹出左侧菜单的显示方式
    View.OnClickListener My_order_icon_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            getPopupWindow();
            // 这里是位置显示方式,在屏幕的左侧
            popupWindow.showAtLocation(v, Gravity.LEFT, 10, 200);
        }
    };
    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        View popupWindow_view = View.inflate(this, R.layout.my_order_icon_detail, null);
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
        /***
         * 获取PopupWindow实例
         */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }
    //消息提醒
    public void news_notification(View view){
        startActivity(new Intent(this,Shopping_NewNotifaction.class));
    }
    //购物车
    public void shopping_cart(View view){
        startActivity(new Intent(this,Shopping_cart.class));
    }
}
