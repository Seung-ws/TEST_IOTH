package com.example.needchat.menu_tab1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Math.round;

public class tab1_menu_Fragment extends Fragment {
    private LocationManager lm = null;

    private TextView tab1_address = null;
    private TextView tab1_sky = null;
    private TextView tab1_temp1 = null;
    private TextView tab1_wind_ms = null;
    private TextView tab1_hum = null;
    private TextView tab1_cloud = null;
    private ImageView tab1_sky_icon = null;
    private FrameLayout tab1m_main_panel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v = inflater.inflate(R.layout.fragment_tab1_menu, container, false);
        v.startAnimation(anim);
        v.setTranslationX(-5000);
        v.animate().translationX(0).setDuration(800);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab1_address = getView().findViewById(R.id.gps_address);
        tab1_sky = getView().findViewById(R.id.tab1_sky);
        tab1_temp1 = getView().findViewById(R.id.tab1_temp1);
        tab1_wind_ms = getView().findViewById(R.id.tab1_wind_ms);
        tab1_cloud = getView().findViewById(R.id.tab1_cloud);
        tab1_hum = getView().findViewById(R.id.tab1_hum);
        tab1_sky_icon = getView().findViewById(R.id.tab1_sky_icon);
        tab1m_main_panel = getView().findViewById(R.id.tab1m_main_panel2);
       // if(tab1m_main_panel!=null)tab1m_main_panel.addView(탐색레이아웃());
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (((menuActivity) getActivity()).tab4us.gpsswitch) {
            if (((menuActivity) getActivity()).tab4us.homeweather) {
                String place = 지역명변환(((menuActivity) getActivity()).tab4us.lat, ((menuActivity) getActivity()).tab4us.lng);
                apiPaser(((menuActivity) getActivity()).tab4us.lat, ((menuActivity) getActivity()).tab4us.lng,place);

            } else {
                //무조건 gps재탐색 검사
                new Thread(new tab1data()).start();
            }

        } else {

        }

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

    class tab1data implements Runnable {

        @Override
        public void run() {

            if (((menuActivity) getActivity()).tab4us.gpsswitch) {
                final LocationListener gpsLocationListener = new LocationListener() {
                    @SuppressLint("MissingPermission")
                    public void onLocationChanged(Location location) {
                        탐색종료(this);
                        String provider = location.getProvider();
                        final double longitude = location.getLongitude();
                        final double latitude = location.getLatitude();
                        final double altitude = location.getAltitude();

                       /* tab1_address.setText("위치정보 : " + provider + "\n" +
                                "위도 : " + longitude + "\n" +
                                "경도 : " + latitude + "\n" +
                                "고도  : " + altitude +"\n"+
                                "지오코더 : "+지역명변환(latitude, longitude));*/
                        String place = 지역명변환(latitude, longitude);
                        if (place != null) {

                            apiPaser(latitude, longitude,place);
                        } else {
                            final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    1000,
                                    1,
                                    this);
                           lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                   1000,
                                   1,
                                   this);
                       }


                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };



                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        탐색(gpsLocationListener);
                    }
                });
                System.out.println("종료");

            }
        }
    }

    public void 탐색(LocationListener gpsLocationListener) {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                1,
                gpsLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                1,
                gpsLocationListener);
    }
    public void 탐색종료(LocationListener gpsLocationListener) {

        lm.removeUpdates(gpsLocationListener);
    }
    public String 지역명변환(double lat,double lng){
        Geo_coding geo_coding=new Geo_coding(getActivity());
        return geo_coding.getGeoAddress(lat,lng);
    }
    public Weather datatoJSON(JSONObject jsonObject)
    {
        Weather w=new Weather();
        System.out.println("이거"+jsonObject);
        try {
            JSONObject main=jsonObject.getJSONObject("main");
                w.currtemp =""+round(Float.parseFloat(main.get("temp").toString())-273.15);
                w.currhum =""+main.get("humidity").toString();
                w.lowtemp =""+round(Float.parseFloat(main.get("temp_min").toString())-273.15);
                w.hightemp =""+round(Float.parseFloat(main.get("temp_max").toString())-273.15);
            JSONArray weather=jsonObject.getJSONArray("weather");
                w.weather =((JSONObject)weather.get(0)).getString("main");
                w.weatherdiscription =((JSONObject)weather.get(0)).getString("description");
            JSONObject wind=jsonObject.getJSONObject("wind");
                w.windr =wind.getString("deg");
                w.windf =wind.getString("speed");
            JSONObject sys=jsonObject.getJSONObject("sys");
                w.country =sys.getString("country");
                w.city =jsonObject.getString("name");
            JSONObject clouds=jsonObject.getJSONObject("clouds");
                w.cloud =clouds.getString("all");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("현재온도:"+w.currtemp);
        System.out.println("현재습도:"+w.currhum);
        System.out.println("최저온도:"+w.lowtemp);
        System.out.println("최고온도:"+w.hightemp);
        System.out.println("날씨:"+w.weather);
        System.out.println("날씨설명:"+w.weatherdiscription);
        System.out.println("풍향:"+w.windr);
        System.out.println("풍속:"+w.windf);
        System.out.println("나라:"+w.country);
        System.out.println("도시이름:"+w.city);
        System.out.println("구름:"+w.cloud);
        return w;
    }
    public void apiPaser(final double latitude,final double longitude,final String e)
    {
        Runnable r=new Runnable() {
            @Override
            public void run() {
                WebParser wp=new WebParser(latitude, longitude);

                try {
                    final Weather w= datatoJSON(new JSONObject(wp.weather_parse()));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String[] str = e.split(" ");
                            tab1_address.setText(str[1] + " " + str[2] + " " + str[3]);
                            ((menuActivity) getActivity()).tab4us.lat = latitude;
                            ((menuActivity) getActivity()).tab4us.lng = longitude;
                            tab1_sky.setText(w.weatherdiscription);
                            tab1_temp1.setText(w.currtemp +"℃");
                            TextView t=getActivity().findViewById(R.id.tab1_lowtemp);
                            t.setText(w.lowtemp +"℃");
                            TextView t1=getActivity().findViewById(R.id.tab1_hightemp);
                            t1.setText(w.hightemp +"℃");
                            tab1_wind_ms.setText(w.windf +"㎧");
                            tab1_hum.setText(w.currhum +"%");
                            tab1_cloud.setText(w.cloud +"%");
                            tab1_sky_icon.setImageBitmap(w.geticon(w.weatherdiscription, getActivity()));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        };
        Thread th=new Thread(r);
        th.start();
    }



}

