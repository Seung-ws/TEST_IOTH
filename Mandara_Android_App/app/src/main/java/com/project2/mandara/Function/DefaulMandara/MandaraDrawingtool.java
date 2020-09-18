package com.project2.mandara.Function.DefaulMandara;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project2.mandara.R;

import static com.project2.mandara.Data.StaticColor.StaticColor.Black;
import static com.project2.mandara.Data.StaticColor.StaticColor.Blue;
import static com.project2.mandara.Data.StaticColor.StaticColor.Brown;
import static com.project2.mandara.Data.StaticColor.StaticColor.DarkSeeGreen;
import static com.project2.mandara.Data.StaticColor.StaticColor.Green;
import static com.project2.mandara.Data.StaticColor.StaticColor.Navy;
import static com.project2.mandara.Data.StaticColor.StaticColor.Orange;
import static com.project2.mandara.Data.StaticColor.StaticColor.Pink;
import static com.project2.mandara.Data.StaticColor.StaticColor.Puple;
import static com.project2.mandara.Data.StaticColor.StaticColor.Red;
import static com.project2.mandara.Data.StaticColor.StaticColor.Yellow;

public class MandaraDrawingtool extends Fragment {

    public static Fragment getInstance(int no)
    {
        MandaraDrawingtool mdt=new MandaraDrawingtool();
        Bundle b=new Bundle();
        b.putInt("number",no);
        mdt.setArguments(b);
        return mdt;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_mandara_drawing_check_brushoption,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSeekBar((SeekBar)getView().findViewById(R.id.SizeControl));
        setRadioButton((RadioGroup)getView().findViewById(R.id.PenTypeGroup));
        setColorbtn(R.id.redbtn);
        setColorbtn(R.id.orangebtn);
        setColorbtn(R.id.yellowbtn);
        setColorbtn(R.id.greenbtn);
        setColorbtn(R.id.bluebtn);
        setColorbtn(R.id.navybtn);
        setColorbtn(R.id.puplebtn);
        setColorbtn(R.id.brownbtn);
        setColorbtn(R.id.darkseegreenbtn);
        setColorbtn(R.id.pinkbtn);
        setColorbtn(R.id.blackbtn);

    }

    public void setSeekBar(SeekBar target) {

        target.setProgress((int)((MandaraDrawingView)getActivity()).MandaraColorSize);
        target.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((MandaraDrawingView)getActivity()).MandaraColorSize=(i);
                if(((MandaraDrawingView)getActivity()).MandaraPenType==R.id.type_crayon)
                {
                    ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon=
                            Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),((MandaraDrawingView)getActivity()).MandaraRenderType2),
                                    (int)((MandaraDrawingView)getActivity()).MandaraColorSize,(int)  ((MandaraDrawingView)getActivity()).MandaraColorSize,false);
                }
                if(((MandaraDrawingView)getActivity()).MandaraPenType==R.id.type_Brush)
                {
                    ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon=
                            Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),((MandaraDrawingView)getActivity()).MandaraRenderType1),
                                    (int)((MandaraDrawingView)getActivity()).MandaraColorSize,(int)  ((MandaraDrawingView)getActivity()).MandaraColorSize,false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void setRadioButton(RadioGroup v) {
        RadioGroup group=(RadioGroup)v;
        group.check(((MandaraDrawingView)getActivity()).MandaraPenType);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.type_Pen:{
                        ((MandaraDrawingView)getActivity()).MandaraPenType=R.id.type_Pen;
                        break;
                    }
                    case R.id.type_Brush:{
                        ((MandaraDrawingView)getActivity()).MandaraPenType=R.id.type_Brush;
                        ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon=
                                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),((MandaraDrawingView)getActivity()).MandaraRenderType1),
                                        (int)((MandaraDrawingView)getActivity()).MandaraColorSize,(int)  ((MandaraDrawingView)getActivity()).MandaraColorSize,false);
                        break;
                    }
                    case R.id.type_crayon:{
                        ((MandaraDrawingView)getActivity()).MandaraPenType=R.id.type_crayon;
                        ((MandaraDrawingView)getActivity()).RenderBitmap_Type_Crayon=
                                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),((MandaraDrawingView)getActivity()).MandaraRenderType2),
                                        (int)((MandaraDrawingView)getActivity()).MandaraColorSize,(int)  ((MandaraDrawingView)getActivity()).MandaraColorSize,false);
                    break;
                    }

                    case R.id.type_Filler: {
                        ((MandaraDrawingView)getActivity()).MandaraPenType=R.id.type_Filler;
                        break;
                    }
                }
            }
        });

    }
    public void setColorbtn(int target){
        final LinearLayout colorpanel=getView().findViewById(R.id.color_panel_subcolor);
        switch (target)
        {
            case R.id.redbtn:
            {
                getView().findViewById(R.id.redbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Red);
                    }
                });
                break;
            }
            case R.id.orangebtn:
            {
                getView().findViewById(R.id.orangebtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Orange);
                    }
                });
                break;
            }
            case R.id.yellowbtn:
            {
                getView().findViewById(R.id.yellowbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Yellow);
                    }
                });

                break;
            }
            case R.id.greenbtn:
            {
                getView().findViewById(R.id.greenbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Green);
                    }
                });
                break;
            }
            case R.id.bluebtn:
            {
                getView().findViewById(R.id.bluebtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Blue);
                    }
                });

                break;
            }
            case R.id.navybtn:
            {
                getView().findViewById(R.id.navybtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Navy);
                    }
                });

                break;
            }
            case R.id.puplebtn:
            {
                getView().findViewById(R.id.puplebtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Puple);
                    }
                });

                break;
            }
            case R.id.brownbtn:
            {
                getView().findViewById(R.id.brownbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Brown);
                    }
                });

                break;
            }
            case R.id.darkseegreenbtn:
            {
                getView().findViewById(R.id.darkseegreenbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,DarkSeeGreen);
                    }
                });
                break;
            }
            case R.id.pinkbtn:
            {
                getView().findViewById(R.id.pinkbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Pink);
                    }
                });
                break;
            }
            case R.id.blackbtn:
            {
                getView().findViewById(R.id.blackbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorpanel.removeAllViews();
                        AddColorSubButton(colorpanel,Black);
                    }
                });
                break;
            }
        }
    }

    private FloatingActionButton CreateColorButton(final int Color){
        final FloatingActionButton fab=new FloatingActionButton(getActivity());
        //final Button btn=new Button(this);
        //btn.setWidth(50);
        // btn.setHeight(50);
        LinearLayout.LayoutParams vlp=new LinearLayout.LayoutParams(125,125);
        vlp.setMargins(20,20,20,20);
        fab.setLayoutParams(vlp);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color));

        // btn.setBackgroundColor(color);
        // btn.setTextColor(Color);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<((LinearLayout)((FloatingActionButton)view).getParent()).getChildCount();i++)
                {
                    ((LinearLayout)((FloatingActionButton)view).getParent()).getChildAt(i).setEnabled(true);
                    ((MandaraDrawingView)getActivity()).MandaraColor=Color;
                }

                ((FloatingActionButton)view).setEnabled(false);


            }
        });
        return fab;
    }
    private void AddColorSubButton(LinearLayout ll,int[] target){
        for(int i=0;i<target.length;i++)
        {

            final FloatingActionButton b=CreateColorButton(target[i]);

            //b.setImageDrawable(getResources().getDrawable(R.drawable.activity_drawing_btn_colorsub));
            b.setImageDrawable(getResources().getDrawable(R.drawable.activity_drawing_btn_colorsub));

            if(((MandaraDrawingView)getActivity()).MandaraColor==target[i])
            {
                b.setEnabled(false);
            }
            ll.addView(b);
        }
    }





}
