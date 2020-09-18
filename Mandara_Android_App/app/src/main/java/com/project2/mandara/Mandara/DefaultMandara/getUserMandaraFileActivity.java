package com.project2.mandara.Mandara.DefaultMandara;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.project2.mandara.Data.FileInfo.MandaraFileInfo;
import com.project2.mandara.Data.OpenSave.Server_ImageDelete;
import com.project2.mandara.Data.OpenSave.Server_ImageGetter;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.Function.DefaulMandara.MandaraDrawingView;
import com.project2.mandara.LoginFilter.FireBaseLoginInfo;
import com.project2.mandara.analysis.CheckResultActivity;
import com.project2.mandara.R;

import org.json.JSONArray;



public class getUserMandaraFileActivity extends AppCompatActivity {
    private LinearLayout carditem_panel;
    private Server_ImageGetter sig=null;
    private String CommandType=null;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_get_user_mandarafile);
        carditem_panel = (LinearLayout) findViewById(R.id.carditem_bg2);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.activity_start);
        findViewById(R.id.activity_get_user_mandarafile_bg).startAnimation(anim);
        sig=new Server_ImageGetter();
        CommandType=getIntent().getStringExtra("CommandType");
        StaticData.process_setmsg(this);
        carditem_panel.removeAllViews();

    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(carditem_panel.getChildCount()==0)
        {
            StaticData.process_showmsg("데이터를 불러오는 중입니다.");
            파싱후데이터가져오기();
            StaticData.process_delmsg();
        }

    }

    private void 파싱후데이터가져오기(){
        JSONArray jsonArray=sig.getUserSaveImageJSONList(FireBaseLoginInfo.g_user.getUid(),CommandType);

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                carditem_panel.addView(버튼제작(sig.getUserIconImageBitmap(FireBaseLoginInfo.g_user.getUid(), jsonArray.getJSONObject(i).getString("toName"), CommandType), jsonArray.getJSONObject(i).getString("toName")));
            }
        }catch (Exception e)
        {
            System.out.println("JSON파싱 불러오기 카드추가 에러 : "+e);
        }
   }
    private CardView 버튼제작(Bitmap b,final String toName) {

            final CardView cv = new CardView(this);

            LinearLayout ll = new LinearLayout(this);
            ImageView iv = new ImageView(this);
            TextView tv = new TextView(this);

            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    ,LinearLayout.LayoutParams.MATCH_PARENT));
            ll.setOrientation(LinearLayout.VERTICAL);
            //카드뷰의 내용
            tv.setText(toName);
            tv.setTextSize(20);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            //카드뷰의 이미지

            iv.setImageBitmap(b);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            ll.addView(iv);
            ll.addView(tv);
            //카드뷰
            CardView.LayoutParams clp = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            clp.setMargins(10, 10, 10, 10);
            cv.setLayoutParams(clp);
            cv.setBackgroundResource(R.drawable.button_style);
            cv.addView(ll);
            cv.setTag(toName);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   CardView_Touch_Option(cv,toName);
                }
            });


        return cv;

    }


    private void CardView_Touch_Option(final View v,final String toName){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("불러오기 정보").setMessage("불러오겠습니까?").setPositiveButton("불러오기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(CommandType.equals("MandaraDefault"))
                {
                    StaticData.process_showmsg("불러오는중..");
                    Intent intent = new Intent(getApplicationContext(), MandaraDrawingView.class);
                    MandaraFileInfo mfi=new MandaraFileInfo();
                    mfi.setArtName(toName);
                    mfi.setCommandType(CommandType);
                    intent.putExtra("UserData",mfi);
                    StaticData.process_delmsg();
                    finish();
                    startActivity(intent);
                }
                else if(CommandType.equals("MandaraTest"))
                {
                    /*StaticData.처리중문구_출력("불러오는중..");
                    Intent intent = new Intent(getApplicationContext(), MandaraTestDrawingView.class);
                    MandaraFileInfo mfi=new MandaraFileInfo();
                    mfi.setArtName(toName);
                    mfi.setCommandType(CommandType);
                    StaticData.MainBitmap=(sig.getUserMainImageBitmap(FireBaseLoginInfo.g_user.getUid(),toName,CommandType)).copy(Bitmap.Config.ARGB_8888,true);
                    StaticData.MirrorBitmap=(sig.getUserMirrorImageBitmap(FireBaseLoginInfo.g_user.getUid(),toName,CommandType)).copy(Bitmap.Config.ARGB_8888,true);
                    StaticData.OriginBitmap=(sig.getUserOriginImageBitmap(FireBaseLoginInfo.g_user.getUid(),toName,CommandType)).copy(Bitmap.Config.ARGB_8888,true);
                    intent.putExtra("UserData",mfi);
                    StaticData.처리중문구_제거();
                    finish();
                    startActivity(intent);*/

                    Intent intent = new Intent(getApplicationContext(), CheckResultActivity.class);
                    MandaraFileInfo mfi=new MandaraFileInfo();
                    mfi.setArtName(toName);
                    mfi.setCommandType(CommandType);
                    intent.putExtra("UserData",mfi);
                    StaticData.process_delmsg();
                    finish();
                    startActivity(intent);


                }
                dialogInterface.dismiss();


            }
        })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // delUserFile(getApplicationContext(),name);


                        StaticData.process_showmsg("처리중입니다.");

                        if(new Server_ImageDelete().ImageDeleter(FireBaseLoginInfo.g_user.getUid(),toName,CommandType));
                        {

                            carditem_panel.removeView(v);
                            Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                        StaticData.process_delmsg();
                        dialogInterface.dismiss();
                    }
                }).show();

    }






}

