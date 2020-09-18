package com.project2.mandara.Mandara.Module;

import android.graphics.Matrix;

public class ScaleMatrix {
    private Matrix  m=new Matrix();
    private float BetweenSize_w=0;
    private float BetweenSize_h=0;
    public ScaleMatrix(float Layout_w,float Layout_h,float target_w, float target_h) {
        BetweenSize_h=Layout_h/target_h;
        BetweenSize_w=Layout_w/target_w;
        m.setScale(BetweenSize_w,BetweenSize_h);
        m.setTranslate(0,0);
    }

    public float getBetweenSize_w()
    {
        return BetweenSize_w;
    }
    public float getBetweenSize_h()
    {
        return BetweenSize_h;
    }


    public Matrix getMatrix() {
        return m;
    }
}
