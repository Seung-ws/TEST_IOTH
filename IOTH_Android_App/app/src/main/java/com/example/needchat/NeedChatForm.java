package com.example.needchat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.needchat.Server.Server_Socket;
import com.example.needchat.Server.toHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NeedChatForm extends AppCompatActivity {
    Server_Socket ss=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String host = getIntent().getStringExtra("host");
        String port =getIntent().getStringExtra("port");
        ss=new Server_Socket(host,port);
        setContentView(R.layout.activity_needchat);
        ss.connection();



    }


    public void TouchTrue(View v)
    {
        JSONObject totalobject=new JSONObject();
        JSONArray jsonArr=new JSONArray();
        JSONObject object=new JSONObject();
        try {
            object.put("client_name","LED1");
            object.put("sign","tr");
            jsonArr.put(object);
            totalobject.put("#!toMsg",jsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ss.onSend(totalobject.toString());
    }
    public void TouchFalse(View v)
    {
        JSONObject totalobject=new JSONObject();
        JSONArray jsonArr=new JSONArray();
        JSONObject object=new JSONObject();
        try {
            object.put("client_name","LED1");
            object.put("sign","fa");
            jsonArr.put(object);
            totalobject.put("#!toMsg",jsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ss.onSend(totalobject.toString());
    }

    public void UDPtrue(View v)
    {
        new toHttpClient("http://1.246.212.140:8090/Seung/LED1","t").con();
    }
    public void UDPfalse(View v)
    {
        new toHttpClient("http://1.246.212.140:8090/Seung/LED1","f").con();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ss.close();
    }


}
