package com.project2.mandara.analysis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.Data.OpenSave.Server_ImageGetter;
import com.project2.mandara.Data.StaticColor.StaticColor;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.LoginFilter.FireBaseLoginInfo;
import com.project2.mandara.R;

import java.util.ArrayList;
import java.util.List;

public class
CheckResultActivity extends AppCompatActivity {

    private CheckSystem cs=null;
    private MandaraFileInfo mfi=null;
    private Server_ImageGetter sig=null;
    private   List<Integer> list1=null;
    private ArrayList<PieEntry> obj=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkresult);
        //사용색깔을 받기위한 리스트
        list1=new ArrayList<Integer>();
        sig=new Server_ImageGetter();
        mfi=(MandaraFileInfo)getIntent().getSerializableExtra("UserData");
        StaticData.process_setmsg(this);
        StaticData.process_showmsg("처리중..");
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cs= new CheckSystem(mfi
                        ,sig.getUserMainImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())
                        ,sig.getUserOriginImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())
                        ,sig.getUserMirrorImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())
                );
                           }
        };
       Thread th=new Thread(r);
       th.start();
        while(th.isAlive()){}
        Thread th1=new Thread(){
            public void run(){
               obj=cs.getColors();
            }
        };
        th1.start();
        while(th1.isAlive()){}


        resultSet();

        StaticData.process_delmsg();
    }
    private void resultSet(){
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setBackgroundColor(Color.BLACK);

        //타이틀
        TextView ArtName=new TextView(this);
        ArtName.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams ttllp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArtName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ArtName.setLayoutParams(ttllp);
        ArtName.setText(cs.getArtName());
        ArtName.setTextSize(20);

        ((LinearLayout)findViewById(R.id.ResPanel)).addView(ArtName);



        //유저 이미지
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.weight=1;
        ImageView UserImage=new ImageView(this);
        UserImage.setImageBitmap(cs.getCurrentBitmap());
        UserImage.setLayoutParams(llp);

        ll.addView(UserImage);
        //오리지날 이미지

        ImageView OriginImage=new ImageView(this);
        OriginImage.setImageBitmap(cs.getOriginBitmap());
        OriginImage.setLayoutParams(llp);
        ll.addView(OriginImage);



     //   ((LinearLayout)findViewById(R.id.ResPanel)).addView(ll);


        /*TextView MandaraName=new TextView(this);
            MandaraName.setText(cs.getMandaraName());
            ((LinearLayout)findViewById(R.id.ResPanel)).addView(MandaraName);
        TextView MandaraType=new TextView(this);
            MandaraType.setText(cs.getMandaraType());
            ((LinearLayout)findViewById(R.id.ResPanel)).addView(MandaraType);*/

        ((LinearLayout)findViewById(R.id.ResPanel)).addView(SetChart(new PieChart(this),obj));
        ((LinearLayout)findViewById(R.id.ResPanel)).addView(버튼제작(cs.getLossRate(),cs.mirr,0));
        for(int i=0;i<list1.size();i++)
        {
            ((LinearLayout)findViewById(R.id.ResPanel)).addView(버튼제작("색상비율 : "+Math.round(obj.get(i).getValue())+"%",null,list1.get(i)));
        }


    }
    private PieChart SetChart(PieChart p,ArrayList<PieEntry> obj){
        String []str=new String[]{"RED","ORAGE","YELLOW","GREEN","BLUE","NAVY","PUPLE","BROWN","DARKSEAGREEN","PINK","BLACK","WHITE"};
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000);


        p.setLayoutParams(llp);
      //  p.setBackgroundResource(R.drawable.roundbox_button_black);

        p.setUsePercentValues(true);
        p.getDescription().setEnabled(false);
        p.setExtraOffsets(5,10,5,5);
        p.setDragDecelerationFrictionCoef(1f);


        p.setDrawHoleEnabled(true);

        p.setHoleColor(0x32FFFFFF);
        p.setEntryLabelColor(Color.BLACK);
        p.setTransparentCircleColor(Color.WHITE);

        p.setTransparentCircleRadius(100f);


        p.setAlpha(0.9f);
        try{
            PieDataSet pds=new PieDataSet(obj,"색상");
            pds.setSliceSpace(3f);
            pds.setSelectionShift(5f);



            for(int i=0;i<obj.size();i++)
            {
                if(obj.get(i).getLabel()==str[0])list1.add(StaticColor.Red_sub0);
                if(obj.get(i).getLabel()==str[1])list1.add(StaticColor.Orange_sub0);
                if(obj.get(i).getLabel()==str[2])list1.add(StaticColor.Yellow_sub0);
                if(obj.get(i).getLabel()==str[3])list1.add(StaticColor.Green_sub0);
                if(obj.get(i).getLabel()==str[4])list1.add(StaticColor.Blue_sub0);
                if(obj.get(i).getLabel()==str[5])list1.add(StaticColor.Navy_sub0);
                if(obj.get(i).getLabel()==str[6])list1.add(StaticColor.Puple_sub0);
                if(obj.get(i).getLabel()==str[7])list1.add(StaticColor.Brown_sub0);
                if(obj.get(i).getLabel()==str[8])list1.add(StaticColor.DarkSeeGreen_sub0);
                if(obj.get(i).getLabel()==str[9])list1.add(StaticColor.Pink_sub0);
                if(obj.get(i).getLabel()==str[10])list1.add(0xFF000000);
                if(obj.get(i).getLabel()==str[11])list1.add(0xFFFFFFFF);
            }
            pds.setColors(list1);

            PieData data=new PieData(pds);
            data.setValueTextSize(20);
            data.setValueTextColor(Color.BLACK);



            p.setData(data);

        }catch(Exception e)
        {
            System.out.println("CheckResultActivity SetChart ="+e);
        }
       return p;

    }

    private CardView 버튼제작(final String data, final Bitmap b,final int color){
        CardView cv=new CardView(this);
        LinearLayout ll=new LinearLayout(this);
        ImageView iv=new ImageView(this);
        TextView tv=new TextView(this);

        //텍스트
        tv.setText(data);
        tv.setTextSize(20);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);




        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(200, 200);
        iv.setLayoutParams(llp);
        //이미지
        if(b!=null) {
            iv.setImageBitmap(b);
        }else {

            iv.setBackgroundColor(color);

        }
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

            }
        });
        return cv;
    }

}
