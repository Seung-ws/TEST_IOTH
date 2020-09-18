package com.example.needchat.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server_Socket {
    public String host=null;
    public int port=0;
    public Socket socket=null;
    private  ServerChatAnalyzer sca=null;
    private Queue<String> q=new LinkedList<String>();
    private OutputStream os=null;
    private InputStream is=null;
    private Thread onSender=null;
    private Thread Receive=null;
    public Thread Connection=null;
    public boolean con=false;
    public boolean acc=false;
    boolean c_receive=true;
    public String res=null;

    public Server_Socket()
    {
        socket=new Socket();
        sca=new ServerChatAnalyzer();
    }
    public Server_Socket(String host,String port){
        this.host=host;
        this.port=Integer.parseInt(port);
        socket=new Socket();
        sca=new ServerChatAnalyzer();

    }
    public void sethost(String host)
    {
        this.host=host;
    }
    public void setPort(String p){
        this.port=Integer.parseInt(p);
    }

    public void Accept(){
        JSONObject totalObject=new JSONObject();
        JSONArray arr=new JSONArray();
        JSONObject object=new JSONObject();
        try{
            object.put("client_type","mobile");
            object.put("client_usage","c");
            object.put("client_name","user");
            arr.put(object);
            totalObject.put("#!sys",arr);
        }catch (Exception e)
        {
            System.out.println("JSON오류"+e);
        }
        if(onSend(totalObject.toString()))
        {
            acc=true;
            c_receive=true;
            if(Receive!=null&&Receive.isAlive())
            {
                Receive.interrupt();
            }
            Receive=new Thread(new Receive());
            Receive.start();


        }
    }
    public void connection(){
      if(Connection!=null&&Connection.isAlive())
      {
          return;
      }
      else
      {
          Runnable r=new Runnable() {
              @Override
              public void run(){
                  try{
                      socket.connect(new InetSocketAddress(host,port),5000);
                      os=socket.getOutputStream();
                      is=socket.getInputStream();
                      con=true;

                  }catch(Exception e)
                  {
                      System.out.println("Server_Socket connection error:"+e);
                  }
              }
          };
          Connection=new Thread(r);

      }
      Connection.start();
        try {
            Connection.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public synchronized  String popMsg(String _if)
    {
        long time=System.currentTimeMillis();
        boolean find=false;
        String res=null;
        while(!find&&!(System.currentTimeMillis()-time>10000))
        {
            if(!q.isEmpty()) {
                res = q.poll();
                System.out.println("popMsg:"+res);
                if (res.contains(_if)) {
                    find = true;

                } else {
                    q.add(res);
                    res = null;
                }
            }
        }

       return res;
    }



    public synchronized boolean onSend(final String msg)
    {
        if(socket.isConnected()) {
            if (onSender != null && onSender.isAlive()) {
                return true;
            } else {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {

                            os.write(msg.getBytes(),0,msg.length());
                            os.flush();

                        } catch (Exception e) {
                            System.out.println("SenderError");
                        }
                    }
                };
                onSender = new Thread(r);
            }
            onSender.start();
            try {
                onSender.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }else
        {
            return false;
        }

    }

    public void close(){
        try {
            con=false;
            acc=false;
            c_receive=false;
            if(os!=null)os.close();
            if(is!=null)is.close();
            if(socket!=null)socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Receive implements Runnable{
        @Override
        public void run() {

            int len=0;
            String tmp=null;
            while(con&&acc&&c_receive)
            {
                byte[] b=new byte[4096];
                try{
                    len=is.read(b);
                    tmp=new String(b,0,len,"UTF-8");
                    System.out.println("tmp:"+tmp);
                    if(tmp!=null)
                    {
                        if(tmp.contains("ROK")||tmp.contains("Alive")){

                            }else{
                                q.offer(tmp);
                            }
                        tmp=null;
                    }
                }catch(Exception e)
                {
                    c_receive=false;
                }
            }

        }
    }
}
