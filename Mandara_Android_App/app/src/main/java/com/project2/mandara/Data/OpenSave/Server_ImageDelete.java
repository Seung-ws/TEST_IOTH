package com.project2.mandara.Data.OpenSave;

import com.project2.mandara.Data.StaticData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server_ImageDelete {

    public boolean ImageDeleter(final String g_uid,final String toName,final String I_type){
        final String ToServer="http://"+ StaticData.myhost +"/Mandara/UserImageDelete";
        final boolean[] res=new boolean[1];
        res[0]=false;
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL(ToServer);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream());
                    BufferedWriter bw=new BufferedWriter(osw);
                    bw.write("g_uid="+g_uid);
                    bw.write("&toName="+toName);
                    bw.write("&I_type="+I_type);
                    bw.flush();
                    bw.close();
                    osw.close();
                    if(conn.getResponseCode()==conn.HTTP_OK)
                    {
                        InputStreamReader isr=new InputStreamReader(conn.getInputStream());
                        BufferedReader br=new BufferedReader(isr);
                        String tmp=null;
                        while((tmp=br.readLine())!=null)
                        {
                            res[0]=Boolean.parseBoolean(tmp);
                        }
                    }
                }catch(Exception e)
                {
                    System.out.println("Server_ImageDelete : ImageDeleter="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}
        return res[0];
    }
}
