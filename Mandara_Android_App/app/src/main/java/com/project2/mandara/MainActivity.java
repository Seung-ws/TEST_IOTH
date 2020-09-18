package com.project2.mandara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.IdpResponse;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.LoginFilter.FireBaseLoginInfo;
import com.project2.mandara.LoginFilter.Main_Info;
import com.project2.mandara.Mandara.MainForm.Load_Mandara_Type_Activity;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private Thread AutoLogin=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {

                // Successfully signed in
                if(FireBaseLoginInfo.test_login(this))
                {
                   // FireBaseLoginInfo.사용자정보출력(this);
                    //////////new Server_useradd(FireBaseLoginInfo.g_user.getUid());
                    Intent intent=new Intent(this, Load_Mandara_Type_Activity.class);
                    finish();
                    startActivity(intent);
                };

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                System.out.println(response.getError());
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main_Info.a_context=getApplicationContext();
        final AppCompatActivity a=this;
        StaticData.process_setmsg(this);
        StaticData.process_showmsg("사용자 확인 중..");
        Runnable r=new Runnable() {
            boolean state=false;
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(FireBaseLoginInfo.test_login(a))
                        {
                            // FireBaseLoginInfo.사용자정보출력(this);
                           /// new Server_useradd(FireBaseLoginInfo.g_user.getUid());
                            Intent intent=new Intent(a, Load_Mandara_Type_Activity.class);
                            StaticData.process_delmsg();
                            finish();
                            startActivity(intent);
                        }
                        else {
                            findViewById(R.id.gotoGoogleId).setEnabled(true);
                            StaticData.process_delmsg();
                        }
                    }
                });

            }
        };
       AutoLogin=new Thread(r);
        AutoLogin.start();

    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    public void LoginTest(View v)
    {
        if(AutoLogin!=null)AutoLogin.interrupt();
        FireBaseLoginInfo.call_login(this,RC_SIGN_IN);

    }


}
