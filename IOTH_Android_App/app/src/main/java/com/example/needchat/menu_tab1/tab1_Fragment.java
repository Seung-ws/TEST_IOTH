package com.example.needchat.menu_tab1;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.R;
import com.example.needchat.menuActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tab1_Fragment extends Fragment {
    private FrameLayout tab1f_panel=null;
    private  LinearLayout tab1f_data_panel=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab1_weather,container,false);
        v.startAnimation(anim);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab1f_panel=getView().findViewById(R.id.tab1f_panel);
        tab1f_data_panel=getView().findViewById(R.id.tab1f_data_panel);

        ImageView tab1_image_clouds=getView().findViewById(R.id.tab1_image_clouds);
        tab1_image_clouds.setTranslationY(5000);
        //tab1_image_clouds.animate().translationY(-250).setDuration(1000);
        tab1f_panel.addView(탐색레이아웃());
        데이터_불러오기();
    }
    public LinearLayout 탐색레이아웃(){
        LinearLayout ll=new LinearLayout(getActivity());
        ll.setTag("탐색레이아웃");
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(125,0,0,0));
        ll.setLayoutParams(lp);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.VERTICAL);
        ProgressBar pb=new ProgressBar(getActivity());
        ll.addView(pb);
        TextView alert=new TextView(getActivity());
        alert.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        alert.setText("데이터를 불러오는 중입니다.");

        ll.addView(alert);
        return ll;
    }
    public void 데이터_불러오기(){
        final List<JSONObject> list=new ArrayList<>();

        String res=null;
        Runnable r=new Runnable() {
            @Override
            public void run() {
                WebParser wp=new WebParser(((menuActivity) getActivity()).tab4us.lat,((menuActivity) getActivity()).tab4us.lng);
                try {
                    JSONObject totalobject=new JSONObject(wp.nextweather_parse());
                    JSONArray arr=totalobject.getJSONArray("list");

                    int size=arr.length();

                    for(int i=0;i<size;i++)
                    {
                        JSONObject object=arr.getJSONObject(i);

                        String target=object.getString("dt_txt").toString();
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date date=sdf.parse(target);
                        if(System.currentTimeMillis()<(date.getTime()+32400000)){
                            if(target.contains("09:00:00")||target.contains("21:00:00"))
                            {
                                if(target.contains("09:00:00"))
                                {
                                    object.put("dt_txt",sdf.format(new Date((date.getTime()+32400000))));
                                    list.add(object);
                                }else if(target.contains("21:00:00"))
                                {
                                    object.put("dt_txt",sdf.format(new Date((date.getTime()+32400000))));
                                    list.add(object);
                                }
                            }
                        }
                    }

                    int listSize=list.size();
                    for(int i=0;i<listSize;i++)
                    {
                        final nextWeather nw=new nextWeather();
                        nw.setNext(list.get(i));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tab1f_data_panel.addView(addWeatherCard(nw));
                            }
                        });
                    }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tab1f_panel.removeViewAt(1);
                    }
                });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
    }

    public FrameLayout addWeatherCard(nextWeather nw){
        FrameLayout panel=new FrameLayout(getActivity());

        {
            TextView title=new TextView(getActivity());
            title.setText(nw.d_date);
            title.setTextColor(Color.WHITE);
            panel.addView(title);

        }
        panel.setPadding(20,20,20,20);
        panel.setBackgroundResource(R.drawable.button_style2);
        FrameLayout.LayoutParams flp=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flp.setMargins(10,10,10,10);
        panel.setLayoutParams(flp);
            LinearLayout lv=new LinearLayout(getActivity());
            {
                LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lv.setOrientation(LinearLayout.VERTICAL);
                lv.setLayoutParams(lp1);

                lv.setGravity(Gravity.CENTER);
                panel.addView(lv);
                LinearLayout MainTab1=new LinearLayout(getActivity());
                {
                    lv.addView(MainTab1);
                    LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    MainTab1.setOrientation(LinearLayout.HORIZONTAL);
                    MainTab1.setLayoutParams(lp2);
                    MainTab1.setPadding(10,10,10,10);
                    ImageView iv=new ImageView(getActivity());{
                        iv.setImageBitmap(nw.geticon(nw.weatherdescription,getActivity()));
                        MainTab1.addView(iv);
                    }
                    LinearLayout subTab1=new LinearLayout(getActivity());{
                        LinearLayout.LayoutParams lp3=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        subTab1.setLayoutParams(lp3);
                        MainTab1.addView(subTab1);
                        subTab1.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams subtvlp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView subtv1=new TextView(getActivity());
                        {
                            subtv1.setLayoutParams(subtvlp);
                            subtv1.setGravity(Gravity.RIGHT + Gravity.BOTTOM);
                            subtv1.setText(nw.currtemp);
                            subtv1.setTextColor(Color.BLACK);
                            subtv1.setTextSize(20);
                            subTab1.addView(subtv1);
                        }
                     /*   LinearLayout temptemp=new LinearLayout(getActivity());
                        {
                            temptemp.setOrientation(LinearLayout.HORIZONTAL);
                            temptemp.setGravity(Gravity.RIGHT);
                            subTab1.addView(temptemp);
                                TextView subtv2=new TextView(getActivity());{
                            //    subtv2.setLayoutParams(subtvlp);
                                subtv2.setGravity(Gravity.RIGHT);
                                subtv2.setText(nw.최저온도);
                                subtv2.setTextColor(Color.BLUE);
                                temptemp.addView(subtv2);
                            }
                            TextView subtv3=new TextView(getActivity());{
                          //  subtv3.setLayoutParams(subtvlp);
                            subtv3.setGravity(Gravity.RIGHT);
                            subtv3.setText(" / ");
                            temptemp.addView(subtv3);
                            }
                            TextView subtv4=new TextView(getActivity());{
                         //   subtv4.setLayoutParams(subtvlp);
                            subtv4.setGravity(Gravity.RIGHT);
                            subtv4.setText(nw.최고온도);
                            subtv4.setTextColor(Color.RED);
                            temptemp.addView(subtv4);
                        }

                        }*/


                        TextView subtv3=new TextView(getActivity());{
                            subtv3.setLayoutParams(subtvlp);
                            subtv3.setGravity(Gravity.RIGHT);
                            subtv3.setText(nw.weatherdescription);
                            subTab1.addView(subtv3);
                        }

                        TextView subtv4=new TextView(getActivity());
                        {
                            subtv4.setLayoutParams(subtvlp);
                            subtv4.setGravity(Gravity.RIGHT);
                            String [] res=new Geo_coding(getActivity()).getGeoAddress(((menuActivity)getActivity()).tab4us.lat,((menuActivity)getActivity()).tab4us.lng).split(" ");

                            subtv4.setText(res[1]+" "+res[2]+" "+res[3]);
                            subTab1.addView(subtv4);
                        }





                    }

                }//MainTab1
                LinearLayout MainTab2=new LinearLayout(getActivity());{
                lv.addView(MainTab2);
                MainTab2.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                MainTab2.setLayoutParams(lp);
                MainTab2.setPadding(10,10,10,10);
                MainTab2.setBackgroundResource(R.drawable.testw);
                LinearLayout sub2tab1 =new LinearLayout(getActivity());{
                    MainTab2.addView(sub2tab1);
                    sub2tab1.setOrientation(LinearLayout.VERTICAL);
                    sub2tab1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
                    sub2tab1.setPadding(10,10,10,10);
                    ImageView iv=new ImageView(getActivity());
                    {
                        Drawable d=getResources().getDrawable(R.drawable.ic_wistrongwind);
                        d.mutate();
                        iv.setImageDrawable(d);
                        sub2tab1.addView(iv);
                    }
                    TextView tv=new TextView(getActivity());
                    {
                        tv.setText(nw.windpow +"m/s");
                        tv.setTextColor(Color.BLACK);
                        tv.setGravity(Gravity.CENTER);
                        sub2tab1.addView(tv);
                    }

                }
                LinearLayout sub2tab2 =new LinearLayout(getActivity());{
                    MainTab2.addView(sub2tab2);
                    sub2tab2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
                    sub2tab2.setOrientation(LinearLayout.VERTICAL);
                    sub2tab2.setPadding(10,10,10,10);
                    ImageView iv=new ImageView(getActivity());
                    {
                        Drawable d=getResources().getDrawable(R.drawable.ic_wi_raindrop);
                        d.mutate();
                        iv.setImageDrawable(d);
                        sub2tab2.addView(iv);
                    }
                    TextView tv=new TextView(getActivity());
                    {
                        tv.setText(nw.currhum +"%");
                        tv.setTextColor(Color.BLACK);
                        tv.setGravity(Gravity.CENTER);
                        sub2tab2.addView(tv);
                    }

                }
                LinearLayout sub2tab3 =new LinearLayout(getActivity());{
                    MainTab2.addView(sub2tab3);
                    sub2tab3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f));
                    sub2tab3.setOrientation(LinearLayout.VERTICAL);
                    sub2tab3.setPadding(10,10,10,10);
                    ImageView iv=new ImageView(getActivity());
                    {
                        Drawable d=getResources().getDrawable(R.drawable.ic_wi_cloudy);
                        d.mutate();
                        iv.setImageDrawable(d);
                        sub2tab3.addView(iv);
                    }
                    TextView tv=new TextView(getActivity());
                    {
                        tv.setText(nw.cloud +"%");
                        tv.setTextColor(Color.BLACK);
                        tv.setGravity(Gravity.CENTER);
                        sub2tab3.addView(tv);
                    }
                }
            }//MainTab2
            }






















        return  panel;
    }


}
