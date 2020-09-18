package com.project2.mandara.Data.OpenSave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.project2.mandara.Data.StaticData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Stack;

public class Server_MandaraImageOffer {


    public Stack<JSONObject> ImageOffer(){
        Stack<JSONObject> stack=new Stack<JSONObject>();

        String ToServer="http://"+StaticData.myhost +"/Mandara/MandaraListOffer";
        String []res=new String[1];
        res[0]="";
        try{
            URL url=new URL(ToServer);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();
            if(conn.getResponseCode()==conn.HTTP_OK)
            {
                System.out.println("옭게감");
                InputStreamReader isr=new InputStreamReader(conn.getInputStream());
                BufferedReader br=new BufferedReader(isr);
                String tmp=null;
                while((tmp=br.readLine())!=null)
                {
                    res[0]+=tmp;
                }
                JSONObject jsonObject=new JSONObject(res[0]);
                JSONArray jsonArray=jsonObject.getJSONArray("MandaraData");
                for(int i=jsonArray.length()-1;-1<i;i--)
                {
                    JSONObject obj_tmp=jsonArray.getJSONObject(i);
                    JSONObject obj=new JSONObject();
                    obj.put("Num",obj_tmp.getString("Num"));
                    obj.put("Name",obj_tmp.getString("Name"));
                    obj.put("Address",getTargetOfferImage(obj_tmp.getString("Num"),obj_tmp.getString("Name")));
                    stack.push(obj);
                }
                br.close();
                isr.close();
            }


        }catch(Exception e)
        {
            System.out.println("만다라도안 이미지 리스트 가져오기 에러:"+e);
        }
        return stack;
    }
    public Bitmap AddressToBitmap(final String address) {
        final Bitmap[] b = new Bitmap[1];
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
                    b[0] = BitmapFactory.decodeStream(bufferedInputStream);
                    bufferedInputStream.close();
                } catch (Exception e) {
                    System.out.println("Server_MandaraImageOffer : AddressToBitmap 오류 =" + e);
                }

            }
        };
        Thread th = new Thread(r);
        th.start();
        while (th.isAlive()) {}
        return b[0];
     }













    private String getTargetOfferImage(String No,String name)
    {
        return "http://"+StaticData.myhost +"/Mandara/MandaraTargetIconOffer?Num="+No+"&MandaraName="+name;
    }
}
