package com.example.mycooking.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mycooking.R;
import com.example.mycooking.bean.Categories;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MenuSortActivity extends AppCompatActivity {

    private static final String TAG = "MenuSortActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sort);

        getSupportActionBar().hide();
        //将本地的json 解析放入java bean
        parseJsonFromLocal(this,"category.json");

    }

    public void back(View v){

        finish();
    }

    public static void parseJsonFromLocal(Context context, String jsonfileName) {

        File file = new File("/data/data/" + context.getPackageName() + "/" + jsonfileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String json="";
            while ((json = bufferedReader.readLine())!=null){
                stringBuilder.append(json);
            }
            Log.i(TAG,stringBuilder.toString());

            /*Gson gson = new Gson();
            Categories categories = gson.fromJson(jsonfileName, Categories.class);*/

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }



    }

}
