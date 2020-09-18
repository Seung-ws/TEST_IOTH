package com.example.needchat.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.needchat.Server.Server_Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class staticData {
    public static ProgressDialog progressDialog=null;

    public static void proceesing_set_msg(AppCompatActivity c)
    {
        progressDialog=new ProgressDialog(c);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }
    public static void processing_msg(String message)
    {
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public static void processing_delmsg()
    {
        progressDialog.setCancelable(true);progressDialog.dismiss();
    }
    public static boolean processing_show(){return progressDialog.isShowing();}
    public static Server_Socket ss=null;
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
}
