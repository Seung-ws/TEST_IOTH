package com.example.needchat;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.needchat.Data.staticData;
import com.example.needchat.Server.Server_Socket;
import com.example.needchat.menu_tab1.tab1_Fragment;
import com.example.needchat.menu_tab2.tab2_Fragment;
import com.example.needchat.menu_tab3.tab3_Fragment;
import com.example.needchat.menu_tab32.tab32_Fragment;
import com.example.needchat.menu_tab35.tab35_Fragment;
import com.example.needchat.menu_tab4.tab4_Fragment;
import com.example.needchat.menu_tab4.tab4_usersetting;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class menuActivity extends AppCompatActivity {
    public tab4_usersetting tab4us=null;
    public Thread th=null;
    public boolean tab0=false;


    @Override
    public void onBackPressed() {


            super.onBackPressed();
        tab0=false;
        File f= new File(this.getCacheDir().toString()+"\\userdata.dat");
        try {
            //(this).tab4us.host=(host.getText().toString());
            //(this).tab4us.port=(port.getText().toString());

            FileOutputStream fis=new FileOutputStream(f);
            ObjectOutputStream oos=new ObjectOutputStream(fis);
            oos.writeObject(this.tab4us);
            oos.flush();
            oos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tab4us=(tab4_usersetting)getIntent().getSerializableExtra("usersetting");





    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tab4us.autoconnect)
        {
            staticData.ss=new Server_Socket(tab4us.host,tab4us.port);
            reConnect();
        }else
        {
            staticData.ss=new Server_Socket();
        }
    }

    @Override
    protected void onPause() {
        staticData.ss.close();
        super.onPause();

    }


    class cx implements Runnable{
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(), "연결시도 시간 10초..", Toast.LENGTH_LONG).show();
                }
            });
            if(staticData.ss.host.length()>0){
                    staticData.ss.connection();
                    boolean state1 = false;
                    long time1 = System.currentTimeMillis();
                    while (!state1 && !(System.currentTimeMillis() - time1 > 5000)) {
                        if (staticData.ss.con) {
                            state1 = true;
                        }
                    }
                    staticData.ss.Accept();
                    boolean state2 = false;
                    long time2 = System.currentTimeMillis();
                    while (state1 && !state2 && !(System.currentTimeMillis() - time2 > 5000)) {
                        if (staticData.ss.acc) {
                            state2 = true;
                        }
                    }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(staticData.ss.acc&&staticData.ss.con)
                    {
                        Toast.makeText(getApplicationContext(),"연결성공",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"연결실패",Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

    public void reConnect(){
        if(th!=null&&th.isAlive())th.interrupt();
        th=new Thread(new cx());
        th.start();
    }

    public void Tab1_dragView(View v)
    {

        if(!tab0&&tab4us.gpsswitch)
        {
            tab0=true;
            tab1_Fragment fragment = new tab1_Fragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }
    public void Tab2_dragView(View v)
    {
        if(!tab0&&staticData.ss.acc) {
            tab0 = true;
            tab2_Fragment fragment = new tab2_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }
    public void Tab3_dragView(View v)
    {
        if(!tab0&&staticData.ss.acc) {
            tab0 = true;
            tab3_Fragment fragment = new tab3_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }
    public void Tab3_dialog(final String to){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("장치 사용 설정");
//        builder.setMessage("장치 사용을 중지 할 수 있습니다.");
        builder.setPositiveButton("On",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject totalobject=new JSONObject();
                        JSONArray jsonArr=new JSONArray();
                        JSONObject object=new JSONObject();
                        try {
                            object.put("client_name",to);
                            object.put("sign","tr");
                            jsonArr.put(object);
                            totalobject.put("#!toMsg",jsonArr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        staticData.ss.onSend(totalobject.toString());
                        Toast.makeText(getApplicationContext(),"활성화",Toast.LENGTH_LONG).show();

                    }
                });
        builder.setNegativeButton("Off",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject totalobject=new JSONObject();
                        JSONArray jsonArr=new JSONArray();
                        JSONObject object=new JSONObject();
                        try {
                            object.put("client_name",to);
                            object.put("sign","fa");
                            jsonArr.put(object);
                            totalobject.put("#!toMsg",jsonArr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        staticData.ss.onSend(totalobject.toString());
                        Toast.makeText(getApplicationContext(),"비활성화",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();

    }
    public void Tab3_dialog_LED(final String to){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
       final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        final String str[] = {"침실","화장실","주방","전체"};
        builder.setTitle("홈 조명설정")
                .setNegativeButton("취소", null)
                .setItems(str, // 리스트 목록에 사용할 배열
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {

                                builder2.setTitle(str[which]+"조명 설정");
//        builder.setMessage("장치 사용을 중지 할 수 있습니다.");
                                builder2.setPositiveButton("On",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which2) {
                                                JSONObject totalobject=new JSONObject();
                                                JSONArray jsonArr=new JSONArray();
                                                JSONObject object=new JSONObject();
                                                try {
                                                    object.put("client_name",to);
                                                    object.put("sign","tr"+which);
                                                    jsonArr.put(object);
                                                    totalobject.put("#!toMsg",jsonArr);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                staticData.ss.onSend(totalobject.toString());
                                                Toast.makeText(getApplicationContext(),"활성화",Toast.LENGTH_LONG).show();

                                            }
                                        });
                                builder2.setNegativeButton("Off",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,final int which2) {
                                                JSONObject totalobject=new JSONObject();
                                                JSONArray jsonArr=new JSONArray();
                                                JSONObject object=new JSONObject();
                                                try {
                                                    object.put("client_name",to);
                                                    object.put("sign","fa"+which);
                                                    jsonArr.put(object);
                                                    totalobject.put("#!toMsg",jsonArr);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                staticData.ss.onSend(totalobject.toString());
                                                Toast.makeText(getApplicationContext(),"비활성화",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                builder2.show();

                            }
                        }); // 클릭 리스너



        builder.show();

    }
    public void Tab32_dragView(View v)
    {
        if(!tab0) {
            tab0 = true;
            tab32_Fragment fragment = new tab32_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }

    public void Tab35_dragView(View v)
    {
        if(!tab0) {
            tab0 = true;
            tab35_Fragment fragment = new tab35_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }
    public void Tab4_dragView(View v)
    {
        if(!tab0) {
            tab0 = true;
            tab4_Fragment fragment = new tab4_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.fagment_tab0, fragment).commit();
        }
    }


}
