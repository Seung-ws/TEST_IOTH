package com.project2.mandara.Function.DrawingDefaultMandara.Modules;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.project2.mandara.Data.StaticData;

public class drawRender {

    public void drawFromTo(Canvas canvas,Bitmap RenderBitmap_Type_Crayon,  float lastpoint[], float x1, float y1, Paint p) {
        Matrix rotateMatrix = new Matrix();
        float xTerm=x1-lastpoint[0]; //비트맵 끊긴 사이의 텀x
        float yTerm=y1-lastpoint[1];//그려진 렌더이미지 사이 텀y
        float dist = (float) Math.sqrt(xTerm * xTerm + yTerm * yTerm);
        //직삼각형의 빗변길이 구하듯 계산 제곱근 계산
        if (dist < 0.1)
            return;
        //다음으로 그려진 이미지가 분할단위보다 작으면 안그립니다.
        float stepInPercentage = 0.1f;
        //빗변 의길이 10%지점마다 그려지게끔 기준 정함
        float i=0;//빗변 위치를 위한
        float dx = x1 - lastpoint[0]; //x거리길이
        float dy = y1 - lastpoint[1];//y거리길이

        int render_Width=(RenderBitmap_Type_Crayon.getWidth()/2);
        int render_Height=(RenderBitmap_Type_Crayon.getHeight()/2);
        //좌측상단을 기준으로 원본 비트맵의 중심점을 계산하기 위해 비트맵 절반 길이를 저장

        int r_rotate=(int)Math.toDegrees(Math.atan2(-xTerm,yTerm))+90;
        //선과 축사이 각도 활용코드 웹 참조
        if(r_rotate!=0)rotateMatrix.postRotate(r_rotate);
        //매트릭스를 이용하여 비트맵에 적용시킬 회전각저용
        p.setAlpha(90); //일반 펜은 무조건 진하게 나오지 않기때문에 겹쳐지는곳만 진해지도록 자연스럽게 하기 위함
        Bitmap b=Bitmap.createBitmap(RenderBitmap_Type_Crayon,0,0,render_Width*2,render_Height*2,rotateMatrix,false);

        for (; i <= 1; i += stepInPercentage) {
            float xCenter = lastpoint[0] + i * dx;
            float yCenter = lastpoint[1] + i * dy;
            //비트맵의 직선길이의 10분의 1로 나눠 다음

            canvas.drawBitmap(b,
                    xCenter - render_Width, yCenter - render_Height, p);
        }
        lastpoint[0] = lastpoint[0] + i * dx;
        lastpoint[1] = lastpoint[1] + i * dy;
        //
    }
    public void drawToEnd(Canvas canvas, float lastDrawnPoint[], float x1, float y1, Paint p)
    {

    }
}
