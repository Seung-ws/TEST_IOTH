package com.project2.mandara.LoginFilter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project2.mandara.R;

import java.util.Arrays;
import java.util.List;

public class FireBaseLoginInfo {


    public static FirebaseUser g_user=null;
    public static GoogleSignInOptions gso=null;
    public static GoogleApiClient gac=null;
    private static final int RC_SIGN_IN = 123;
    public static void show_user_info(AppCompatActivity c){
        String res=g_user.getDisplayName()+"\n";
        res+=g_user.getEmail()+"\n";
        res+=g_user.getPhoneNumber()+"\n";
        res+=g_user.getProviderId()+"\n";
        res+=g_user.getUid()+"\n";

        Toast.makeText(c,res,Toast.LENGTH_SHORT).show();

    }

    public static boolean test_login(AppCompatActivity c) {

        g_user = FirebaseAuth.getInstance().getCurrentUser();
        if(g_user!=null)
        {
            Toast.makeText(c,"success",Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(c,"fail",Toast.LENGTH_SHORT).show();
        return false;
    }
    public static void call_login(AppCompatActivity c, int requestCode){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        c.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                requestCode);
    }
    public static void call_login_info(AppCompatActivity c)
    {

    }



}
