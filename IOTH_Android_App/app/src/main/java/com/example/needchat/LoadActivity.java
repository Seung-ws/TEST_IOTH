package com.example.needchat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.needchat.Data.FireBaseLoginInfo;
import com.example.needchat.menu_tab4.tab4_usersetting;
import com.firebase.ui.auth.IdpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadActivity extends AppCompatActivity {
    private Animation au=null;
    private ImageView logo=null;
    private tab4_usersetting tab4us=null;
    private AppCompatActivity c=null;
    private ProgressBar pb=null;
    private static final int RC_SIGN_IN = 123;
    private Button load_btn1=null;
    Thread start1=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
               if(FireBaseLoginInfo.show_login_state(getApplicationContext()))
                {

                    // FireBaseLoginInfo.사용자정보출력(this);
                    try{
                        File f=new File(getApplicationContext().getCacheDir()+"\\userdata.dat");
                        if(f.isFile())
                        {
                            FileInputStream fis=new FileInputStream(f);
                            ObjectInputStream ois=new ObjectInputStream(fis);
                            tab4us=(tab4_usersetting)ois.readObject();
                            ois.close();
                            fis.close();

                        }else
                        {
                            tab4us=new tab4_usersetting();
                        }

                        Intent intent=new Intent(getApplicationContext(),menuActivity.class);
                        intent.putExtra("usersetting",tab4us);


                        finish();
                        startActivity(intent);
                    }catch(Exception e)
                    {

                    }

                };

                // ...
            } else {
                System.out.println("!!!!!!!!!!!!!!!!!");
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                System.out.println(response.getError());
            }
        }

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        c=this;
        logo=findViewById(R.id.Logo);
        load_btn1=findViewById(R.id.load_btn1);
        pb=findViewById(R.id.load_progressBar);
        Handler hd=new Handler();
        hd.postDelayed(new FirebaseLogin(),3000);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in ,R.anim.fade_out );
    }
    public void LoginTest(View v)
    {
        //if(AutoLogin!=null)AutoLogin.interrupt();
        FireBaseLoginInfo.call_login_intent(this,RC_SIGN_IN);

    }

    class FirebaseLogin implements Runnable{

        @Override
        public void run() {
            c.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(c,Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        } else {
                            ActivityCompat.requestPermissions(c,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            finish();
                            return;
                        }
                    }
                    long time =System.currentTimeMillis();
                    while(!(System.currentTimeMillis()-time>3000))
                    {

                    }
                    if(FireBaseLoginInfo.show_login_state(getApplicationContext()))
                    {
                        try{
                            File f=new File(getApplicationContext().getCacheDir()+"\\userdata.dat");
                            if(f.isFile())
                            {
                                FileInputStream fis=new FileInputStream(f);
                                ObjectInputStream ois=new ObjectInputStream(fis);
                                tab4us=(tab4_usersetting)ois.readObject();
                                ois.close();
                                fis.close();

                            }

                            Intent intent=new Intent(getApplicationContext(),menuActivity.class);
                            intent.putExtra("usersetting",tab4us);


                            finish();
                            startActivity(intent);
                        }catch(Exception e)
                        {
                            System.out.println("백업");
                        }
                    }else
                    {
                        findViewById(R.id.textView10).setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                        load_btn1.animate().alpha(1).setDuration(700);
                        load_btn1.setEnabled(true);
                    }
                }
            });

        }
    }











    Runnable r=new Runnable() {
        @Override
        public void run() {
            try {
              /*  if(FireBaseLoginInfo.로그인여부(a))
                {

                }


*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
