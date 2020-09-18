package com.example.needchat.menu_tab1;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class Geo_coding {
    Geocoder geocoder=null;
            public Geo_coding(Context c){
                geocoder=new Geocoder(c);
            }
            public String getGeoAddress(double Lat,double Lng){

                try {
                    List<Address> list=geocoder.getFromLocation(Lat,Lng,1);
                    return list.get(0).getAddressLine(0);
                } catch (IOException e) {
                    System.out.println("변환실패");
                    return null;
                }
            }
}
