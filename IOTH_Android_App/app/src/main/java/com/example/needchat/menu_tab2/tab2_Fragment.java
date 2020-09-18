package com.example.needchat.menu_tab2;

import android.graphics.Color;
import android.icu.text.AlphabeticIndex;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class tab2_Fragment extends Fragment {

    JSONObject total=null;
    TabItem tab2_tempbtn=null;
    TabItem tab2_humbtn=null;
    TabItem tab2_gasbtn=null;
    LinearLayout tab2_panel=null;
    TabLayout tab2_chart=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab2_thg,container,false);
        v.startAnimation(anim);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Thread tab2fdata=new Thread(new tab2fdata());
        tab2fdata.start();
        try {
            tab2fdata.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        tab2_tempbtn=getView().findViewById(R.id.tab2_tempbtn);
        tab2_humbtn=getView().findViewById(R.id.tab2_humbtn);
        tab2_gasbtn=getView().findViewById(R.id.tab2_gasbtn);
        tab2_chart=getView().findViewById(R.id.tab2_chart);
        tab2_panel=getView().findViewById(R.id.tab2_panel);
        tab2_panel.removeAllViews();
        tab2_panel.addView(buildText("온도"));
        try {
            tab2_panel.addView(buildChat("온도",total.getJSONArray("#tab2f")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tab2_chart.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String name=tab.getText().toString();
               switch (name)
               {

                   case "온도":
                   {
                       try {
                           tab2_panel.removeAllViews();
                           tab2_panel.addView(buildText(name));
                           tab2_panel.addView(buildChat(name,total.getJSONArray("#tab2f")));
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       break;
                   }
                   case "습도":
                   {
                       try {
                           tab2_panel.removeAllViews();
                           tab2_panel.addView(buildText(name));
                           tab2_panel.addView(buildChat(name,total.getJSONArray("#tab2f")));
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       break;
                   }
                   case "일산화탄소":
                   {
                       try {
                           tab2_panel.removeAllViews();
                           tab2_panel.addView(buildText(name));
                           tab2_panel.addView(buildChat(name,total.getJSONArray("#tab2f")));
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       break;
                   }
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }
    public LinearLayout buildText(String name){
        LinearLayout ll=new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        ll.setPadding(10,10,10,10);
        ll.setLayoutParams(lp);
        TextView tv=new TextView(getActivity());

        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20f);

        float size=0;
        float sum=0;
        try {
            size=total.getJSONArray("#tab2f").length();

        for(int i=0;i<size;i++)
        {
            if(name.equals("온도"))sum+=Integer.parseInt(((JSONObject)total.getJSONArray("#tab2f").get(i)).get("Temp").toString());
            else if(name.equals("습도"))sum+=Integer.parseInt(((JSONObject)total.getJSONArray("#tab2f").get(i)).get("Hum").toString());
            else if(name.equals("일산화탄소"))sum+=Integer.parseInt(((JSONObject)total.getJSONArray("#tab2f").get(i)).get("Gas").toString());
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView tv1=new TextView(getActivity());
        TextView tv2=new TextView(getActivity());

        if(name.equals("온도")){
            tv.setText("실내 "+name+" 평균");
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tv1.setText(""+Math.round(sum/size)+"℃");
            tv2.setText("를 유지하고 있습니다.");

        }
        else if(name.equals("습도")){
            tv.setText("실내 "+name+" 평균");
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tv1.setText(""+Math.round(sum/size)+"%");
            tv2.setText("를 유지하고 있습니다.");
        }
        else if(name.equals("일산화탄소"))
        {
            tv.setText("일산화탄소(CO) 농도 평균");
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tv1.setText(""+Math.round(sum/size)+"ppm");
            tv2.setText("을 유지하고 있습니다.");
        };
        tv1.setTextColor(Color.BLUE);
        tv1.setTextSize(40f);


        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(20f);
        tv.setGravity(Gravity.CENTER);
        tv1.setGravity(Gravity.CENTER);
        tv2.setGravity(Gravity.CENTER);
        ll.addView(tv);
        ll.addView(tv1);
        ll.addView(tv2);
        return ll;
    }
    public LineChart buildChat(final String attritem, final JSONArray array){
        final LineChart lineChart=new LineChart(getActivity());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);

        lineChart.setLayoutParams(lp);


        final ArrayList<Entry> entries = new ArrayList<>();

        try{
            int size=array.length();
            for(int i=0;i<size;i++)
            {
                JSONObject object=array.getJSONObject(i);


                float data=0;
                if(attritem.equals("온도"))data=Float.parseFloat(object.getString("Temp"));
                else if(attritem.equals("습도"))data=Float.parseFloat(object.getString("Hum"));
                else if(attritem.equals("일산화탄소"))data=Float.parseFloat(object.getString("Gas"));

                Entry e=new Entry(i,data);

                entries.add(e);


            }



        }catch (Exception e)
        {
            System.out.println("chardata error::"+e);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LineDataSet dataset = new LineDataSet(entries,"");
                dataset.setValueTextSize(10f);
                XAxis xAxis = lineChart.getXAxis();




                IAxisValueFormatter iavf=new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {

                        try {
                            return array.getJSONObject((int)value).get("time").toString();
                        } catch (JSONException e) {
                            return null;
                        }
                    }
                };
               xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                xAxis.setValueFormatter(iavf);
                xAxis.setTextSize(5f);
                YAxis lyAxis=lineChart.getAxisLeft();

                if(attritem.equals("온도"))  lyAxis.setAxisMaximum(50);
                else if(attritem.equals("습도"))  lyAxis.setAxisMaximum(100);
                else if(attritem.equals("일산화탄소"))  lyAxis.setAxisMaximum(150);

                lyAxis.setAxisMinimum(-30);
                xAxis.setLabelCount(0);
                xAxis.setLabelCount(array.length(),true);
                xAxis.setDrawLabels(true);
                YAxis yRAxis = lineChart.getAxisRight();
                yRAxis.setDrawLabels(false);
                yRAxis.setDrawAxisLine(false);
                yRAxis.setDrawGridLines(false);
                LineData data = new LineData(dataset);

                dataset.setColors(ColorTemplate.COLORFUL_COLORS); //

        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

                lineChart.setData(data);
                lineChart.setDescription(null);
                lineChart.animateY(1000);
            }
        });

        return lineChart;
    }
    class tab2fdata implements Runnable{

        @Override
        public void run() {
            long time =System.currentTimeMillis();

            while(!((System.currentTimeMillis()-time)>10000))
            {
                if(staticData.ss!=null&&staticData.ss.con&&staticData.ss.acc)
                {
                    staticData.ss.onSend("#!sys:GetTHG");
                    final String r=staticData.ss.popMsg("#tab2f");
                    if(r!=null)
                    {
                        try {
                            total=new JSONObject(r);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //JSON
                }
            }//
            //실패구간
        }
    }

}
