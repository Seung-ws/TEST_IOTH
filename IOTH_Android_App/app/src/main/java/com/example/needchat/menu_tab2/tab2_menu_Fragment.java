package com.example.needchat.menu_tab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;
import com.example.needchat.menuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class tab2_menu_Fragment extends Fragment {
    LinearLayout tab2_menu_prepare=null;
    TableLayout tab2_menu_panel=null;
    ImageView tab2_tem_image=null;
    TextView tab2_tem_value=null;
    ImageView tab2_hum_image=null;
    TextView tab2_hum_value=null;
    ImageView tab2_gas_image=null;
    TextView tab2_gas_value=null;
    LinearLayout tab2_fail=null;
    Button tab2_refresh=null;
    ProgressBar tab2_progressbar=null;
    int tempValue=0;
    int gasValue=0;
    int humValue=0;
    Thread tab2mdata=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab2_menu,container,false);
        v.startAnimation(anim);
        v.setTranslationX(-5000);
        v.animate().translationX(0).setDuration(1000);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        tab2_menu_prepare=getView().findViewById(R.id.tab2_menu_prepare);
        tab2_menu_panel=getView().findViewById(R.id.tab2_menu_panel);
        tab2_tem_image=getView().findViewById(R.id.tab2_tem_image);
        tab2_tem_value=getView().findViewById(R.id.tab2_tem_value);
        tab2_hum_image=getView().findViewById(R.id.tab2_hum_image);
        tab2_hum_value=getView().findViewById(R.id.tab2_hum_value);
        tab2_gas_image=getView().findViewById(R.id.tab2_gas_image);
        tab2_gas_value=getView().findViewById(R.id.tab2_gas_value);
        tab2_fail=getView().findViewById(R.id.tab2_fail);
        tab2_refresh=getView().findViewById(R.id.tab2_refresh);
        tab2_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        tab2_progressbar=getView().findViewById(R.id.tab2_progressBar);
        tab2_progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        start();









    }



    public void start(){
        if(tab2mdata!=null&&tab2mdata.isAlive())
        {
          tab2mdata.interrupt();
          tab2mdata=new Thread(new tab2mdata());
        }else
        {
            tab2mdata=new Thread(new tab2mdata());
        }
        tab2mdata.start();

    }
    class tab2mdata implements Runnable
    {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tab2_menu_panel.setAlpha(0);
                    tab2_menu_prepare.setVisibility(View.VISIBLE);
                    tab2_fail.setVisibility(View.GONE);
                }
            });


            long time =System.currentTimeMillis();

            while(!((System.currentTimeMillis()-time)>10000))
            {
                if(staticData.ss!=null&&staticData.ss.con&&staticData.ss.acc)
                {
                    try {

                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    staticData.ss.onSend("#!sys:gettemp&gasValue");


                    final String r=staticData.ss.popMsg("#tab2m");
                    if(r!=null)
                    {
                        Bitmap tempiv=null;
                        Bitmap gasiv=null;
                        Bitmap humiv=null;

                        try {

                            JSONObject totalobject=new JSONObject(r);
                            JSONArray jsonArray=totalobject.getJSONArray("#tab2m");
                            JSONObject object=jsonArray.getJSONObject(0);
                            tempValue=Integer.parseInt(object.get("Temp").toString());
                            gasValue=Integer.parseInt(object.get("Gas").toString());
                            humValue=Integer.parseInt(object.get("Hum").toString());
                            if(tempValue>=30)
                            {
                                tempiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_tem_30),200,200,false);
                            }else if(30>tempValue&&tempValue>=15)
                            {
                                tempiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_tem_20),200,200,false);
                            }else if(15>tempValue&&tempValue>=0)
                            {
                                tempiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_tem_10),200,200,false);
                            }else{
                                tempiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_tem_0),200,200,false);
                            }
                            gasiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_gas_0),200,200,false);
                            humiv=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.tab2_hum),200,200,false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tab2_menu_panel.setAlpha(0);
                                    tab2_menu_prepare.setVisibility(View.GONE);
                                    tab2_fail.setVisibility(View.VISIBLE);
                                }
                            });
                            return;

                        }
                        final Bitmap finalTempiv = tempiv;
                        final Bitmap finalGasiv=gasiv;
                        final Bitmap finalHum=humiv;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                    tab2_tem_image.setImageBitmap(finalTempiv);
                                    tab2_tem_value.setText(""+tempValue+"℃");
                                    tab2_gas_image.setImageBitmap(finalGasiv);
                                    tab2_gas_value.setText(""+gasValue+"ppm");
                                    tab2_hum_image.setImageBitmap(finalHum);
                                    tab2_hum_value.setText(""+humValue+"%");
                                    tab2_menu_prepare.setVisibility(View.GONE);
                                    tab2_fail.setVisibility(View.GONE);
                                    tab2_menu_panel.setAlpha(1);
                            }
                        });
                    }else//r이 null
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tab2_menu_panel.setAlpha(0);
                                tab2_menu_prepare.setVisibility(View.GONE);
                                tab2_fail.setVisibility(View.VISIBLE);
                            }
                        });


                    }
                    return;

                }

            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tab2_menu_panel.setAlpha(0);
                    tab2_menu_prepare.setVisibility(View.GONE);
                    tab2_fail.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
