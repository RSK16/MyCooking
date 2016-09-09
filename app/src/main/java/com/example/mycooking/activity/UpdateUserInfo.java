package com.example.mycooking.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.UserUploadInfo;
import com.example.mycooking.bean.Userinfo;
import com.example.mycooking.utils.SharePreferenceUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class UpdateUserInfo extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UpdateUserInfo";
    private Button bt_icon;
    private Button bt_username;
    private Button bt_sex;
    private Button bt_birth;
    private Button bt_address;
    private Button bt_job;
    private EditText et_dialog_name;
    private TextView update_tv_username;
    private AlertDialog alertDialog;

    String[] sex = {"男", "女"};
    String[] year = {"1988年", "1989年", "1990年"};
    String[] month = {"1月", "2月", "3月", "4月"};
    String[] day = {"1日", "2日", "3日", "4日"};
    private TextView tv_finish;
    private TextView tv_finish2;
    private TextView update_tv_sex;
    private TextView update_tv_birth;
    private String s;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        getSupportActionBar().hide();
        Bmob.initialize(this, "2f78c11280ce16e4d17e9b7340caba38");
        initView();
        initDate();

    }

    private void initDate() {

    }

    private void initView() {
        bt_icon = (Button) findViewById(R.id.bt_icon);
        bt_icon.setOnClickListener(this);
        bt_username = (Button) findViewById(R.id.bt_username);
        bt_username.setOnClickListener(this);
        bt_sex = (Button) findViewById(R.id.bt_sex);
        bt_sex.setOnClickListener(this);
        bt_birth = (Button) findViewById(R.id.bt_birth);
        bt_birth.setOnClickListener(this);
        bt_address = (Button) findViewById(R.id.bt_address);
        bt_address.setOnClickListener(this);
        bt_job = (Button) findViewById(R.id.bt_job);
        bt_job.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_icon:
                Toast.makeText(this, "可以", Toast.LENGTH_LONG).show();
                break;
            //修改用户名
            case R.id.bt_username:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                final View dialogView = View.inflate(this, R.layout.edit_username, null);
                et_dialog_name = (EditText) dialogView.findViewById(R.id.et_dialog_name);
                Button bt_dialogsetname_confirm = (Button) dialogView.findViewById(R.id.bt_dialogsetname_confirm);
                final Button bt_dialogsetname_cancle = (Button) dialogView.findViewById(R.id.bt_dialogsetname_cancle);
                update_tv_username = (TextView) findViewById(R.id.update_tv_username);

                builder1.setView(dialogView);
                alertDialog = builder1.create();
                Log.i(TAG, "onClick: ===可以");
                alertDialog.show();

                bt_dialogsetname_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String updateusername = et_dialog_name.getText().toString();
                        Log.i(TAG, "onClick: " + updateusername);
                        if (updateusername.equals("")) {
                            Toast.makeText(UpdateUserInfo.this, "用户名不能为空", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(UpdateUserInfo.this, "用户名修改成功", Toast.LENGTH_LONG).show();
                            BmobUser currentUser = BmobUser.getCurrentUser();
                            // 修改用户的邮箱为xxx@163.com
                            currentUser.setUsername(updateusername);
                            currentUser.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        toast("更新用户信息成功");
                                    } else {
                                        toast("更新用户信息失败:" + e.getMessage());
                                    }
                                }
                            });
                            update_tv_username.setText(updateusername);
                            et_dialog_name.setText("");
                            alertDialog.dismiss();
                        }
                    }
                });
                bt_dialogsetname_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                bt_dialogsetname_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                break;
            //修改性别
            case R.id.bt_sex:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                final View dialogView2 = View.inflate(this, R.layout.edit_sex, null);
                ListView lv_edit_sex = (ListView) dialogView2.findViewById(R.id.lv_edit_sex);
                tv_finish = (TextView) dialogView2.findViewById(R.id.tv_finish);
                update_tv_sex = (TextView) findViewById(R.id.update_tv_sex);
                lv_edit_sex.setAdapter(new MyAdapter());
                builder2.setView(dialogView2);
                alertDialog = builder2.create();
                Log.i(TAG, "onClick: ===可以");
                alertDialog.show();
                lv_edit_sex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int currentposition = position;
                        tv_finish.setOnClickListener(new View.OnClickListener() {
                            private BmobUser currentUser;

                            @Override
                            public void onClick(View v) {

                                switch (currentposition) {
                                    case 0:
                                        //BmobUser中的特定属性
                                        currentUser = BmobUser.getCurrentUser();
                                        currentUser.setValue("sex", true);
                                        Log.i(TAG, "onClick: ==================");
                                        update_tv_sex.setText("男");
                                        alertDialog.dismiss();
                                        break;
                                    case 1:
                                        currentUser = BmobUser.getCurrentUser();
                                        currentUser.setValue("sex", false);
                                        Log.i(TAG, "onClick: ==================");
                                        update_tv_sex.setText("女");
                                        alertDialog.dismiss();
                                        break;
                                }
                            }

                        });
                    }
                });
                Toast.makeText(this, "可以", Toast.LENGTH_LONG).show();
                break;
            //修改出生年月日
            case R.id.bt_birth:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                final View dialogView3 = View.inflate(this, R.layout.edit_birth, null);
                final ListView lv_birth_year = (ListView) dialogView3.findViewById(R.id.lv_birth_year);
                final ListView lv_birth_month = (ListView) dialogView3.findViewById(R.id.lv_birth_month);
                final ListView lv_birth_day = (ListView) dialogView3.findViewById(R.id.lv_birth_day);
                tv_finish2 = (TextView) dialogView3.findViewById(R.id.tv_finish);
                update_tv_birth = (TextView) findViewById(R.id.update_tv_birth);
                lv_birth_year.setAdapter(new MyAdapterYear());
                lv_birth_month.setAdapter(new MyAdapterMonth());
                lv_birth_day.setAdapter(new MyAdapterDay());
                builder3.setView(dialogView3);
                alertDialog = builder3.create();
                Log.i(TAG, "onClick: ===可以");
                alertDialog.show();
                lv_birth_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        i=i+1;
                        switch (position) {
                            case 0:
                                s =year[0];
                                break;
                            case 1:
                                s =year[1];
                                break;
                            case 2:
                                s =year[2];
                                break;
                        }
                    }
                });
                lv_birth_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        i=i+1;
                        switch (position) {
                            case 0:
                                s = s + month[0];

                                break;
                            case 1:
                                s = s + month[1];
                                break;
                            case 2:
                                s = s + month[2];
                                break;
                            case 3:
                                s = s + month[3];
                                break;
                        }
                    }
                });
                lv_birth_day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        i=i+1;
                        switch (position) {
                            case 0:
                                Log.i(TAG, "lv_birth_month: 111" + s);
                                s = s + day[0];
                                break;
                            case 1:
                                s = s + day[1];
                                break;
                            case 2:
                                s = s + day[2];
                                break;
                            case 3:
                                s = s + day[3];
                                break;
                        }
                    }
                });
                tv_finish2.setOnClickListener(new View.OnClickListener() {
                    private BmobUser currentUser;

                    @Override
                    public void onClick(View v) {
                        if (i<3){
                            Toast.makeText(UpdateUserInfo.this,"你选择的信息不全，请重新选择年月日",Toast.LENGTH_LONG).show();
                        }
                        else {
                            currentUser = BmobUser.getCurrentUser();
                            currentUser.setValue("birth", s);
                            Log.i(TAG, "setValue: ============" + s);
                            update_tv_birth.setText(s);
                            Log.i(TAG, "update_tv_birth: ============" + s);
                            s = "";
                            alertDialog.dismiss();
                        }
                    }
                });
                Toast.makeText(this, "可以", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_address:
                Toast.makeText(this, "可以", Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_job:
                Toast.makeText(this, "可以", Toast.LENGTH_LONG).show();
                break;

        }
    }

    public void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(UpdateUserInfo.this);
            textView.setText(sex[position]);
            return textView;
        }
    }

    class MyAdapterYear extends BaseAdapter {
        @Override
        public int getCount() {
            return year.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(UpdateUserInfo.this);
            textView.setText(year[position]);
            return textView;
        }
    }

    class MyAdapterMonth extends BaseAdapter {
        @Override
        public int getCount() {
            return month.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(UpdateUserInfo.this);
            textView.setText(month[position]);
            return textView;
        }
    }

    class MyAdapterDay extends BaseAdapter {
        @Override
        public int getCount() {
            return day.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(UpdateUserInfo.this);
            textView.setText(day[position]);
            return textView;
        }
    }

    public void previous(View v){
        finish();
    }
}
