package com.example.needchat.menu_tab1;

import android.graphics.BitmapFactory;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class WebParser {

    double lat = 0;
    double lng = 0;
    String tmp = "";
    String res = "";

    public WebParser(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String weather_parse() {
        String host = "api";
        tmp = "";
        res = "";
        try {
            URL url = new URL(host);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                while ((tmp = br.readLine()) != null) {
                    res += tmp;
                }
                br.close();
                isr.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
    public String nextweather_parse() {
        String host = "api";
        System.out.println(host);
        tmp = "";
        res = "";
        try {
            URL url = new URL(host);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                while ((tmp = br.readLine()) != null) {
                    res += tmp;
                }
                br.close();
                isr.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
