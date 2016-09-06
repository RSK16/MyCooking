package com.example.mycooking.activity.shangchuangcaipu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.Buzhou;
import com.example.mycooking.bean.Cailiao;
import com.example.mycooking.bean.Recipe;
import com.example.mycooking.view.TakePhotoPopWin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LoadTwoActivity extends Activity {

    private static final String TAG = "LoadTwoActivity";
    private static  int KONGJIAN = 0;
    //主料
    private RelativeLayout rl_load_tittlepic;
    private ImageButton ib_fengmianicon;
    private String liao_name;
    private String liao_num;
    private TextView tv_add_zhuliao2;
    private TextView tv_add_liaonum2;
    private boolean isADD=false;
    private Recipe recipe;
    private TextView tv_loadtwo_tittle;
    private LinearLayout ll_loadtwo_editzhuliao;
    private TextView tv_loadtwo_zhuliaoming;
    private TextView tv_loadtwo_zhuliaonum;
    private RelativeLayout rl_loadtwo_addbuttun;
    private LinearLayout ll_loadtwo_addll;
    //步骤
    private TextView buzhou_tv_id;
    private EditText buzhou_et_name;
    private ImageButton buzhou_ib_icon;
    private EditText et_buzhou_desc;
    private RelativeLayout buzhoupic_rl_dianji;
    private Button buzhou_btn_add;
    private Button buzhou_btn_edit;
    private LinearLayout buzhou_ll_addll;
    private int stepnumber;//步骤数量

    private Cailiao cailiao;//菜谱材料
    private Cailiao.ListBean zhuliaoBean;//主料
    private Cailiao.ListTBean fuliaoBean;//辅料
    private List<Cailiao.ListBean> zhuliaolist;//主料集合
    private List<Cailiao.ListTBean> zfuliaolist;//辅料集合

    private Buzhou buzhou;//步骤
    private Buzhou.ZuofaBean zuofaBean;//做法
    private List<Buzhou.ZuofaBean> zuofalist;//做法集合
    private TakePhotoPopWin takePhotoPopWin;
    private LinearLayout buzhou_ll_right;
    private boolean isShow;//是否显示右边
    private GridView buzhou_gv_reslutpics;
    private ImageButton ib_addtupian;
    int picsnumber=1;
    private int RESULT_NUM=0;
    private List<Bitmap> piclist;
    private boolean isResult=false;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_two);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        cailiao=new Cailiao();//材料
        zhuliaolist = new ArrayList<Cailiao.ListBean>();
        fuliaoBean = new Cailiao.ListTBean();//辅料
        Log.i(TAG, "onCreate: 8886e87368723"+recipe.getGongyi());
        stepnumber=0;//步骤数量初始化为1
        initView();
        initZhuliaoData();
        initBuzhouData();


    }
    private void initBuzhouData() {
        //步骤里图片点击事件
        buzhoupic_rl_dianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KONGJIAN=20;
                showPopFormBottom();
            }
        });

        //添加步骤点击事件
        buzhou_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepnumber++;
                View inflate = View.inflate(getApplicationContext(), R.layout.buzhou_content2, null);
                TextView buzhou_tv_id2 = (TextView) inflate.findViewById(R.id.buzhou_tv_id2);
                buzhou_tv_id2.setText("第"+(stepnumber+1)+"步");
                EditText buzhou_et_name2 = (EditText) inflate.findViewById(R.id.buzhou_et_name2);
                ImageButton up2 = (ImageButton) inflate.findViewById(R.id.up2);
                ImageButton down2 = (ImageButton) inflate.findViewById(R.id.down2);
                final ImageButton delete2 =  (ImageButton) inflate.findViewById(R.id.delete2);
                //步骤里删除
                delete2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(LoadTwoActivity.this)
                                .setTitle("确认删除吗？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LinearLayout parent = (LinearLayout) delete2.getParent().getParent().getParent();
                                        LinearLayout parent1 = (LinearLayout) parent.getParent();
                                        parent1.removeView(parent);
                                        Log.i(TAG, "onClick: 988887878");
                                    }
                                })
                                .setNegativeButton("取消",null).show();

                    }
                });
                LinearLayout buzhou_ll_right2 = (LinearLayout) inflate.findViewById(R.id.buzhou_ll_right2);
                RelativeLayout buzhoupic_rl_dianji2 = (RelativeLayout) inflate.findViewById(R.id.buzhoupic_rl_dianji2);
                //步骤里图片点击事件
                buzhoupic_rl_dianji2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KONGJIAN=stepnumber;
                        showPopFormBottom();
                    }
                });

                ImageButton buzhou_ib_icon2 = (ImageButton) inflate.findViewById(R.id.buzhou_ib_icon2);

                buzhou_ll_addll.addView(inflate,stepnumber);
            }
        });
        //编辑步骤点击事件
        buzhou_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    //buzhou_ll_right.setVisibility(View.GONE);
                    int childCount = buzhou_ll_addll.getChildCount();
                    Log.i(TAG, "onClick: 3333"+childCount);
                    for (int i=0;i<buzhou_ll_addll.getChildCount();i++) {
                        LinearLayout childAt = (LinearLayout) buzhou_ll_addll.getChildAt(i);
                        LinearLayout childAt1 = (LinearLayout) childAt.getChildAt(0);
                        childAt1.getChildAt(2).setVisibility(View.GONE);
                    }
                } else {
                    int childCount = buzhou_ll_addll.getChildCount();
                    Log.i(TAG, "onClick: 3333"+childCount);
                    for (int i=0;i<buzhou_ll_addll.getChildCount();i++) {
                        LinearLayout childAt = (LinearLayout) buzhou_ll_addll.getChildAt(i);
                        LinearLayout childAt1 = (LinearLayout) childAt.getChildAt(0);
                        childAt1.getChildAt(2).setVisibility(View.VISIBLE);
                    }
                }
                isShow=!isShow;
            }
        });
        //成品图的展示
        piclist = new ArrayList<Bitmap>();
        myAdapter = new MyAdapter();
        buzhou_gv_reslutpics.setAdapter(myAdapter);
        ib_addtupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 12121212121212121212121");
                picsnumber++;
                isResult=true;
                showPopFormBottom();
            }
        });

    }

    private void initZhuliaoData() {
        tv_loadtwo_tittle.setText(recipe.getTitle());
        rl_load_tittlepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KONGJIAN=10;
                showPopFormBottom();
            }
        });
        ll_loadtwo_editzhuliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EditliaoActivity.class));
            }
        });
        rl_loadtwo_addbuttun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = tv_loadtwo_zhuliaoming.getText().toString();
                Log.i(TAG, "onClick: "+string+zhuliaolist.size());
                if (string.equals("添加主料")) {
                    Toast.makeText(LoadTwoActivity.this, "请先填写主料", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(),EditliaoActivity.class));
                    isADD=true;
                    View inflate = View.inflate(getApplicationContext(), R.layout.add_ll, null);
                    tv_add_zhuliao2 = (TextView) inflate.findViewById(R.id.tv_add_zhuliao2);
                    tv_add_liaonum2 = (TextView) inflate.findViewById(R.id.tv_add_liaonum2) ;
                    ll_loadtwo_addll.addView(inflate);
                    isADD=true;
                }
            }
        });
    }

    private void initView() {

        tv_loadtwo_tittle = (TextView) findViewById(R.id.tv_loadtwo_tittle);
        ll_loadtwo_editzhuliao = (LinearLayout) findViewById(R.id.ll_loadtwo_editzhuliao);
        rl_load_tittlepic = (RelativeLayout) findViewById(R.id.rl_load_tittlepic);
        ib_fengmianicon = (ImageButton) findViewById(R.id.ib_fengmianicon);
        //主料
        tv_loadtwo_zhuliaoming = (TextView) findViewById(R.id.tv_loadtwo_zhuliaoming);
        tv_loadtwo_zhuliaonum = (TextView) findViewById(R.id.tv_loadtwo_zhuliaonum);
        rl_loadtwo_addbuttun = (RelativeLayout) findViewById(R.id.rl_loadtwo_addbuttun);
        ll_loadtwo_addll = (LinearLayout) findViewById(R.id.ll_loadtwo_addll);
        //辅料
        //步骤
        buzhou_tv_id = (TextView) findViewById(R.id.buzhou_tv_id);
        buzhou_et_name = (EditText) findViewById(R.id.buzhou_et_name);
        buzhou_ib_icon = (ImageButton) findViewById(R.id.buzhou_ib_icon);
        et_buzhou_desc = (EditText) findViewById(R.id.et_buzhou_desc);
        buzhoupic_rl_dianji = (RelativeLayout) findViewById(R.id.buzhoupic_rl_dianji);


        buzhou_btn_add = (Button) findViewById(R.id.buzhou_btn_add);
        buzhou_btn_edit = (Button) findViewById(R.id.buzhou_btn_edit);
        buzhou_ll_addll = (LinearLayout) findViewById(R.id.buzhou_ll_addll);

        buzhou_ll_right = (LinearLayout) findViewById(R.id.buzhou_ll_right);

        buzhou_gv_reslutpics = (GridView) findViewById(R.id.buzhou_gv_reslutpics);
        ib_addtupian = (ImageButton) findViewById(R.id.ib_addtupian);



        ImageButton up1 = (ImageButton) findViewById(R.id.up1);
        ImageButton down1 = (ImageButton) findViewById(R.id.down1);
        final ImageButton delete1 =  (ImageButton) findViewById(R.id.delete1);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoadTwoActivity.this, "不能没有步骤", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void showPopFormBottom() {
        takePhotoPopWin = new TakePhotoPopWin(this, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_take_photo:
                    System.out.println("btn_take_photo");
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera, 100);
                    } else {
                        Toast.makeText(LoadTwoActivity.this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_pick_photo:
                    System.out.println("btn_pick_photo");
                    Intent picture = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    picture.setType("image/*");//相片类型
                    startActivityForResult(picture, 200);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri == null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型
                    String spath="aa.jpg";
                    saveImage( photo,  spath);
                } else {
                    Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //do find the path of pic by uri
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Log.i(TAG, "onActivityResult: ++++++++++" + selectedImage.getPath());

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            doBitmap(bitmap);
            buzhou_gv_reslutpics.setAdapter(myAdapter);
            takePhotoPopWin.dismiss();
        }



    }

    private void doBitmap(Bitmap bitmap) {
        Log.i(TAG, "doBitmap: "+KONGJIAN+stepnumber+RESULT_NUM+picsnumber);
        if (KONGJIAN==10&&isResult==false) {
            ib_fengmianicon.setImageBitmap(bitmap);
        } else if (KONGJIAN==20&&isResult==false) {
            buzhou_ib_icon.setImageBitmap(bitmap);
        }
        if (KONGJIAN == stepnumber&&isResult==false) {
            LinearLayout childAt = (LinearLayout) buzhou_ll_addll.getChildAt(stepnumber);
            LinearLayout childAt1 = (LinearLayout) childAt.getChildAt(0);
            LinearLayout childAt2 = (LinearLayout) childAt1.getChildAt(1);
            RelativeLayout childAt3 = (RelativeLayout) childAt2.getChildAt(1);
            ImageButton childAt4 = (ImageButton) childAt3.getChildAt(0);
            childAt4.setImageBitmap(bitmap);
        }
        if (isResult) {
            piclist.add(0,bitmap);
            isResult=false;
            Log.i(TAG, "doBitmap: reslut"+piclist.size());
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        liao_name = intent.getStringExtra("liao_name");
        liao_num = intent.getStringExtra("liao_num");
        Log.i(TAG, "onNewIntent: 11"+liao_name+liao_num);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (liao_name != null && liao_num != null) {
            if (isADD) {
                Log.i(TAG, "onResume:add00 " + liao_name + liao_num);
                tv_add_zhuliao2.setText(liao_name);
                tv_add_liaonum2.setText(liao_num);
            } else {
                Log.i(TAG, "onResume:first " + liao_name + liao_num);
                tv_loadtwo_zhuliaoming.setText(liao_num);
                tv_loadtwo_zhuliaonum.setText(liao_name);
            }
            zhuliaoBean = new Cailiao.ListBean();//主料
            zhuliaoBean.setTitle(liao_name);
            zhuliaoBean.setNum(liao_num);
            zhuliaolist.add(zhuliaoBean);
        }
        zhuliaoBean=null;
    }

    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            //return false;
        }
        //return true;
    }
    /**
     * 获取相机拍照的图片
     * @param
     * @return
     */
    private String getCameraImage(Bundle bundle) {
        String strState = Environment.getExternalStorageState();
        if (!strState.equals(Environment.MEDIA_MOUNTED))
        {
            Log.i("Test File", "SD card is not available/writealble right now!");
        }
        String fileName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        // Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        File file = new File("/sdcard/com.test.diary/");
        if (!file.exists())
        {
            file.mkdirs();
        }
        fileName = "/sdcard/com.test.diary/" + fileName;
        FileOutputStream stream = null;
        try
        {
            stream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (stream != null)
                {
                    stream.flush();
                    stream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        // ((ImageView) findViewById(R.id.iv_temp)).setImageBitmap(bitmap);
        return fileName;
    }



    /**
     * 获取系统缺省路径选择的图片
     * @param
     * @return
     */
    private String getPhoneImage(String uriString) {

        // Uri selectedImage = data.getData();
        Uri selectedImage = Uri.parse(uriString);
        String[] filePathColumns = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
        String fileName = cursor.getString(columnIndex);
        cursor.close();

        // ((ImageView) findViewById(R.id.iv_temp)).setImageBitmap(BitmapFactory.decodeFile(picturePath));
        return fileName;
    }
    public void ib_previous2(View view) {
        finish();
    }
    public void fabu(View view) {

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return piclist.size();
        }

        @Override
        public Object getItem(int position) {
            return piclist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(getApplicationContext(), R.layout.item_buzhouresultpics_gridview, null);
            ImageView delete_pic = (ImageView) inflate.findViewById(R.id.delete_pic2);
            ImageButton ib_tupian2 = (ImageButton) inflate.findViewById(R.id.ib_tupian2);
            ib_tupian2.setImageBitmap(piclist.get(position));
            return inflate;
        }
    }
}
