package com.project2.mandara.Data;

import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.os.Environment;


import androidx.appcompat.app.AppCompatActivity;

import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class StaticData {


    public static final ArrayList<MandaraFileInfo> MandaraList=new ArrayList<MandaraFileInfo>();


    public static boolean ActiveDisplayControl=false;




    public static String myhost ="1.246.212.140:8090";




    public static BitmapRecord reader=new BitmapRecord();



    //private
    public static Bitmap getImageBitmaps(final String url) {
        final Bitmap[] mb = new Bitmap[1];
        Thread th=new Thread(){
            @Override
            public void run(){

                try {
                    URL aURL = new URL(url);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    mb[0] = BitmapFactory.decodeStream(bis);

                    bis.close();
                    is.close();
                } catch (IOException e) {
                    System.out.println("url -> 이미지변환 오류:"+e);
                }
            }
        };
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            System.out.println("url to 비트맵 변화 에러:"+e);
        }
        return mb[0];
    }

    public static ProgressDialog progressDialog=null;

    public static void process_setmsg(AppCompatActivity c)
    {
        progressDialog=new ProgressDialog(c);
        progressDialog.setIndeterminate(false);
    }
    public static void process_showmsg(String message)
    {
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public static void process_delmsg()
    {
        progressDialog.dismiss();
    }
    public static boolean 처리중문구_여부(){return progressDialog.isShowing();}
}
