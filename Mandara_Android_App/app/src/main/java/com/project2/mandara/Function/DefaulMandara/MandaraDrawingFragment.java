package com.project2.mandara.Function.DefaulMandara;


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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project2.mandara.Function.DrawingDefaultMandara.BrushSet.Crayon;
import com.project2.mandara.Function.DrawingDefaultMandara.BrushSet.Fuller;
import com.project2.mandara.Function.DrawingDefaultMandara.BrushSet.MarkerPen;
import com.project2.mandara.Function.DrawingDefaultMandara.Modules.drawRender;
import com.project2.mandara.Mandara.Module.ViewController2;
import com.project2.mandara.R;

import static com.project2.mandara.Data.StaticData.ActiveDisplayControl;


import static com.project2.mandara.Data.StaticData.reader;


public class MandaraDrawingFragment extends Fragment {

    public Bitmap MainMandara=null;
    public Bitmap MirrorMandara=null;
    public Bitmap OriginMandara=null;
    private ProgressBar pb;
    public static MandaraDrawingFragment getInstance(int no) {
        MandaraDrawingFragment fragment = new MandaraDrawingFragment();
        //Bundle args = new Bundle();
        //args.putInt(ARG_NO, no);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pb=getActivity().findViewById(R.id.DrawingProgress);

        MandaraCheckBoard mcb=new MandaraCheckBoard(getActivity());

        ViewGroup.LayoutParams pageParam=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mcb.setLayoutParams(pageParam);

        ViewController2 vc = new ViewController2(getActivity());

        vc.addView(mcb);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        vc.setLayoutParams(layoutParams);
     //   vc.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        vc.setMaxZoom(6f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
     //   vc.setMiniMapCaption("CurrentPosition"); //미니 맵 내용
      //  vc.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정/**/

        return vc;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().finish();
        reader.recordRemove();
    }
    private class MandaraCheckBoard extends View
    {
        private Matrix m=new Matrix();
        private float MainSize_w=600;
        private float MainSize_h=600;
        private float Between_w=0;
        private float Between_h=0;
        private Point oldp=null;
        private boolean ar=false;
        private int mX=0;
        private int mY=0;
        MarkerPen mp=new MarkerPen();
        Crayon crayon=new Crayon(getActivity());
        drawRender dr=new drawRender();

        public MandaraCheckBoard(Context context) {
            super(context);
            ((MandaraDrawingView)getActivity()).MainMandara=Bitmap.createScaledBitmap(((MandaraDrawingView)getActivity()).MainMandara,(int)MainSize_w,(int)MainSize_h,false);
            ((MandaraDrawingView)getActivity()).MirrorMandara=Bitmap.createScaledBitmap(((MandaraDrawingView)getActivity()).MirrorMandara,(int)MainSize_w,(int)MainSize_h,false);
            ((MandaraDrawingView)getActivity()).OriginMandara=Bitmap.createScaledBitmap(((MandaraDrawingView)getActivity()).OriginMandara,(int)MainSize_w,(int)MainSize_h,false);

            ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.brush_pencil),
                    (int)((MandaraDrawingView)getActivity()).MandaraColorSize,
                    (int)((MandaraDrawingView)getActivity()).MandaraColorSize,
                    false);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {



                if(!ActiveDisplayControl){
                    if(((MandaraDrawingView)getActivity()).MandaraColor==0)
                    {
                        Toast.makeText(getActivity(),"색상을 선택해 주세요",Toast.LENGTH_LONG).show();
                        return true;
                    }
                    pb.setVisibility(VISIBLE);
                    switch(((MandaraDrawingView)getActivity()).MandaraPenType)
                    {
                        case R.id.type_Pen:
                        {
                            mp.run(((MandaraDrawingView)getActivity()).MainMandara,
                                    ((MandaraDrawingView)getActivity()).MirrorMandara,

                                    event,
                                    ((MandaraDrawingView)getActivity()).MandaraPenType,
                                    ((MandaraDrawingView)getActivity()).MandaraColor,
                                    ((MandaraDrawingView)getActivity()).MandaraColorSize,
                                    Between_w,
                                    Between_h);
                            break;
                        }
                        case R.id.type_Brush:
                        {
                            crayon.run(((MandaraDrawingView)getActivity()).MainMandara,
                                    ((MandaraDrawingView)getActivity()).MirrorMandara,
                                    ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon,
                                    event,
                                    ((MandaraDrawingView)getActivity()).MandaraColor,
                                    ((MandaraDrawingView)getActivity()).MandaraColorSize,
                                    Between_w,
                                    Between_h);
                            break;
                        }
                        case R.id.type_Filler:
                        {
                            new Fuller().run(((MandaraDrawingView)getActivity()).MainMandara,
                                    ((MandaraDrawingView)getActivity()).MandaraColor,
                                    event,
                                    Between_w,
                                    Between_h);
                            break;
                        }

                        case R.id.type_crayon:
                        {
                            crayon.run(((MandaraDrawingView)getActivity()).MainMandara,
                                    ((MandaraDrawingView)getActivity()).MirrorMandara,
                                    ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon,
                                    event,
                                    ((MandaraDrawingView)getActivity()).MandaraColor,
                                    ((MandaraDrawingView)getActivity()).MandaraColorSize,
                                    Between_w,
                                    Between_h);
                            break;
                        }
                    }
                    pb.setVisibility(GONE);
                }


            invalidate();
            return true;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //초기설정들
            if(Between_w==0)
            {
                Between_w=((float)w/MainSize_w);
            }
            if(Between_h==0)
            {
                Between_h=((float)h/MainSize_h);
            }
            if(((MandaraDrawingView)getActivity()).MainMandara!=null)
            {

                m.setTranslate(0,0);
                m.setScale(Between_w,Between_h);
            }



        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if(!ActiveDisplayControl) {
                Paint p = new Paint();
                Path path = new Path();
                if (ar && oldp != null) {

                    path.moveTo(oldp.x, oldp.y);
                    path.lineTo(mX, mY);


                    p.setAntiAlias(true);
                    p.setStyle(Paint.Style.STROKE);
                    p.setStrokeJoin(Paint.Join.ROUND);
                    p.setStrokeCap(Paint.Cap.ROUND);
                    p.setStrokeWidth(((MandaraDrawingView)getActivity()).MandaraColorSize);
                    p.setColorFilter(null);
                    p.setColor(((MandaraDrawingView)getActivity()).MandaraColor);
                    Canvas c = new Canvas(((MandaraDrawingView)getActivity()).MainMandara);
                    Canvas mc = new Canvas(((MandaraDrawingView)getActivity()).MirrorMandara);

                    switch (((MandaraDrawingView)getActivity()).MandaraPenType) {
                        case R.id.type_Pen: {

                            c.drawPath(path, p);
                            mc.drawPath(path, p);
                            oldp = new Point(mX, mY);

                            break;
                        }
                        case R.id.type_crayon: {
                            p.setAlpha(200);
                            p.setColorFilter(new PorterDuffColorFilter(((MandaraDrawingView)getActivity()).MandaraColor, PorterDuff.Mode.SRC_ATOP));
                            dr.drawFromTo(c,((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon, new float[]{oldp.x, oldp.y}, mX, mY, p);
                            oldp = new Point(mX, mY);
                            p.setAlpha(255);
                            mc.drawPath(path, p);
                            break;
                        }
                        case R.id.type_Brush:{
                            p.setAlpha(200);
                            p.setColorFilter(new PorterDuffColorFilter(((MandaraDrawingView)getActivity()).MandaraColor, PorterDuff.Mode.SRC_ATOP));
                            dr.drawFromTo(c,((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon, new float[]{oldp.x, oldp.y}, mX, mY, p);
                            oldp = new Point(mX, mY);
                            p.setAlpha(255);
                            mc.drawPath(path, p);
                            break;
                        }
                    }
                }

            }
            canvas.drawBitmap(((MandaraDrawingView)getActivity()).MainMandara, m, null);




        }



    }



}
