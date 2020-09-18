package com.project2.mandara.Function.DrawingDefaultMandara.Modules;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.HashSet;
import java.util.Stack;


public class ColorFuller {
    private HashSet<String> hs=new HashSet<String>();
    private Stack<Point> stack=new Stack<Point>();
    private Bitmap bitmap;
    private Thread th=null;
    private boolean th_alive=false;


    public void run(int x, int y, int wishColor,int CurrentColor,Bitmap bitmap){
        this.bitmap=bitmap;
        FirstStep(x,y,wishColor,CurrentColor);

    }
    private void FirstStep(int x, int y, int wishColor,int CurrentColor){

        int nowColor=bitmap.getPixel(x,y);
        //
        if(!th_alive)
        {
            if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
            {
                th_alive=true;
                SearchPixel(x,y,wishColor,CurrentColor);
                SecondStep(wishColor,CurrentColor);
            }else
            {
                th_alive=true;
                change_and_search_Pixel(x,y,0xFFFFFFFF,CurrentColor);
                stack_processing(0xFFFFFFFF,CurrentColor);
            }
        }
    }
    private void SecondStep(final int wishColor,final int CurrentColor)
    {

        Runnable r1=new Runnable() {
            @Override

            public void run() {
                Point p;
                while(!stack.isEmpty())
                {

                    p=stack.pop();
                    SearchPixel(p.x,p.y,wishColor,CurrentColor);


                }
                hs.clear();


            }
        };

            th=new Thread(r1);
            th.start();
    }
    private void SearchPixel(int x, int y, int wishColor,int CurrentColor){

        int nowColor=bitmap.getPixel(x,y);

        if((nowColor==CurrentColor)&&nowColor!=wishColor&&!ZeroArea(nowColor))
        {
            bitmap.setPixel(x,y,wishColor);
            //오른쪽

            if(x+1<bitmap.getWidth())
            {
                nowColor=bitmap.getPixel(x+1,y);
                if((nowColor==CurrentColor)&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x+1)+","+(y)))
                    {
                        hs.add(""+(x+1)+","+(y));
                        stack.add(new Point(x+1,y));
                    }
                }
            }
            //왼쪽
            if(x-1>=0)
            {
                nowColor=bitmap.getPixel(x-1,y);
                if((nowColor==CurrentColor)&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x-1)+","+(y)))
                    {
                        hs.add(""+(x-1)+","+(y));
                        stack.add(new Point(x-1,y));
                    }
                }
            }
            //위
            if(y-1>=0)
            {
                nowColor=bitmap.getPixel(x,y-1);
                if((nowColor==CurrentColor)&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x)+","+(y-1)))
                    {
                        hs.add(""+(x)+","+(y-1));
                        stack.add(new Point(x,y-1));
                    }
                }
            }
            //아래
            if(y+1<bitmap.getHeight())
            {
                nowColor=bitmap.getPixel(x,y+1);
                if((nowColor==CurrentColor)&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x)+","+(y+1)))
                    {
                        hs.add(""+(x)+","+(y+1));
                        stack.add(new Point(x,y+1));
                    }
                }
            }
        }


    }
    private void stack_processing(final int wishColor, final int CurrentColor)
    {

        Runnable r1=new Runnable() {
            @Override

            public void run() {
                Point p;
                while(!stack.isEmpty())
                {

                    p=stack.pop();
                    change_and_search_Pixel(p.x,p.y,wishColor,CurrentColor);


                }
                hs.clear();


            }
        };

        th=new Thread(r1);
        th.start();
    }
    private void change_and_search_Pixel(int x, int y, int wishColor, int CurrentColor){

        int nowColor=bitmap.getPixel(x,y);

        if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
        {
            bitmap.setPixel(x,y,wishColor);
            //오른쪽

            if(x+1<bitmap.getWidth())
            {
                nowColor=bitmap.getPixel(x+1,y);
                if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x+1)+","+(y)))
                    {
                        hs.add(""+(x+1)+","+(y));
                        stack.add(new Point(x+1,y));
                    }
                }
            }
            //왼쪽
            if(x-1>=0)
            {
                nowColor=bitmap.getPixel(x-1,y);
                if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x-1)+","+(y)))
                    {
                        hs.add(""+(x-1)+","+(y));
                        stack.add(new Point(x-1,y));
                    }
                }
            }
            //위
            if(y-1>=0)
            {
                nowColor=bitmap.getPixel(x,y-1);
                if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x)+","+(y-1)))
                    {
                        hs.add(""+(x)+","+(y-1));
                        stack.add(new Point(x,y-1));
                    }
                }
            }
            //아래
            if(y+1<bitmap.getHeight())
            {
                nowColor=bitmap.getPixel(x,y+1);
                if(nowColor==CurrentColor&&nowColor!=wishColor&&!ZeroArea(nowColor))
                {
                    if(!hs.contains(""+(x)+","+(y+1)))
                    {
                        hs.add(""+(x)+","+(y+1));
                        stack.add(new Point(x,y+1));
                    }
                }
            }
        }


    }
    private boolean ZeroArea(int argb)
    {
        //int A=(argb>>24)&0xFF;
        //int R=(argb>>16)&0xFF;
        //int G=(argb>>8)&0xFF;
        //int B=argb&0xFF;
    //    if(R<80&&G<80&&B<80)
        if(argb==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean WhiteArea(int argb){
        //int A=(argb>>24)&0xFF;
        int R=(argb>>16)&0xFF;
        int G=(argb>>8)&0xFF;
        int B=argb&0xFF;
        if(R>200&&G>200&&B>200){
            return true;
        }
        else
        {
            return false;
        }
    }
}
