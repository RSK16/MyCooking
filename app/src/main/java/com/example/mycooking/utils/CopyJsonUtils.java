package com.example.mycooking.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/4.
 */
public class CopyJsonUtils {

    //将json数据Copy到本地  /data/data
    public static void copyJson(Context context, String jsonfileName){

        File jsonfile = new File("data/data/" + context.getPackageName() + "/" + jsonfileName);
        if(jsonfile.exists()){
            return;
        }
        //第一次进APP
        AssetManager assetManager = context.getAssets();
        try {
            FileOutputStream fos = new FileOutputStream(jsonfile);
            InputStream open = assetManager.open(jsonfileName);

            byte[] bytes = new byte[1024];
            int len=-1;
            while ((len=open.read(bytes,0,1024))!=-1){
                fos.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
