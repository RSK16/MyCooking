package com.example.mycooking.activity.shangchuangcaipu;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycooking.R;
import com.example.mycooking.bean.Recipe;
import com.example.mycooking.view.TakePhotoPopWin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class LoadTwoActivity extends Activity {

    private static final String TAG = "LoadTwoActivity";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_two);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        Log.i(TAG, "onCreate: 8886e87368723"+recipe.getGongyi());
        initView();
        tv_loadtwo_tittle.setText(recipe.getTitle());
        rl_load_tittlepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                isADD=true;
                startActivity(new Intent(getApplicationContext(),EditliaoActivity.class));

                View inflate = View.inflate(getApplicationContext(), R.layout.add_ll, null);
                tv_add_zhuliao2 = (TextView) inflate.findViewById(R.id.tv_add_zhuliao2);
                tv_add_liaonum2 = (TextView) inflate.findViewById(R.id.tv_add_liaonum2) ;
                ll_loadtwo_addll.addView(inflate);
            }
        });


    }

    private void initView() {

        tv_loadtwo_tittle = (TextView) findViewById(R.id.tv_loadtwo_tittle);
        ll_loadtwo_editzhuliao = (LinearLayout) findViewById(R.id.ll_loadtwo_editzhuliao);
        rl_load_tittlepic = (RelativeLayout) findViewById(R.id.rl_load_tittlepic);
        ib_fengmianicon = (ImageButton) findViewById(R.id.ib_fengmianicon);

        tv_loadtwo_zhuliaoming = (TextView) findViewById(R.id.tv_loadtwo_zhuliaoming);
        tv_loadtwo_zhuliaonum = (TextView) findViewById(R.id.tv_loadtwo_zhuliaonum);
        rl_loadtwo_addbuttun = (RelativeLayout) findViewById(R.id.rl_loadtwo_addbuttun);
        ll_loadtwo_addll = (LinearLayout) findViewById(R.id.ll_loadtwo_addll);

    }
    public void showPopFormBottom() {
        TakePhotoPopWin takePhotoPopWin = new TakePhotoPopWin(this, onClickListener);
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
            ib_fengmianicon.setImageBitmap(bitmap);
//            Drawable bitmapDrawable = new BitmapDrawable(bitmap);
//            rl_load_tittlepic.setBackground(bitmapDrawable);
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
        if (isADD) {
            Log.i(TAG, "onResume:add00 " + liao_name + liao_num);
            tv_add_zhuliao2.setText(liao_name);
            tv_add_liaonum2.setText(liao_num);
        } else {
            if (liao_name != null && liao_num != null) {
                Log.i(TAG, "onResume:first " + liao_name + liao_num);
                tv_loadtwo_zhuliaoming.setText(liao_num);
                tv_loadtwo_zhuliaonum.setText(liao_name);
            }
        }



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
    private String getCameraImage(Bundle bundle)
    {
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
    private String getPhoneImage(String uriString)
    {

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
}
