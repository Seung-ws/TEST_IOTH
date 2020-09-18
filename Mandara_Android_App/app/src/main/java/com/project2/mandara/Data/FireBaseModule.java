package com.project2.mandara.Data;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.mandara.Data.StaticData;
import com.project2.mandara.Mandara.MainForm.MandaraSelectingVIew;
import com.project2.mandara.Data.FileInfo.MandaraFileInfo;


public class FireBaseModule {
    private String CommandType="";
    private Context c;
    public FireBaseModule(Context c, String type)
    {
        this.c=c;CommandType=type;
    }













}
