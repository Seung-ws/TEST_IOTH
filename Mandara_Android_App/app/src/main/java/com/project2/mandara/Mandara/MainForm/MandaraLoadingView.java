package com.project2.mandara.Mandara.MainForm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.project2.mandara.Data.FireBaseModule;
import com.project2.mandara.Data.OpenSave.Server_MandaraImageOffer;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.R;

import org.json.JSONObject;

import java.util.List;
import java.util.Queue;

public class MandaraLoadingView extends AppCompatActivity {
    FrameLayout bg;
    private String CommandType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_mandara_loadingview);
        /*
        전달방식시작
        */

        CommandType=getIntent().getStringExtra("CommandType");

        /*
        전달방식끝
        */
        bg=findViewById(R.id.Loading_bg);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.activity_start);
        bg.startAnimation(anim);
        run();
    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }


    @Override
    protected void onStart() {

        super.onStart();

    }
    public void run(){

        Intent intent=new Intent(this,MandaraSelectingVIew.class);
        finish();
        startActivity(intent);
    }




}
