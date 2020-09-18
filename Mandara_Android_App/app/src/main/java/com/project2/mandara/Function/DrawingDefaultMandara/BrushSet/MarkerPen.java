package com.project2.mandara.Function.DrawingDefaultMandara.BrushSet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.MotionEvent;

import com.project2.mandara.Function.DrawingDefaultMandara.Modules.drawRender;
import com.project2.mandara.R;



public class MarkerPen{
    Paint p=new Paint();
    Path path=new Path();
    Point oldPoint=null;
    int mX=0;
    int mY=0;

    boolean ar=false;




    public void run(Bitmap Main, Bitmap Mirr, MotionEvent event,int MandaraPenType, int Color, float Size, float Between_w, float Between_h){
        mX=(int)(event.getX()/Between_w);
        mY=(int)(event.getY()/Between_h);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                oldPoint=new Point(mX,mY);

                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                if(Math.abs(oldPoint.x-mX)>4||Math.abs(oldPoint.y-mY)>4)
                {
                    ar=true;

                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                oldPoint=null;
                break;
            }

        }


        Paint p = new Paint();
        Path path = new Path();
        if (ar && oldPoint != null) {

            path.moveTo(oldPoint.x, oldPoint.y);
            path.lineTo(mX, mY);


            p.setAntiAlias(false);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeJoin(Paint.Join.ROUND);
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(Size);
            p.setColorFilter(null);
            p.setColor(Color);

            Canvas c = new Canvas(Main);
            Canvas mc = new Canvas(Mirr);

            switch (MandaraPenType) {
                case R.id.type_Pen: {

                    c.drawPath(path, p);
                    mc.drawPath(path, p);
                    oldPoint = new Point(mX, mY);

                    break;
                }

            }
        }




    }
}
