package com.project2.mandara.Function.DrawingDefaultMandara.BrushSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;

import com.firebase.ui.auth.data.model.Resource;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.Function.DrawingDefaultMandara.Modules.drawRender;
import com.project2.mandara.R;


public class Crayon {

    Paint p=new Paint();
    Path path=new Path();
    Point oldPoint=null;
    int mX=0;
    int mY=0;
    drawRender render=new drawRender();

    boolean ar=false;
    private Context c=null;

    public Crayon(Context r)
    {
        this.c=r;
    }


    public void run(Bitmap Main,Bitmap Mirr,Bitmap RenderBrush,MotionEvent event, int Color, float Size,float Between_w,float Between_h){
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


            p.setAlpha(200);
            p.setColorFilter(new PorterDuffColorFilter(Color, PorterDuff.Mode.SRC_ATOP));
            render.drawFromTo(c,RenderBrush, new float[]{oldPoint.x, oldPoint.y}, mX, mY, p);
            oldPoint = new Point(mX, mY);
            p.setAlpha(255);
            mc.drawPath(path, p);

            }
        }




    }

