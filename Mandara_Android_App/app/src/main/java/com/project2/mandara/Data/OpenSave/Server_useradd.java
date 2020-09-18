package com.project2.mandara.Data.OpenSave;

import com.project2.mandara.Data.StaticData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server_useradd {
   private String ToServer = "http://"+ StaticData.myhost +"/Mandara/UserAdder";
   private URL url=null;
   private HttpURLConnection conn=null;
   private OutputStreamWriter osw=null;
   private BufferedWriter bw=null;
   private String g_uid=null;
   private String toMessage=null;
   private String resultMessage=null;

   public Server_useradd(String g_uid){

           this.g_uid=g_uid;
           toMessage="g_uid="+this.g_uid;
            Thread th=new Thread(new Server_Con());
            th.start();
            while(th.isAlive()){}
   }
   class Server_Con implements Runnable {

       @Override
       public void run() {
           runs();
           System.out.println("정상종료됨?");
       }
   }
   private void runs(){
       try {
           url = new URL(ToServer);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("POST");
           conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           conn.setConnectTimeout(10000);
           conn.setReadTimeout(10000);
           osw = new OutputStreamWriter(conn.getOutputStream());
           bw = new BufferedWriter(osw);
           bw.write(toMessage);
           bw.flush();
           if (conn.getResponseCode() == conn.HTTP_OK) {
               InputStreamReader isr = new InputStreamReader(conn.getInputStream());
               BufferedReader br = new BufferedReader(isr);
               String tmp = null;
               while ((tmp = br.readLine()) != null) {
                   resultMessage += tmp;
               }
               System.out.println(resultMessage);//등록이 되면 True 안되면 False 존재하면??
               br.close();
               isr.close();
           }
       }catch(Exception e)
       {
        System.out.println(e);
       }
       try {
           bw.close();
           osw.close();
       } catch (IOException e) {
           e.printStackTrace();
       }


   }
}
