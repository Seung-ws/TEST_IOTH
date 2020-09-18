package com.example.needchat.menu_tab32;

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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class tab32_Fragment extends Fragment {
    FrameLayout tab32f_bg=null;
    FrameLayout tab32_image_panel=null;
    SimpleAdapter adapter=null;
    ListView lv=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab32_webcam,container,false);
        v.startAnimation(anim);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tab32f_bg=getView().findViewById(R.id.tab32f_bg);

        tab32_image_panel=getView().findViewById(R.id.tab32_image_panel);
        tab32f_bg.addView(tab32_search_layout());
        lv=getView().findViewById(R.id.tab32_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String name=adapterView.getAdapter().getItem(i).toString();
            String name2=name.substring(1,name.indexOf("}"));
            Bitmap b=getUserIconImageBitmap(name2);
            if(b!=null)
            {
                tab32_image_panel.removeAllViews();
                ImageView iv=new ImageView(getActivity());
                iv.setImageBitmap(b);
                tab32_image_panel.addView(iv);

            }else
            {
                tab32_image_panel.removeAllViews();
                tab32_image_panel.addView(tab32_empty_layout());
            }
        }
    });
        Thread th=new Thread(new tab32fdata());
        th.start();
    }
    public LinearLayout tab32_search_layout(){
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
    public LinearLayout tab32_empty_layout(){
        LinearLayout ll=new LinearLayout(getActivity());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(125,0,0,0));
        ll.setLayoutParams(lp);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView alert=new TextView(getActivity());
        alert.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        alert.setText("데이터가 없습니다.");

        ll.addView(alert);
        return ll;
    }
    public void setSimpleAdapter( ArrayList<HashMap<String,String>> arr)
    {
        adapter=new SimpleAdapter(getActivity(),arr,android.R.layout.simple_list_item_1,new String[]{"name"},new int[]{android.R.id.text1});
    }
    public Bitmap getUserIconImageBitmap(final String name)
    {
        final Bitmap [] b=new Bitmap[1];
        b[0]=null;
        final  String toServer="serveraddress?"+name;
        Runnable r=new Runnable() {
            @Override
            public void run() {

                try{
                    URL url=new URL(toServer);
                    URLConnection conn=url.openConnection();
                    conn.connect();
                    BufferedInputStream bis=new BufferedInputStream(conn.getInputStream());
                    b[0]= BitmapFactory.decodeStream(bis);
                    bis.close();
                }catch(Exception e)
                {
                    System.out.println("Server_ImageGetter : getUserIconImageBitmap 오류 ="+e);
                }
            }
        };
        Thread th=new Thread(r);
        th.start();
        while(th.isAlive()){}


        return b[0];
    }




    class tab32fdata implements Runnable
    {

        @Override
        public void run() {



            long time =System.currentTimeMillis();

            while(!((System.currentTimeMillis()-time)>10000))
            {
                if(staticData.ss!=null&&staticData.ss.con&&staticData.ss.acc)
                {
                    try {

                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    staticData.ss.onSend("#!sys:getWebCamlist");
                    final String r=staticData.ss.popMsg("#tab32f");
                    if(r!=null)
                    {
                        ArrayList<HashMap<String,String>> arr=new ArrayList<>();
                        try{
                            JSONObject total=new JSONObject(r);
                            JSONArray jsonArray=total.getJSONArray("#tab32f");

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object=jsonArray.getJSONObject(i);
                                HashMap<String,String> hm=new HashMap<>();
                                hm.put("name",object.getString("name"));
                                arr.add(hm);
                            }


                        }catch(Exception e)
                        {
                            System.out.println("tab32_Fragment JSON parsing Error"+e);
                        }
                        setSimpleAdapter(arr);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tab32f_bg.removeViewAt(1);
                                lv.setBackgroundResource(R.drawable.button_style);
                                tab32_image_panel.setBackgroundResource(R.drawable.button_style2);
                                lv.setAdapter(adapter);

                            }
                        });
                    }else//r이 null
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            tab32f_bg.removeAllViews();
                            tab32f_bg.addView(tab32_empty_layout());
                            }
                        });


                    }
                    return;

                }

            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tab32f_bg.removeAllViews();
                    tab32f_bg.addView(tab32_empty_layout());
                }
            });
        }
    }


}
