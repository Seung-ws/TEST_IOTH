package com.example.needchat.menu_tab3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;
import com.example.needchat.menuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class tab3_menu_Fragment extends Fragment {
    private LinearLayout failView=null;
    private LinearLayout itemView=null;
    private LinearLayout prepareView=null;
    private TextView deviceCount=null;
    private Button btn_refresh=null;
    private ProgressBar tab3_progressbar=null;
    private FloatingActionButton btn_refresh2=null;

    private Thread th=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Animation anim= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab3_menu,container,false);
        v.startAnimation(anim);
        v.setTranslationX(+5000);
        v.animate().translationX(0).setDuration(1500);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareView=this.getView().findViewById(R.id.tab3_prepare);
        failView=this.getView().findViewById(R.id.tab3_fail);
        itemView=this.getView().findViewById(R.id.tab3_itembox);
        deviceCount=this.getView().findViewById(R.id.tab3_device);
        btn_refresh=this.getView().findViewById(R.id.tab3_refresh);
        th=new Thread(new cx());
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        btn_refresh2=this.getView().findViewById(R.id.tab3_refresh2);
        btn_refresh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setRotation(0);
                view.animate().rotation(360).setDuration(500);
                start();

            }
        });
        tab3_progressbar=getView().findViewById(R.id.tab3_progressBar);
        tab3_progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        start();

        //Server_Socket ss=new Server_Socket(((menuActivity)getActivity()).tab4us.host,((menuActivity)getActivity()).tab4us.port);


    }



    public void start(){
        if(th!=null&& th.isAlive())
        {
            th.interrupt();
            th=new Thread(new cx());
        }else
        {
            th=new Thread(new cx());

        }
        th.start();
    }
    class cx implements Runnable{

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {
                    itemView.setAlpha(0);
                    failView.setVisibility(View.GONE);
                    btn_refresh2.setVisibility(View.INVISIBLE);
                    prepareView.setVisibility(View.VISIBLE);

                }
            });
            long time =System.currentTimeMillis();

                while(!(System.currentTimeMillis()-time>3000))
                {
                    if(staticData.ss!=null&&staticData.ss.con&&staticData.ss.acc)
                    {
                        try {

                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        staticData.ss.onSend("#!sys:getcount");


                        final String r=staticData.ss.popMsg("#tab3m");
                        System.out.println(r);
                        if(r!=null)
                        {
                            try {
                                JSONObject total=new JSONObject(r);
                                JSONArray jsonArray=total.getJSONArray("#tab3m");
                                final JSONObject object=jsonArray.getJSONObject(0);
                                final String result=object.get("deviceCount").toString();
                                getActivity().runOnUiThread(new Runnable() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void run() {
                                        if(result!=null){

                                            deviceCount.setText(result);
                                            failView.setVisibility(View.GONE);
                                            prepareView.setVisibility(View.GONE);
                                            itemView.setAlpha(1);
                                            btn_refresh2.setVisibility(View.VISIBLE);
                                        }else
                                        {
                                            itemView.setAlpha(0);
                                            prepareView.setVisibility(View.GONE);
                                            failView.setVisibility(View.VISIBLE);
                                            btn_refresh2.setVisibility(View.INVISIBLE);
                                        }

                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {

                                    @SuppressLint("RestrictedApi")
                                    public void run() {

                                            itemView.setAlpha(0);
                                            prepareView.setVisibility(View.GONE);
                                            failView.setVisibility(View.VISIBLE);
                                            btn_refresh2.setVisibility(View.INVISIBLE);


                                    }

                                });
                                return;
                            }
                        }

                        return;
                    }

                }
                //실패
            getActivity().runOnUiThread(new Runnable() {
                @SuppressLint("RestrictedApi")
                @Override
                public void run() {
                    itemView.setAlpha(0);
                    prepareView.setVisibility(View.GONE);
                    failView.setVisibility(View.VISIBLE);
                    btn_refresh2.setVisibility(View.INVISIBLE);

                }
            });

        }
    }



}
