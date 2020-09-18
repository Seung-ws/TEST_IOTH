package com.project2.mandara.Function.DrawingDefaultMandara.BrushSet;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.project2.mandara.Function.DrawingDefaultMandara.Modules.ColorFuller;



public class Fuller {
    private ColorFuller colofuller=new ColorFuller();
    private int mX=0;
    private int mY=0;


    public void run(Bitmap Main,int col, MotionEvent event, float Between_w, float Between_h){
        mX=(int)(event.getX()/Between_w);
        mY=(int)(event.getY()/Between_h);
        switch(event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                colofuller.run(mX,mY,col,Main.getPixel(mX,mY),Main);
                break;
            }
        }
    }

}
