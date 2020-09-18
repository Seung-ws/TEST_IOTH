package com.project2.mandara.Mandara.MainForm;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.Data.OpenSave.Server_MandaraImageOffer;
import com.project2.mandara.Data.StaticData;

import com.project2.mandara.Function.DefaulMandara.MandaraDrawingView;
import com.project2.mandara.R;

import org.json.JSONObject;

import java.util.Stack;


public class MandaraSelectingVIew extends AppCompatActivity {
    private FrameLayout bg;

    private LinearLayout card_panel;
    private String CommandType=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandara_selectingview);
        bg=findViewById(R.id.Selecting_bg);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.activity_start);
        bg.startAnimation(anim);
        card_panel=findViewById(R.id.carditem_bg);
        CommandType=getIntent().getStringExtra("CommandType");



    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(card_panel.getChildCount()==0)
        {
            StaticData.process_setmsg(this);
            card_panel.removeAllViews();
            StaticData.process_showmsg("데이터를 불러오는중 입니다..");
            card_builder(card_panel);
            StaticData.process_delmsg();
        }

    }

    private void card_builder(final LinearLayout returnLayer){
        final Server_MandaraImageOffer smio=new Server_MandaraImageOffer();

        Runnable r=new Runnable() {
            @Override
            public void run() {
             ////////   Stack<JSONObject> stack=smio.ImageOffer();
                try {
                    JSONObject obj = null;
                  //////  while((obj=stack.pop())!=null){
                        returnLayer.addView(btn_builder(obj.getString("Num"),obj.getString("Name"), smio.AddressToBitmap(obj.getString("Address"))));

                   /// }
                }catch(Exception e)
                {
                    System.out.println("MandaraSelectingView : 제작하기, 만다라 도안 이미지 받고 버튼제작 오류="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();

        while(th.isAlive()){}
    }

    private CardView btn_builder(final String No, final String name, final Bitmap b){
        CardView cv=new CardView(this);
        LinearLayout ll=new LinearLayout(this);
        ImageView iv=new ImageView(this);
        TextView tv=new TextView(this);

        //텍스트
        tv.setText(name);
        tv.setTextSize(20);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        //이미지
        iv.setImageBitmap(b);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        cv.setPadding(20,20,20,20);
        //리니어
        ll.addView(iv);
        ll.addView(tv);

        //카드뷰
        CardView.LayoutParams clp=new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.setMargins(10,10,10,10);
        cv.setLayoutParams(clp);
        cv.setBackgroundResource(R.drawable.button_style);
        cv.addView(ll);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.process_showmsg("새 페이지 작성중..");
                if(StaticData.처리중문구_여부()&&CommandType.equals("MandaraDefault"))
                {
                    Intent intent=new Intent(getApplicationContext(), MandaraDrawingView.class);
                    MandaraFileInfo mfi=new MandaraFileInfo();
                    mfi.setArtName(name);
                    mfi.setCommandType(CommandType);
                    mfi.setArtNumber(No);
                    mfi.setMandaraName(name);
                    intent.putExtra("UserData",mfi);
                    StaticData.process_delmsg();
                    finish();
                    startActivity(intent);
                }else if(StaticData.처리중문구_여부()&&CommandType.equals("MandaraTest"))
                {
                    Intent intent=new Intent(getApplicationContext(), MandaraDrawingView.class);
                    MandaraFileInfo mfi=new MandaraFileInfo();
                    mfi.setArtName(name);
                    mfi.setCommandType(CommandType);
                    mfi.setArtNumber(No);
                    mfi.setMandaraName(name);


                    intent.putExtra("UserData",mfi);
                    StaticData.process_delmsg();
                    finish();
                    startActivity(intent);
                }
            }
        });
        return cv;
    }


}
