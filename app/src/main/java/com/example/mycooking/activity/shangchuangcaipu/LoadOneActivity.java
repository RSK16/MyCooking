package com.example.mycooking.activity.shangchuangcaipu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.Recipe;

public class LoadOneActivity extends Activity {

    private static final String TAG = "LoadOneActivity";
    public Recipe recipe;
    private Button bt_next;

    private Button bt_dotime;
    private Button bt_gongyi;
    private Button bt_kouwei;
    private Button bt_nandu;
    private Button bt_number;

    private EditText et_name;
    private TextView tv_gongyi;
    private TextView tv_kouwei;
    private TextView tv_nandu;
    private TextView tv_number;
    private TextView tv_dotime;

    private String gongyidata;
    private String kouweidata;
    private String nandudata;
    private String numberdata;
    private String timedata;
    private String tittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_one);

        initView();
        tittle = et_name.getText().toString();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        gongyidata = intent.getStringExtra("gongyi");
        kouweidata = intent.getStringExtra("kouwei");
        nandudata = intent.getStringExtra("nandu");
        numberdata = intent.getStringExtra("number");
        timedata = intent.getStringExtra("time");
        Log.i(TAG, "onNewIntent: "+numberdata);
    }

    @Override
    protected void onResume() {
        if (gongyidata != null) {
            tv_gongyi.setText(gongyidata);
            recipe.setGongyi(gongyidata);
        }
        if (kouweidata != null) {
            tv_kouwei.setText(kouweidata);
            recipe.setKouwei(kouweidata);
        }
        if (nandudata != null) {
            tv_nandu.setText(nandudata);
            recipe.setMake_diff(nandudata);
        }
        if (numberdata != null) {
            tv_number.setText(numberdata);
            recipe.setMake_amount(numberdata);
        }
        if (timedata != null) {
            tv_dotime.setText(timedata);
            recipe.setMake_time(timedata);
            recipe.setMake_pretime(timedata);
        }
        super.onResume();
    }

    private void initView() {
        recipe = new Recipe();

        bt_next = (Button) findViewById(R.id.bt_next);
        bt_dotime = (Button) findViewById(R.id.bt_dotime);
        bt_gongyi = (Button) findViewById(R.id.bt_gongyi);
        bt_kouwei = (Button) findViewById(R.id.bt_kouwei);
        bt_nandu = (Button) findViewById(R.id.bt_nandu);
        bt_number = (Button) findViewById(R.id.bt_number);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_gongyi = (TextView) findViewById(R.id.tv_gongyi);
        tv_kouwei = (TextView) findViewById(R.id.tv_kouwei);
        tv_nandu = (TextView) findViewById(R.id.tv_nandu);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_dotime = (TextView) findViewById(R.id.tv_dotime);
    }
    public void doclick(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                donext();
                break;
            case R.id.bt_dotime:
                startActivity(new Intent(this,Timeactivity.class));
                break;
            case R.id.bt_gongyi:
                startActivity(new Intent(this,GongyiActivity.class));
                break;
            case R.id.bt_kouwei:
                startActivity(new Intent(this,KouweiActivity.class));
                break;
            case R.id.bt_nandu:
                Log.i(TAG, "doclick: 11111111111");
                startActivity(new Intent(this,NanduActivity.class));
                break;
            case R.id.bt_number:
                startActivity(new Intent(this,NumberActivity.class));
                break;
        }
    }
    /**
     * 下一步
     */
    private void donext() {
        if (tv_gongyi.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "工艺不能为空", Toast.LENGTH_SHORT).show();
        } else if (tv_kouwei.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "口味不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_nandu.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "难度不能为空", Toast.LENGTH_SHORT).show();
        }else if (tv_dotime.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "时间不能为空", Toast.LENGTH_SHORT).show();
        } else if (tv_number.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "食用人数不能为空", Toast.LENGTH_SHORT).show();
        }else if (et_name.getText().toString().equals("")) {
            Toast.makeText(LoadOneActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, LoadTwoActivity.class);
            recipe.setTitle(et_name.getText().toString());
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }
//        if (et_name.getText().toString().equals("")) {
//            Toast.makeText(LoadOneActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
//        } else {
//            recipe.setTitle(et_name.getText().toString());
//            Intent intent = new Intent(this, LoadTwoActivity.class);
//            intent.putExtra("recipe", recipe);
//            Log.i(TAG, "donext: "+recipe.getTitle()+"ass"+recipe.getGongyi()+recipe.getKouwei()+recipe.getMake_pretime());
//            startActivity(intent);
//        }
    }
    public void previous(View view) {
        finish();
    }

}
