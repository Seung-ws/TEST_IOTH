package com.example.needchat.menu_tab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.needchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class nextWeather {
    String currtemp =null;
    String currhum =null;
    String lowtemp =null;
    String hightemp =null;
    String weather =null;
    String weatherdescription =null;
    String windr =null;
    String windpow =null;
    String country =null;
    String c_city =null;
    String d_date =null;
    String cloud =null;
    public void setNext(JSONObject jsonObject)
    {
        try {
            JSONObject object=jsonObject.getJSONObject("main");
            currtemp =""+Math.round(Float.parseFloat(object.getString("temp")) - 273.15)+"℃";
            lowtemp =""+Math.round(Float.parseFloat(object.getString("temp_min")) - 273.15)+"℃";
            hightemp =""+Math.round(Float.parseFloat(object.getString("temp_max")) - 273.15)+"℃";
            currhum =object.getString("humidity");
            JSONArray arr=jsonObject.getJSONArray("weather");
            JSONObject weather=arr.getJSONObject(0);
            this.weather =weather.getString("main");
            weatherdescription =weather.getString("description");
            JSONObject object2=jsonObject.getJSONObject("clouds");
            cloud =object2.getString("all");
            JSONObject object3=jsonObject.getJSONObject("wind");
            windr =object3.getString("deg");
            windpow =object3.getString("speed");
            String str=jsonObject.getString("dt_txt");
            if(str.contains("06"))
            {
                str=str.replace("06:00:00","오전");
            }else if(str.contains("18"))
            {
               str= str.replace("18:00:00","오후");
            }
            d_date =str;

            System.out.println(currtemp);
            System.out.println(lowtemp);
            System.out.println(hightemp);
            System.out.println(currhum);
            System.out.println(this.weather);
            System.out.println(weatherdescription);
            System.out.println(cloud);
            System.out.println(windr);
            System.out.println(windpow);
            System.out.println(d_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Bitmap geticon(String weather, Context context)
    {
        Bitmap b=null;
        switch(weather)
        {
            case "clear sky":
            {
                b= BitmapFactory.decodeResource(context.getResources(), R.drawable.clearsky);

                break;
            }
            case "few clouds":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.fewclouds);

                break;
            }
            case "scattered clouds":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.scatteredclouds);

                break;
            }
            case "broken clouds":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.brokenclouds);

                break;
            }
            case "overcast clouds":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.brokenclouds);

                break;
            }
            case "light intensity shower rain":
            case "heavy intensity shower rain":
            case "ragged shwer rain":
            case "shower rain":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.showerrain);

                break;
            }
            case "light intensity drizzle":
            case "drizzle":
            case "heavy intensity drizzle":
            case "light intensity drizzle rain":
            case "drizzle rain":
            case "heavy intensity drizzle rain":
            case "shower rain and drizzle":
            case "heavy shower rain and drizzle":
            case "shower drizzle":
                //drizzle

            case "light rain":
            case "moderate rain":
            case "heavy intensity rain":
            case "very heavy rain":
            case "extreme rain":
            case "freezing rain":
            case "rain":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.rain);

                break;
            }
            case "light snow":
            case "Snow":
            case "Heavy snow":
            case "Sleet":
            case "Light shower sleet":
            case "Shower sleet":
            case "Light rain and snow":
            case "Rain and snow":
            case "Light shower snow":
            case "Shower snow":
            case "Heavy shower snow":
            case "snow":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.snow);

                break;
            }
            case "thunderstorm with light rain":
            case "thunderstorm with rain":
            case "thunderstorm with heavy rain":
            case "light thunderstorm":
            case "theavy thunderstorm":
            case "tragged thunderstorm":
            case "thunderstorm with light drizzle":
            case "thunderstorm with drizzle":
            case "thunderstorm with heavy drizzle":
            case "thunderstorm":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.thunderstorm);

                break;
            }

            case "mist":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.mist);

                break;
            }
            case "Smoke":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_smoke);

                break;
            }
            case "Haze":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_haze);

                break;
            }
            case "fog":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_fog);

                break;
            }
            case "sand":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_sand);

                break;
            }
            case "dust":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_dust);

                break;
            }
            case "volcanic ash":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_ash);

                break;
            }
            case "squalls":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_squall);

                break;
            }
            case "tornado":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_tornado);

                break;
            }
            case "haze":
            {
                b= BitmapFactory.decodeResource(context.getResources(),R.drawable.weather_haze);

                break;
            }
            default:
            {
                break;
            }

        }
        b=Bitmap.createScaledBitmap(b,320,320,true);
        return b;
    }
}

