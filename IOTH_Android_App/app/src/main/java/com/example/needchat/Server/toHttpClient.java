package com.example.needchat.Server;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class toHttpClient {
    String url=null;
    String sign=null;
    Thread sender=null;
    public toHttpClient(String url,String sign){
        this.url=url;
        this.sign=sign;
    }
    public void con(){
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try{
                    URL u=new URL(url);
                    HttpURLConnection conn=(HttpURLConnection)u.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream());
                    BufferedWriter bw=new BufferedWriter(osw);
                    bw.write("sign="+sign);
                    bw.flush();
                    bw.close();
                    osw.close();
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }

        };
        if(sender!=null&&!sender.isAlive())
        {
            sender=new Thread(r);
            sender.start();
        }else{
            sender=new Thread(r);
            sender.start();
        }


    }



}
