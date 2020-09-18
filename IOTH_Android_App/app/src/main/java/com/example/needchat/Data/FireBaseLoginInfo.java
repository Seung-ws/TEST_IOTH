package com.example.needchat.Data;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FireBaseLoginInfo {


    public static FirebaseUser g_user=null;
    public static GoogleSignInOptions gso=null;
    public static GoogleApiClient gac=null;
    private static final int RC_SIGN_IN = 123;
    public static void userinfo(Context c){
        String res=g_user.getDisplayName()+"\n";
        res+=g_user.getEmail()+"\n";
        res+=g_user.getPhoneNumber()+"\n";
        res+=g_user.getProviderId()+"\n";
        res+=g_user.getUid()+"\n";

        Toast.makeText(c,res,Toast.LENGTH_SHORT).show();

    }

    public static boolean show_login_state(Context c) {

        g_user = FirebaseAuth.getInstance().getCurrentUser();
        if(g_user!=null)
        {
            Toast.makeText(c,"success",Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(c,"fail",Toast.LENGTH_SHORT).show();
        return false;
    }
    public static void call_login_intent(AppCompatActivity c, int requestCode){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        c.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                requestCode);
    }



}
