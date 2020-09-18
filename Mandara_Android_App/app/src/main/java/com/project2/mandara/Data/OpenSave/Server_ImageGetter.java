package com.project2.mandara.Data.OpenSave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.project2.mandara.Data.StaticData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Server_ImageGetter {


    public String getImageList(final String g_uid,final String I_type){
        final String ToServer="http://"+ StaticData.myhost +"/Mandara/UserImageGetter";
        String tmp=null;
        final String[] res=new String[]{""};
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try{
                    String tmp=null;
                    URL url=new URL(ToServer);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream());
                    BufferedWriter bw=new BufferedWriter(osw);
                    bw.write("g_uid="+g_uid);
                    bw.write("&I_type="+I_type);
                    bw.flush();
                    bw.close();
                    osw.close();
                    if(conn.getResponseCode()==conn.HTTP_OK)
                    {
                        InputStreamReader isr=new InputStreamReader(conn.getInputStream());
                        BufferedReader br=new BufferedReader(isr);
                        while((tmp=br.readLine())!=null){
                            res[0]+=tmp;
                        }
                        br.close();
                        isr.close();
                    }
                }catch(Exception e)
                {
                    System.out.println("저장 이미지 목록 가져오기 오류"+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            System.out.println("저장이미지목록가져오기에러:"+e);
        }
        return res[0];
    }
    public JSONArray getUserSaveImageJSONList(String g_uid,String I_type){
        JSONArray jsonArray=null;
        try{
            JSONObject jsonObject=new JSONObject(getImageList(g_uid,I_type));
            jsonArray=jsonObject.getJSONArray("UserData");

        }catch(Exception e)
        {
            System.out.println("JSONOBject 가져오기 에러:"+e);
        }

        return jsonArray;

    }

    public Bitmap getUserIconImageBitmap(final String g_uid,final String toName,final String I_type)
    {
        final Bitmap [] b=new Bitmap[1];
        final  String toServer="http://"+ StaticData.myhost +"/Mandara/IconImageGetter?g_uid="+g_uid+"&toName="+toName+"&I_type="+I_type;
        Runnable r=new Runnable() {
            @Override
            public void run() {

                try{
                    URL url=new URL(toServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]=BitmapFactory.decodeStream(bis);
                    bis.close();
                }catch(Exception e)
                {
                    System.out.println("Server_ImageGetter : getUserIconImageBitmap 오류 ="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}


        return b[0];
    }
    public Bitmap getUserMirrorImageBitmap(final String g_uid,final String toName,final String I_type)
    {
        final Bitmap [] b=new Bitmap[1];
        final String toServer="http://"+ StaticData.myhost +"/Mandara/MirrorImageGetter?g_uid="+g_uid+"&toName="+toName+"&I_type="+I_type;
        Runnable r=new Runnable() {
            @Override
            public void run() {

                try{
                    URL url=new URL(toServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]=BitmapFactory.decodeStream(bis);
                    bis.close();
                }catch(Exception e)
                {
                    System.out.println("Server_ImageGetter : getUserIconImageBitmap 오류 ="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}


        return b[0];

    }
    public Bitmap getUserMainImageBitmap(final String g_uid,final String toName,final String I_type)
    {
        final Bitmap [] b=new Bitmap[1];
        final String toServer="http://"+ StaticData.myhost +"/Mandara/MainImageGetter?g_uid="+g_uid+"&toName="+toName+"&I_type="+I_type;

        Runnable r=new Runnable() {
            @Override
            public void run() {

                try{
                    URL url=new URL(toServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]=BitmapFactory.decodeStream(bis);
                    bis.close();
                }catch(Exception e)
                {
                    System.out.println("Server_ImageGetter : getUserIconImageBitmap 오류 ="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}


        return b[0];
    }
    public Bitmap getUserOriginImageBitmap(String g_uid,String toName,String I_type)
    {
        final Bitmap [] b=new Bitmap[1];
        final String toServer= "http://"+ StaticData.myhost +"/Mandara/OriginImageGetter?g_uid="+g_uid+"&toName="+toName+"&I_type="+I_type;


        Runnable r=new Runnable() {
            @Override
            public void run() {

                try{
                    URL url=new URL(toServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]=BitmapFactory.decodeStream(bis);
                    bis.close();
                }catch(Exception e)
                {
                    System.out.println("Server_ImageGetter : getUserIconImageBitmap 오류 ="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}


        return b[0];
    }


}
