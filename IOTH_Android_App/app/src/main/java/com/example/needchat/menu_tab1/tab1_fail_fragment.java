package com.example.needchat.menu_tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.needchat.R;

public class tab1_fail_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab2_thg,container,false);
        v.startAnimation(anim);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
