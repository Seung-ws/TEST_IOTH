package com.project2.mandara.Mandara.MainForm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.project2.mandara.Mandara.DefaultMandara.getUserMandaraFileActivity;
import com.project2.mandara.R;

public class Load_Mandara_Type_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.activity_load_mandara_type);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.activity_start);
        findViewById(R.id.activity_load_mandara_type_bg).startAnimation(anim);
    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
    public void btn4(View v)
    {
        Intent intent=new Intent(this, MandaraSelectingVIew.class);
        intent.putExtra("CommandType","MandaraTest");
        startActivity(intent);
    }
    public void btn3(View v)
    {
        Intent intent=new Intent(this, getUserMandaraFileActivity.class);
        intent.putExtra("CommandType","MandaraDefault");
        startActivity(intent);
    }
    public void btn2(View v){
        Intent intent=new Intent(this, MandaraSelectingVIew.class);
        intent.putExtra("CommandType","MandaraDefault");
        startActivity(intent);
    }
    public void btn1(View v)
    {
        Intent intent=new Intent(this, getUserMandaraFileActivity.class);
        intent.putExtra("CommandType","MandaraTest");
        startActivity(intent);
    }
    public void btn5(View v)
    {
        Intent intent=new Intent(this, getUserMandaraFileActivity.class);
        intent.putExtra("CommandType","MandaraTest");
        startActivity(intent);
    }
}
