package com.example.needchat.menu_tab35;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;
import com.example.needchat.menuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class tab35_Fragment extends Fragment {
    View v;
    LinearLayout tab35_listboard=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
         v=inflater.inflate(R.layout.fragment_tab35_devicelist,container,false);
        v.startAnimation(anim);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab35_listboard=getView().findViewById(R.id.tab35_listboard);
        tab35_listboard.removeAllViews();
        tab35_listboard.addView(tab35_search_layout());
        getRelayModule();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    private CardView tab35_empty_display(){
        CardView cv=new CardView(getActivity());
        LinearLayout ll=new LinearLayout(getActivity());
       ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(5,5,5,5);
        ImageView iv=new ImageView(getActivity());
        TextView tv=new TextView(getActivity());
        Bitmap b=BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fail);
        Bitmap  bitmp=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.fail),b.getWidth()/2,b.getHeight()/2,false);

        //텍스트
        tv.setText("연결된 장치가 없습니다.");
        tv.setTextSize(20);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        tv.setTextColor(Color.WHITE);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        //이미지
        iv.setImageBitmap(bitmp);
        iv.setScaleType(ImageView.ScaleType.CENTER);

        cv.setPadding(20,20,20,20);
        //리니어
        ll.addView(iv);
        ll.addView(tv);


        //카드뷰
        CardView.LayoutParams clp=new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.setMargins(10,10,10,10);
        cv.setLayoutParams(clp);
        cv.setBackgroundResource(R.drawable.button_style2);
        cv.addView(ll);

        return cv;
    }


    private CardView tab35_btn_builder(final String usage, final String type, final String name){
        CardView cv=new CardView(getActivity());
        LinearLayout ll=new LinearLayout(getActivity());
        ll.setVerticalGravity(LinearLayout.VERTICAL);
        ImageView iv=new ImageView(getActivity());
        TextView tv=new TextView(getActivity());
        Bitmap b=BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab3_device);
        Bitmap  bitmp=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab3_device),b.getWidth()/2,b.getHeight()/2,false);

        //텍스트
        tv.setText("타입:"+type+"\n"+"용도:"+usage+"\n"+"이름:"+name);
        tv.setTextSize(20);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.LEFT);

        //이미지
        iv.setImageBitmap(bitmp);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        cv.setPadding(20,20,20,20);
        //리니어
        ll.addView(iv);
        ll.addView(tv);


        //카드뷰
        CardView.LayoutParams clp=new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.setMargins(10,10,10,10);
        cv.setLayoutParams(clp);
        cv.setBackgroundResource(R.drawable.button_style2);
        cv.addView(ll);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((menuActivity)getActivity()).Tab3_dialog(name);
            }
        });
        return cv;
    }
    public LinearLayout tab35_search_layout(){
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
    public void getRelayModule(){

        Runnable r=new Runnable() {
            @Override
            public void run() {
                long time =System.currentTimeMillis();

                while(!((System.currentTimeMillis()-time)>10000))
                {
                    if(staticData.ss!=null&&staticData.ss.con&&staticData.ss.acc)
                    {
                        staticData.ss.onSend("#!sys:getrelaylist");

                      //  {"#!saveTHG":[{"Temp":"값","Hum":"값","Gas":"LED1"}]}
                        final String r=staticData.ss.popMsg("#tab35f");
                        if(r!=null&&r.contains("#tab35f")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject totalobject = new JSONObject(r);
                                        JSONArray jsonArray = totalobject.getJSONArray("#tab35f");
                                        tab35_listboard.removeAllViews();
                                        if (jsonArray.length() > 0) {
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String usage = object.getString("client_usage");
                                                String type = object.getString("client_type");
                                                String name = object.getString("client_name");
                                                tab35_listboard.addView(tab35_btn_builder(usage, type, name));
                                            }
                                        } else {
                                            tab35_listboard.removeAllViews();
                                            tab35_listboard.addView(tab35_empty_display());
                                        }
                                    } catch (JSONException e) {
                                        System.out.println("JSON화 오류" + e);
                                    }
                                }
                            });
                        }else
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tab35_listboard.removeAllViews();
                                    tab35_listboard.addView(tab35_empty_display());
                                }
                            });
                        }
                        return;
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tab35_listboard.removeAllViews();
                        tab35_listboard.addView(tab35_empty_display());
                    }
                });
            }
        };
        Thread th=new Thread(r);
        th.start();
    }



}

