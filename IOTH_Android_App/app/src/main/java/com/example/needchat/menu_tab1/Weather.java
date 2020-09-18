package com.example.needchat.menu_tab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.needchat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.lang.Math.round;

public class Weather {
    String currtemp =null;
    String currhum =null;
    String lowtemp =null;
    String hightemp =null;
    String weather =null;
    String weatherdiscription =null;
    String windr =null;
    String windf =null;
    String country =null;
    String city =null;
    String cloud =null;
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
        if(b!=null)b=Bitmap.createScaledBitmap(b,320,320,true);
        return b;
    }
    public void ConvertJSON(JSONObject jsonObject)
    {
        try {
            JSONObject main = jsonObject.getJSONObject("main");
            this.currtemp = "" + round(Float.parseFloat(main.get("temp").toString()) - 273.15);
            this.currhum = "" + main.get("humidity").toString();
            this.lowtemp = "" + round(Float.parseFloat(main.get("temp_min").toString()) - 273.15);
            this.hightemp = "" + round(Float.parseFloat(main.get("temp_max").toString()) - 273.15);
            JSONArray weather = jsonObject.getJSONArray("weather");
            this.weather = ((JSONObject) weather.get(0)).getString("main");
            this.weatherdiscription = ((JSONObject) weather.get(0)).getString("description");
            JSONObject wind = jsonObject.getJSONObject("wind");
            this.windr = wind.getString("deg");
            this.windf = wind.getString("speed");
            JSONObject sys = jsonObject.getJSONObject("sys");
            this.country = sys.getString("country");
            this.city = jsonObject.getString("name");
            JSONObject clouds = jsonObject.getJSONObject("clouds");
            this.cloud = clouds.getString("all");
        }catch (Exception e)
        {

        }
    }
    public void run(){

    }
}
