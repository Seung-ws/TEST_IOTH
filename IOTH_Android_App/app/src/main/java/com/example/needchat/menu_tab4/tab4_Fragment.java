package com.example.needchat.menu_tab4;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.needchat.Data.staticData;
import com.example.needchat.R;
import com.example.needchat.Server.Server_Socket;
import com.example.needchat.menuActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class tab4_Fragment extends Fragment {
    Switch GPSswitch=null;
    Switch autoconnect=null;
    Switch auto_home=null;
    EditText host=null;
    EditText port=null;
    Button tab4_selfConnect=null;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab4_usersetting,container,false);
        v.startAnimation(anim);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(((menuActivity)getActivity()).tab4us==null)
        {
            ((menuActivity)getActivity()).tab4us=new tab4_usersetting();
        }

        GPSswitch=this.getView().findViewById(R.id.gps_switch);
        GPSswitch.setChecked(((menuActivity)getActivity()).tab4us.gpsswitch);
        GPSswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                    GPSswitch.setChecked(!b);
                }else{
                    GPSswitch.setChecked(b);
                    ((menuActivity)getActivity()).tab4us.gpsswitch=b;
                }

            }
        });
        autoconnect=this.getView().findViewById(R.id.auto_con_switch);
        autoconnect.setChecked(((menuActivity)getActivity()).tab4us.autoconnect);
        autoconnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                autoconnect.setChecked(b);
                ((menuActivity)getActivity()).tab4us.autoconnect=b;
            }
        });
        auto_home=this.getView().findViewById(R.id.auto_home);
        auto_home.setChecked(((menuActivity)getActivity()).tab4us.homeweather);
        auto_home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                auto_home.setChecked(b);
                ((menuActivity)getActivity()).tab4us.homeweather=b;
            }
        });
        host=this.getView().findViewById(R.id.host);
        host.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((menuActivity)getActivity()).tab4us.host=host.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ((menuActivity)getActivity()).tab4us.host=host.getText().toString();
            }
        });
        host.setText(((menuActivity)getActivity()).tab4us.host);
        port=this.getView().findViewById(R.id.port);
        port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((menuActivity)getActivity()).tab4us.port=port.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ((menuActivity)getActivity()).tab4us.port=port.getText().toString();
            }
        });
        port.setText(((menuActivity)getActivity()).tab4us.port);
        tab4_selfConnect=this.getView().findViewById(R.id.tab4_selfConnect);
        tab4_selfConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    staticData.ss.close();
                    staticData.ss=null;
                    staticData.ss=new Server_Socket(host.getText().toString(), (port.getText().toString()));

                ((menuActivity)getActivity()).reConnect();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }


}

