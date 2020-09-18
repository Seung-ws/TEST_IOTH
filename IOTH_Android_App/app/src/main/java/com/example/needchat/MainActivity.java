package com.example.needchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity{
    private EditText hostText;
    private EditText portText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hostText=findViewById(R.id.hostText);
        portText=findViewById(R.id.portText);





    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void nextPage1(View v)
    {
        Intent intent=new Intent(this,menuActivity.class);
        startActivity(intent);
    }
    public void nextPage(View v){
        if(hostText.getText().toString().length()>0&&portText.getText().toString().length()>0)
        {
            Intent intent=new Intent(getApplicationContext(),NeedChatForm.class);
            intent.putExtra("host",hostText.getText().toString());
            intent.putExtra("port",portText.getText().toString());
            startActivity(intent);
        }
    }
}
