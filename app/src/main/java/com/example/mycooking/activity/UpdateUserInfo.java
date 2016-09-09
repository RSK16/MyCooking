package com.example.mycooking.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.example.mycooking.R;
import com.example.mycooking.bean.PickerViewData;
import com.example.mycooking.bean.ProvinceBean;
import com.example.mycooking.bean.Userinfo;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class UpdateUserInfo extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();

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
    private TextView update_tv_address;
    private View vMasker;
    private OptionsPickerView pvOptions;
    private TextView update_tv_job;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        getSupportActionBar().hide();
        Bmob.initialize(this, "2f78c11280ce16e4d17e9b7340caba38");
        update_tv_username = (TextView) findViewById(R.id.update_tv_username);
        update_tv_sex = (TextView) findViewById(R.id.update_tv_sex);
        update_tv_birth = (TextView) findViewById(R.id.update_tv_birth);
        update_tv_address = (TextView) findViewById(R.id.update_tv_address);
        update_tv_job = (TextView) findViewById(R.id.update_tv_job);
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
        Userinfo currentUser = BmobUser.getCurrentUser(Userinfo.class);
        update_tv_username.setText(currentUser.getUsername());
        Boolean sex = currentUser.getSex();
        if (sex != null) {
            if (sex){
                update_tv_sex.setText("男");
            }
            else
                update_tv_sex.setText("女");
        }
        update_tv_birth.setText(currentUser.getBirth());
        update_tv_address.setText(currentUser.getAddress());
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
                break;
            //修改出生年月日
            case R.id.bt_birth:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                final View dialogView3 = View.inflate(this, R.layout.edit_birth, null);
                final ListView lv_birth_year = (ListView) dialogView3.findViewById(R.id.lv_birth_year);
                final ListView lv_birth_month = (ListView) dialogView3.findViewById(R.id.lv_birth_month);
                final ListView lv_birth_day = (ListView) dialogView3.findViewById(R.id.lv_birth_day);
                tv_finish2 = (TextView) dialogView3.findViewById(R.id.tv_finish);

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
                break;
            case R.id.bt_address:


                vMasker = findViewById(R.id.vMasker);

                //选项选择器
                pvOptions = new OptionsPickerView(this);
                //选项1
                options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
                options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
                options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

                //选项2
                ArrayList<String> options2Items_01=new ArrayList<>();
                options2Items_01.add("广州");
                options2Items_01.add("佛山");
                options2Items_01.add("东莞");
                options2Items_01.add("阳江");
                options2Items_01.add("珠海");
                ArrayList<String> options2Items_02=new ArrayList<>();
                options2Items_02.add("长沙");
                options2Items_02.add("岳阳");
                ArrayList<String> options2Items_03=new ArrayList<>();
                options2Items_03.add("桂林");
                options2Items.add(options2Items_01);
                options2Items.add(options2Items_02);
                options2Items.add(options2Items_03);


                //选项3
                ArrayList<ArrayList<IPickerViewData>> options3Items_01 = new ArrayList<>();
                ArrayList<ArrayList<IPickerViewData>> options3Items_02 = new ArrayList<>();
                ArrayList<ArrayList<IPickerViewData>> options3Items_03 = new ArrayList<>();
                ArrayList<IPickerViewData> options3Items_01_01=new ArrayList<>();
                options3Items_01_01.add(new PickerViewData("天河"));
                options3Items_01_01.add(new PickerViewData("黄埔"));
                options3Items_01_01.add(new PickerViewData("海珠"));
                options3Items_01_01.add(new PickerViewData("越秀"));
                options3Items_01.add(options3Items_01_01);
                ArrayList<IPickerViewData> options3Items_01_02=new ArrayList<>();
                options3Items_01_02.add(new PickerViewData("南海"));
                options3Items_01_02.add(new PickerViewData("高明"));
                options3Items_01_02.add(new PickerViewData("禅城"));
                options3Items_01_02.add(new PickerViewData("桂城"));
                options3Items_01.add(options3Items_01_02);
                ArrayList<IPickerViewData> options3Items_01_03=new ArrayList<>();
                options3Items_01_03.add(new PickerViewData("其他"));
                options3Items_01_03.add(new PickerViewData("常平"));
                options3Items_01_03.add(new PickerViewData("虎门"));
                options3Items_01.add(options3Items_01_03);
                ArrayList<IPickerViewData> options3Items_01_04=new ArrayList<>();
                options3Items_01_04.add(new PickerViewData("其他"));
                options3Items_01_04.add(new PickerViewData("其他"));
                options3Items_01_04.add(new PickerViewData("其他"));
                options3Items_01.add(options3Items_01_04);
                ArrayList<IPickerViewData> options3Items_01_05=new ArrayList<>();

                options3Items_01_05.add(new PickerViewData("其他1"));
                options3Items_01_05.add(new PickerViewData("其他2"));
                options3Items_01.add(options3Items_01_05);

                ArrayList<IPickerViewData> options3Items_02_01=new ArrayList<>();

                options3Items_02_01.add(new PickerViewData("长沙1"));
                options3Items_02_01.add(new PickerViewData("长沙2"));
                options3Items_02_01.add(new PickerViewData("长沙3"));
                options3Items_02_01.add(new PickerViewData("长沙4"));
                options3Items_02_01.add(new PickerViewData("长沙5"));




                options3Items_02.add(options3Items_02_01);
                ArrayList<IPickerViewData> options3Items_02_02=new ArrayList<>();

                options3Items_02_02.add(new PickerViewData("岳阳"));
                options3Items_02_02.add(new PickerViewData("岳阳1"));
                options3Items_02_02.add(new PickerViewData("岳阳2"));
                options3Items_02_02.add(new PickerViewData("岳阳3"));
                options3Items_02_02.add(new PickerViewData("岳阳4"));
                options3Items_02_02.add(new PickerViewData("岳阳5"));

                options3Items_02.add(options3Items_02_02);
                ArrayList<IPickerViewData> options3Items_03_01=new ArrayList<>();
                options3Items_03_01.add(new PickerViewData("好山水"));
                options3Items_03.add(options3Items_03_01);

                options3Items.add(options3Items_01);
                options3Items.add(options3Items_02);
                options3Items.add(options3Items_03);

                //三级联动效果
                pvOptions.setPicker(options1Items, options2Items, options3Items, true);
                //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
                pvOptions.setTitle("选择城市");
                pvOptions.setCyclic(false, true, true);
                //设置默认选中的三级项目
                //监听确定选择按钮
                pvOptions.setSelectOptions(1, 1, 1);
                //点击弹出选项选择器
            /*    update_tv_address.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: 8处");
                        pvOptions.show();
                    }
                });*/
                pvOptions.show();//点击按钮设置的是整个textview

                pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    BmobUser currentUser = BmobUser.getCurrentUser();//找到服务器上当前用户
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        update_tv_address.setText(tx);
                        currentUser.setValue("address", tx);
                        vMasker.setVisibility(View.GONE);
                    }
                });

                break;
            case R.id.bt_job:


                update_tv_job.setText("学生");
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pvOptions.isShowing()){
                pvOptions.dismiss();
                Log.i(TAG, "onClick: 9处");
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
