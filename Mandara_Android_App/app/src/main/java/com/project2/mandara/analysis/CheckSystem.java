package com.project2.mandara.analysis;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.github.mikephil.charting.data.PieEntry;
import com.project2.mandara.Data.StaticColor.StaticColor;
import com.project2.mandara.Data.FileInfo.MandaraFileInfo;

import java.util.ArrayList;
import java.util.Stack;

public class CheckSystem {
    int Red=0;
    int Orange=0;
    int Yellow=0;
    int Green=0;
    int Blue=0;
    int Navy=0;
    int Puple=0;
    int Brown=0;
    int DarkSeeGreen=0;
    int Pink=0;
    int Black=0;
    int White=0;
    Stack<Integer> ColorCount=new Stack<Integer>();
    MandaraFileInfo mfi;
    Bitmap currb;
    Bitmap orib;
    Bitmap mirr;

    public CheckSystem(MandaraFileInfo mfi,Bitmap a, Bitmap b,Bitmap c) {
        this.mfi=mfi;
        currb=a;//png파일?
        orib=Bitmap.createScaledBitmap(b,600,600,true);//오리지널파일
        mirr=c;
    }
    public Bitmap getCurrentBitmap(){

        return currb.copy(Bitmap.Config.ARGB_8888,true);
    }
    public Bitmap getOriginBitmap(){
        return orib.copy(Bitmap.Config.ARGB_8888,true);
    }

    public String getArtName(){
        return "작품명 : "+mfi.getArtName();
    }
    public String getMandaraName(){
        return "만다라명 : "+mfi.getMandaraName();
    }
    public String getMandaraType(){
        return "만다라 종류 : "+mfi.getMandaraType();
    }
    public String getLossRate(){
        Stack<Point> nameStack=new Stack<Point>();

        float cb=0;
        for(int x=0;x<orib.getWidth();x++)
        {
            for(int y=0;y<orib.getHeight();y++)
            {
                //if(((OriginBitmap.getPixel(x,y)>>24)&0xFF)==0){
                if(orib.getPixel(x,y)==0){
                    nameStack.push(new Point(x,y));

                };
            }
        }
        int Size=nameStack.size();
        System.out.println("갯수"+Size);
        int Loss=0;
        while(!nameStack.isEmpty())
        {
            Point p=nameStack.pop();
            if(currb.getPixel(p.x,p.y)!=orib.getPixel(p.x,p.y))
            {
                Loss++;
            }
        }
        System.out.println("갯수"+Loss);
        System.out.println("갯수"+Size);
        return "훼손도 : "+Math.round(((float)(Loss)/(float)Size)*100)+"%";
    }
    public ArrayList<PieEntry> getColors(){
        for(int x=0;x<mirr.getWidth();x++)
        {
            for(int y=0;y<mirr.getHeight();y++)
            {
                boolean finding=true;
                int Cursor=0;
                int curRGB=mirr.getPixel(x,y);
                if(curRGB!=0&&curRGB!=0xFFFFFFFE)
                {
                    while(finding)
                    {

                        if(StaticColor.Red[Cursor]==curRGB){Red+=1;finding=false;};
                        if(StaticColor.Orange[Cursor]==curRGB){Orange+=1;finding=false;};
                        if(StaticColor.Yellow[Cursor]==curRGB){Yellow+=1;finding=false;};
                        if(StaticColor.Green[Cursor]==curRGB){Green+=1;finding=false;};
                        if(StaticColor.Blue[Cursor]==curRGB){Blue+=1;finding=false;};
                        if(StaticColor.Navy[Cursor]==curRGB){Navy+=1;finding=false;};
                        if(StaticColor.Puple[Cursor]==curRGB){Puple+=1;finding=false;};
                        if(StaticColor.Brown[Cursor]==curRGB){Brown+=1;finding=false;};
                        if(StaticColor.DarkSeeGreen[Cursor]==curRGB){DarkSeeGreen+=1;finding=false;};
                        if(StaticColor.Pink[Cursor]==curRGB){Pink+=1;finding=false;};
                        if(StaticColor.Black[Cursor]==curRGB){Black+=1;finding=false;};
                        if(0xFFFFFFFF==curRGB){White+=1;finding=false;};
                        if((Cursor)<9)
                        {
                            Cursor++;
                        }
                        else
                        {
                            finding=false;
                        }


                    }

                }

            }
        }




        return resultdata();
    }
    private ArrayList<PieEntry> resultdata(){
        ArrayList<PieEntry> arr=new ArrayList<PieEntry>();
        String []str=new String[]{"RED","ORAGE","YELLOW","GREEN","BLUE","NAVY","PUPLE","BROWN","DARKSEAGREEN","PINK","BLACK","WHITE"};
        try{

            float allvalue=(Red+Orange+Yellow+Green+Blue+Navy+Puple+Brown+DarkSeeGreen+Pink+Black+White);
            for(int i=0;i<str.length;i++)
            {
                String name=null;
                float namevalue=0;
                if(str[i]==str[0]){name=str[0];namevalue=Red;};
                if(str[i]==str[1]){name=str[1];namevalue=Orange;};
                if(str[i]==str[2]){name=str[2];namevalue=Yellow;};
                if(str[i]==str[3]){name=str[3];namevalue=Green;};
                if(str[i]==str[4]){name=str[4];namevalue=Blue;};
                if(str[i]==str[5]){name=str[5];namevalue=Navy;};
                if(str[i]==str[6]){name=str[6];namevalue=Puple;};
                if(str[i]==str[7]){name=str[7];namevalue=Brown;};
                if(str[i]==str[8]){name=str[8];namevalue=DarkSeeGreen;};
                if(str[i]==str[9]){name=str[9];namevalue=Pink;};
                if(str[i]==str[10]){name=str[10];namevalue=Black;};
                if(str[i]==str[11]){name=str[11];namevalue=White;};

                if(namevalue!=0) {
                    PieEntry  pe= new PieEntry(((namevalue / allvalue) * 100), name);
                    arr.add(pe);
                }

            }






        }catch(Exception e)
        {
            System.out.println("CheckSystem getColor Error :"+e);
        }
        return arr;
    }


}
