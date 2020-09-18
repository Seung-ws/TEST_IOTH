package com.project2.mandara.Data.OpenSave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.project2.mandara.Data.StaticData;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class Server_getOfferImage {
    String Num=null;
    String Name=null;
    public Server_getOfferImage(String Num,String Name){
        this.Num=Num;
        this.Name=Name;
    }

    public Bitmap setBitmapPatch(){
        final String ToServer="http://"+ StaticData.myhost +"/Mandara/MandaraTargetImageOffer?Num="+Num+"&MandaraName="+Name;
        final Bitmap [] b=new Bitmap[1];
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL(ToServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]=BitmapFactory.decodeStream(bis);
                }catch(Exception e)
                {
                    System.out.println("제공이미지 비트맵 변환 에러 : "+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}
        return b[0];
    }


}
