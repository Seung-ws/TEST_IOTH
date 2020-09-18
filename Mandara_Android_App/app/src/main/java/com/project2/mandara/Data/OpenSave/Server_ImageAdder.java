package com.project2.mandara.Data.OpenSave;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.EditText;

import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.Data.StaticData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server_ImageAdder {
    private String ToServer =null;
    private URL url=null;
    private HttpURLConnection conn=null;

    private DataOutputStream dos=null;

    private String filename=null;
    private String g_iconImage=null;
    private String g_originImage=null;
    private String g_mirrorImage=null;
    private String g_mainImage=null;
    private FileInputStream fis=null;

    private String g_uid=null;
    private String I_type=null;
    private String toName=null;
    private String resultMessage=null;
    private Context c=null;
    private MandaraFileInfo mfi=null;

    public Server_ImageAdder(String g_uid,MandaraFileInfo mfi,Context c){

        this.g_uid=g_uid;
        this.I_type=mfi.getCommandType();
        this.toName=mfi.getArtName();
        this.c=c;
        this.g_iconImage=c.getCacheDir().toString()+"\\iconImage.jpeg";
        this.g_originImage=c.getCacheDir().toString()+"\\originImage.png";
        this.g_mirrorImage=c.getCacheDir().toString()+"\\mirrorImage.png";
        this.g_mainImage=c.getCacheDir().toString()+"\\mainImage.png";

        this.mfi=mfi;


    }
    private void run(final Bitmap ori,final Bitmap Mirr,final Bitmap mainB){
        ToServer="http://"+ StaticData.myhost +"/Mandara/UserImageAdder?g_uid="+g_uid+"&I_type="+I_type+"&toName="+toName;
        Thread th=new Thread(){

            @Override
            public void run() {
                if(mainB!=null) cache_icon_build_ori(mainB);
                if(mainB!=null) cahce_file_build_main(mainB);
                if(Mirr!=null) cache_file_build_mirror(Mirr);
                if(ori!=null) cache_build_ori(ori);
                if(ori!=null&&Mirr!=null&&mainB!=null)
                {
                    Thread th1=new Thread(new Server_IconUpload());
                    Thread th2=new Thread(new Server_OriginUpload());
                    Thread th3=new Thread(new Server_MirrorUpload());
                    Thread th4=new Thread(new Server_MainBUpload());

                    th1.start();
                    while (th1.isAlive()){}
                    th2.start();
                    while (th2.isAlive()){}
                    th3.start();
                    while (th3.isAlive()){}
                    th4.start();
                    while (th4.isAlive()){}

                }
            }
        };
        th.start();
        while(th.isAlive()){}

    }
    public void AlertSaveDialog(final Bitmap ori,final Bitmap mirr,final Bitmap mainB,final Context c)
    {

        final EditText edittext=new EditText(c);
        if(mfi.getArtName()!=null) edittext.setText(mfi.getArtName());
        if(toName!=null) edittext.setText(toName);
        AlertDialog.Builder adb=new AlertDialog.Builder(c);


        adb.setTitle("저장 대화상자").setMessage("이름을 입력해주세요").setView(edittext)
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(edittext.getText().toString().length()>0)
                        {
                            if(!AlterDialog_겹쳐쓰기여부(g_uid,edittext.getText().toString(),I_type))//겹쳐쓸게없으면 false
                            {
                                StaticData.process_showmsg("데이터를 저장하고 있습니다...");
                                toName=edittext.getText().toString();
                                run(ori,mirr,mainB);
                                StaticData.process_delmsg();


                            }else
                            {

                                AlertDialog.Builder adb2=new AlertDialog.Builder(c);
                                adb2.setTitle("오류").setMessage("같은 이름의 이미지가있습니다.").setPositiveButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setNeutralButton("덮어쓰기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        StaticData.process_showmsg("데이터를 저장하고 있습니다..");
                                        toName=edittext.getText().toString();
                                        run(ori,mirr,mainB);
                                        StaticData.process_delmsg();
                                    }
                                }).show();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder adb2=new AlertDialog.Builder(c);
                            adb2.setTitle("오류").setMessage("이름이 입력되지 않았습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                        }
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }

    private boolean AlterDialog_겹쳐쓰기여부(final String g_uid,final String toName,final String I_type)
    {
        StaticData.process_showmsg("중복 검사 중..");
        final boolean []res=new boolean[1];
        res[0]=false;
        Runnable r=new Runnable() {
            @Override
            public void run() {
                String OverWriteDetecting="http://"+ StaticData.myhost +"/Mandara/ImageOverWriting";

                try {
                    URL url=new URL(OverWriteDetecting);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
                        res[0]=Boolean.parseBoolean(br.readLine());
                        System.out.println(res[0]+"이뭐냐");
                        br.close();
                        isr.close();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}
        StaticData.process_delmsg();

        return res[0];
    }



    private void cache_icon_build_ori(final Bitmap bitmap)
    {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(c.getCacheDir().toString()+"\\iconImage.jpeg");
            Bitmap.createScaledBitmap(bitmap,500,500,false).compress(Bitmap.CompressFormat.JPEG, 100, out);
                 out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cache_build_ori(final Bitmap bitmap)
    {
        FileOutputStream out = null;
        try {

            out= new FileOutputStream(c.getCacheDir().toString()+"\\originImage.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cahce_file_build_main(final Bitmap bitmap)
    {
        FileOutputStream out = null;
        try {

            out= new FileOutputStream(c.getCacheDir().toString()+"\\mainImage.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cache_file_build_mirror(final Bitmap bitmap)
    {
        FileOutputStream out = null;
        try {

            out= new FileOutputStream(c.getCacheDir().toString()+"\\mirrorImage.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class Server_IconUpload implements Runnable{
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        @Override
        public void run() {
            try {
                url=new URL(ToServer);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Enctype","multipart/form-data");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                conn.setRequestProperty("iconImage",g_iconImage);


                dos=new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"IconImage\";filename=\""+g_iconImage+"\""+lineEnd);
                dos.writeBytes(lineEnd);


                fis=new FileInputStream(new File(g_iconImage));
                int byteAvailable= fis.available();
                int maxBuffersize=1024*1024;
                int bufferSize=Math.min(byteAvailable,maxBuffersize);
                byte[] b=new byte[bufferSize];
                int bytesRead=fis.read(b,0,bufferSize);
                while(bytesRead>0)
                {
                    dos.write(b,0,bufferSize);
                    byteAvailable=fis.available();
                    bufferSize=Math.min(byteAvailable,maxBuffersize);
                    bytesRead=fis.read(b,0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                /*for(int len=-1;len!=-1;len=fis.read(b))
                {
                    dos.write(b,0,len);
                }*/
                fis.close();
                dos.flush();
                dos.close();
                if(conn.getResponseCode()==conn.HTTP_OK)
                {
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader br=new BufferedReader(isr);
                    resultMessage=br.readLine();
                    System.out.println("결과"+resultMessage);
                }
            } catch (Exception e) {
                System.out.println("문제:"+e);
            }
        }
    }
    class Server_OriginUpload implements Runnable{
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        @Override
        public void run() {
            try {
                url=new URL(ToServer);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Enctype","multipart/form-data");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                conn.setRequestProperty("originImage",g_originImage);


                dos=new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"originImage\";filename=\""+g_originImage+"\""+lineEnd);
                dos.writeBytes(lineEnd);


                fis=new FileInputStream(new File(g_originImage));
                int byteAvailable= fis.available();
                int maxBuffersize=1024*1024;
                int bufferSize=Math.min(byteAvailable,maxBuffersize);
                byte[] b=new byte[bufferSize];
                int bytesRead=fis.read(b,0,bufferSize);
                while(bytesRead>0)
                {
                    dos.write(b,0,bufferSize);
                    byteAvailable=fis.available();
                    bufferSize=Math.min(byteAvailable,maxBuffersize);
                    bytesRead=fis.read(b,0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                /*for(int len=-1;len!=-1;len=fis.read(b))
                {
                    dos.write(b,0,len);
                }*/
                fis.close();
                dos.flush();
                dos.close();
                if(conn.getResponseCode()==conn.HTTP_OK)
                {
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader br=new BufferedReader(isr);
                    resultMessage=br.readLine();
                    System.out.println("결과"+resultMessage);
                }
            } catch (Exception e) {
                System.out.println("문제:"+e);
            }
        }
    }
    class Server_MirrorUpload implements Runnable{
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        @Override
        public void run() {
            try {
                url=new URL(ToServer);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Enctype","multipart/form-data");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                conn.setRequestProperty("mirrorImage",g_mirrorImage);


                dos=new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"mirrorImage\";filename=\""+g_mirrorImage+"\""+lineEnd);
                dos.writeBytes(lineEnd);


                fis=new FileInputStream(new File(g_mirrorImage));
                int byteAvailable= fis.available();
                int maxBuffersize=1024*1024;
                int bufferSize=Math.min(byteAvailable,maxBuffersize);
                byte[] b=new byte[bufferSize];
                int bytesRead=fis.read(b,0,bufferSize);
                while(bytesRead>0)
                {
                    dos.write(b,0,bufferSize);
                    byteAvailable=fis.available();
                    bufferSize=Math.min(byteAvailable,maxBuffersize);
                    bytesRead=fis.read(b,0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                /*for(int len=-1;len!=-1;len=fis.read(b))
                {
                    dos.write(b,0,len);
                }*/
                fis.close();
                dos.flush();
                dos.close();
                if(conn.getResponseCode()==conn.HTTP_OK)
                {
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader br=new BufferedReader(isr);
                    resultMessage=br.readLine();
                    System.out.println("결과"+resultMessage);
                }
            } catch (Exception e) {
                System.out.println("문제:"+e);
            }
        }
    }
    class Server_MainBUpload implements Runnable{
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        @Override
        public void run() {
            try {
                url=new URL(ToServer);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Enctype","multipart/form-data");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                conn.setRequestProperty("mainImage",g_mainImage);


                dos=new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);

                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"g_mainImage\";filename=\""+g_mainImage+"\""+lineEnd);
                dos.writeBytes(lineEnd);


                fis=new FileInputStream(new File(g_mainImage));
                int byteAvailable= fis.available();
                int maxBuffersize=1024*1024;
                int bufferSize=Math.min(byteAvailable,maxBuffersize);
                byte[] b=new byte[bufferSize];
                int bytesRead=fis.read(b,0,bufferSize);
                while(bytesRead>0)
                {
                    dos.write(b,0,bufferSize);
                    byteAvailable=fis.available();
                    bufferSize=Math.min(byteAvailable,maxBuffersize);
                    bytesRead=fis.read(b,0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

                /*for(int len=-1;len!=-1;len=fis.read(b))
                {
                    dos.write(b,0,len);
                }*/
                fis.close();
                dos.flush();
                dos.close();
                if(conn.getResponseCode()==conn.HTTP_OK)
                {
                    InputStreamReader isr=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader br=new BufferedReader(isr);
                    resultMessage=br.readLine();
                    System.out.println("결과"+resultMessage);
                }
            } catch (Exception e) {
                System.out.println("문제:"+e);
            }
        }
    }

}
