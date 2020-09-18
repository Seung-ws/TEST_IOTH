package com.project2.mandara.Function.DefaulMandara;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project2.mandara.Data.OpenSave.Server_ImageAdder;
import com.project2.mandara.Data.OpenSave.Server_ImageGetter;
import com.project2.mandara.Data.OpenSave.Server_getOfferImage;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.LoginFilter.FireBaseLoginInfo;
import com.project2.mandara.R;

import static com.project2.mandara.Data.StaticColor.StaticColor.Orange_sub2;
import static com.project2.mandara.Data.StaticColor.StaticColor.Red_sub0;


import static com.project2.mandara.Data.StaticData.reader;


public class MandaraDrawingView extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "MDF";
    private static final String KEY_NUMBER = "100001";
    private int mNumber = 0;

    //pop하기 위한 카운트
    private FragmentManager.OnBackStackChangedListener mListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int count = 0;
            for (Fragment f: fragmentManager.getFragments()) {
                if (f != null) {
                    count++;
                }
            }
            mNumber = count;
        }
    };

    private FrameLayout bg=null;
    private FloatingActionButton Colorfab=null;

    private MandaraFileInfo mfi=null;

    private Server_ImageAdder sia=null;
    private Server_ImageGetter sig=null;

    public Bitmap MainMandara =null;
    public Bitmap MirrorMandara =null;
    public Bitmap OriginMandara=null;
    public Bitmap RenderBitmap_Type_Crayon=null;

    public int MandaraColor=Red_sub0;
    public float MandaraColorSize=3f;
    public int MandaraPenType= R.id.type_Pen;
    public int MandaraRenderType1=R.drawable.testbrush;
    public int MandaraRenderType2=R.drawable.brush_pencil2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mfi=(MandaraFileInfo)getIntent().getSerializableExtra("UserData");
            sia=new Server_ImageAdder(FireBaseLoginInfo.g_user.getUid(),mfi, this);
            sig=new Server_ImageGetter();
            if(mfi.getArtNumber()!=null) {//번호가있으면 도안이며 없으면 저장파일
                MainMandara = Bitmap.createBitmap(new Server_getOfferImage(mfi.getArtNumber(), mfi.getMandaraName()).setBitmapPatch().copy(Bitmap.Config.ARGB_8888, true));
                MirrorMandara = Bitmap.createBitmap(new Server_getOfferImage(mfi.getArtNumber(), mfi.getMandaraName()).setBitmapPatch().copy(Bitmap.Config.ARGB_8888, true));
                OriginMandara = Bitmap.createBitmap(new Server_getOfferImage(mfi.getArtNumber(), mfi.getMandaraName()).setBitmapPatch().copy(Bitmap.Config.ARGB_8888, true));
            }else
            {
                MainMandara=(sig.getUserMainImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())).copy(Bitmap.Config.ARGB_8888,true);
                MirrorMandara=(sig.getUserMirrorImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())).copy(Bitmap.Config.ARGB_8888,true);
                OriginMandara=(sig.getUserOriginImageBitmap(FireBaseLoginInfo.g_user.getUid(),mfi.getArtName(),mfi.getCommandType())).copy(Bitmap.Config.ARGB_8888,true);
            }
               setContentView(R.layout.activity_mandara_drawingview);
      //      if(mfi.getCommandType().equals("MandaraDefault"))setContentView(R.layout.activity_mandara_drawingview);
     //       if(mfi.getCommandType().equals("MandaraTest"))setContentView(R.layout.activity_mandara_checksystem_drawingview);
            //저장을 위한 용도..

            StaticData.process_setmsg(this);
            bg=(FrameLayout)findViewById(R.id.Drawing_bg);
            Animation anim= AnimationUtils.loadAnimation(this,R.anim.activity_start);
            bg.startAnimation(anim);
            Colorfab=(FloatingActionButton)findViewById(R.id.fab_colorpicker);
            //호출할 프레그먼트에 대한 정의



            loadDrawingTool(100002);


            CreateFirstFragment(savedInstanceState);




        }

            @Override
            protected void onPause() {
                overridePendingTransition(0,0);
                super.onPause();
            }
            @Override
            protected void onDestroy() {
                super.onDestroy();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.removeOnBackStackChangedListener(mListener);
                reader.recordRemove();
                MandaraColor=Red_sub0;
                MandaraColorSize=3;
                MandaraPenType=R.id.type_Pen;

                StaticData.ActiveDisplayControl=false;
            }

            @Override
            public void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                outState.putInt(KEY_NUMBER, mNumber);

            }

            @Override
            protected void onRestoreInstanceState(Bundle savedInstanceState) {
                super.onRestoreInstanceState(savedInstanceState);
                mNumber = savedInstanceState.getInt(KEY_NUMBER);

            }



            private class FragmentPanel extends FrameLayout
            {

                @SuppressLint("ResourceType")
                public FragmentPanel(Context context, int x, int y) {
                    super(context);
                    FrameLayout.LayoutParams flp=new FrameLayout.LayoutParams(x,y);
                    flp.gravity= Gravity.CENTER;
                    flp.setMargins(20,20,20,20);
                    this.setLayoutParams(flp);
                    this.setBackgroundColor(Color.BLACK);

                    this.setId(-110001);
                }

            }






            //주요 함수들을 위한 정의들
                //컬러를 변환하기 위한
                private int IntegerColortoHex(int color ,int i)
                {
                    int A=(color>>24)&0xFF;
                    int R=(color>>16)&0xFF;
                    int G=(color>>8)&0xFF;
                    int B=color&0xFF;
                    if(R+i<=255)
                        R+=i;
                    if(G+i<=255)
                        G+=i;
                    if(B+i<=255)
                        B+=i;
                    return (A<<24)+(R<<16)+(G<<8)+(B);
                }
                //초기 프래그먼트 생성
                private void CreateFirstFragment(Bundle savedInstanceState)
                {
                    DisplayMetrics dm=new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    FragmentPanel fp=new FragmentPanel(this,dm.widthPixels,dm.widthPixels);
                    bg.addView(fp);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.addOnBackStackChangedListener(mListener);
                    Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
/*

                    if (savedInstanceState == null) {
                      if(mfi.getCommandType().equals("MandaraDefault"))
                        {
                            fragmentManager.beginTransaction()
                                    .add(fp.getId(), MandaraDrawingFragment.getInstance(mNumber), FRAGMENT_TAG)
                                    .addToBackStack(null)
                                    .commit();
                        }else if(mfi.getCommandType().equals("MandaraTest"))
                        {
                            fragmentManager.beginTransaction()
                                    .add(fp.getId(), MandaraTestDrawingFragment.getInstance(mNumber), FRAGMENT_TAG)
                                    .addToBackStack(null)
                                    .commit();
                        }

                    }*/
                    fragmentManager.beginTransaction()
                            .add(fp.getId(), MandaraDrawingFragment.getInstance(mNumber), FRAGMENT_TAG)
                            .addToBackStack(null)
                            .commit();
                }




                public  void 프래그먼트생성(int id){

                }
                private void 프래그먼트삭제()
                {
                    if (mNumber == 0) {
                        return;
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStack();

                }



                //온클릭 버튼들
                //색상관련 함수글시작


                private FloatingActionButton CreateColorButton(final int Color){
                    final FloatingActionButton fab=new FloatingActionButton(this);
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
                                MandaraColor=Color;
                                Colorfab.setBackgroundTintList(ColorStateList.valueOf(MandaraColor));
                                Colorfab.setColorFilter(MandaraColor);
                                Colorfab.setRotation(0);
                                Colorfab.animate().rotation(360);
                            }

                            ((FloatingActionButton)view).setEnabled(false);


                        }
                    });
                    return fab;
                }































                //색상관련 함수 끝
                public void Drawing_Back_function(View v){
                    Bitmap x;
                    if((x=reader.back_getBitmapRecord())!=null)
                    {
                        MainMandara=Bitmap.createBitmap(x);
                    }
                    reader.Button_state_checking(this);

                }
                public void Drawing_Forward_function(View v)
                {
                    Bitmap x;
                    if((x=reader.forward_getBitmapRecord())!=null)
                    {
                        MainMandara=Bitmap.createBitmap(x);
                    }
                    reader.Button_state_checking(this);
                }
                //저장
                public void onClickSave(View v)
                {
                    //new StaticMandaraCreateFile().AlertSaveDialog(this,mfi);

                    sia.AlertSaveDialog(OriginMandara,MirrorMandara,MainMandara,this);

                }
                public void setDisplayControl(View v)
                {
                    StaticData.ActiveDisplayControl=!StaticData.ActiveDisplayControl;
                    if(StaticData.ActiveDisplayControl)
                    {
                        ((FloatingActionButton)v).setColorFilter(Orange_sub2);
                    }
                    else
                    {
                        ((FloatingActionButton)v).setColorFilter(null);
                    }
                    ((FloatingActionButton)v).setRotation(0);
                    ((FloatingActionButton)v).animate().rotation(360);
                }
























    public void loadDrawingTool(int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment=MandaraDrawingtool.getInstance(id);

        fragmentManager.beginTransaction()
                .add(fragment.getId(), fragment,"MDT")
                .addToBackStack(null)
                .commit();
    }

}



