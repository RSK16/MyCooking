package com.example.mycooking.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.Buzhou;
import com.example.mycooking.bean.PinLun;
import com.example.mycooking.bean.Recipe;
import com.example.mycooking.utils.JsonToString;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PinLunActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PINLUNACTIVITY = "PinLunActivity";
    private String caipuID;
    private EditText et_pinLun_write;
    private Recipe recipe;
    private String thecomment;
    private String s;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_lun);

        getSupportActionBar().hide();
        mActivity=this;

        ImageButton ib_pinLun_leftTopBack = (ImageButton) findViewById(R.id.ib_pinLun_leftTopBack);
        Button ib_pinLun_righttijiao = (Button) findViewById(R.id.ib_pinLun_righttijiao);
        et_pinLun_write = (EditText) findViewById( R.id.et_pinLun_write       );


        ib_pinLun_leftTopBack.setOnClickListener(this);
        ib_pinLun_righttijiao.setOnClickListener(this);


        Intent intent = getIntent();
        caipuID = intent.getStringExtra("caipuID");

        long l = System.currentTimeMillis();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ib_pinLun_leftTopBack:

                finish();
                break;

            case R.id.ib_pinLun_righttijiao:

                Editable text = et_pinLun_write.getText();
                s = text.toString();
                if(s.equals("")){

                    Toast.makeText(PinLunActivity.this,"请输入评论内容",Toast.LENGTH_SHORT).show();

                }else {
//---------------------------------------开启数据之路--------------------


                    final Handler handler=new Handler(){

                        private String sr;

                        @Override
                        public void handleMessage(Message msg) {

                            if(msg.what==4){

                                String username = (String) BmobUser.getObjectByKey("username");
                                String icon_url = (String) BmobUser.getObjectByKey("icon_url");


                                Gson gson = new Gson();
                                PinLun pinLun = gson.fromJson(thecomment, PinLun.class);
                                PinLun.ABean mycomment = new PinLun.ABean(icon_url,
                                        s, System.currentTimeMillis(), 0, username);

                                if(pinLun!=null){
                                    List<PinLun.ABean> a = pinLun.getA();

                                    a.add(mycomment);

                                    sr = JsonToString.toJson(pinLun);
                                    Log.e("pp", sr);

                                }else {


                                    ArrayList<PinLun.ABean> aBeen = new ArrayList<>();
                                    aBeen.add(mycomment);
                                    PinLun pinLun1 = new PinLun(aBeen);

                                    sr = JsonToString.toJson(pinLun1);

                                }



                                Recipe recipe = new Recipe();
                                recipe.setThecomment(sr);
                                recipe.update(caipuID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){

                                            Toast.makeText(PinLunActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                                            Log.e(PINLUNACTIVITY,"更新成功");

                                            mActivity.finish();

                                        }else{
                                            Log.e(PINLUNACTIVITY,"更新失败");
                                            mActivity.finish();
                                        }
                                    }
                                });



                            }
                        }
                    };


                    BmobQuery<Recipe> bmobQuery = new BmobQuery<Recipe>();

                    bmobQuery.getObject(caipuID, new QueryListener<Recipe>() {
                        @Override
                        public void done(Recipe object,BmobException e) {
                            if(e==null){
                                recipe=object;

                                thecomment = object.getThecomment();




                                Message message=new Message();
                                message.what=4;
                                handler.sendMessage(message);


                                Log.e("查询成功","h");
                                if(recipe!=null){
                                    Log.e("hahhahhhha","成功了");
                                }
                            }else{
                                Log.e("pinlun","获取服务器数据失败");
                            }
                        }
                    });

                }


                break;





        }

    }
}
