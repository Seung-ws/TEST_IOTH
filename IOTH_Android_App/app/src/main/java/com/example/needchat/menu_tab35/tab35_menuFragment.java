package com.example.needchat.menu_tab35;

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

public class tab35_menuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Animation anim= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        View v=inflater.inflate(R.layout.fragment_tab35_menu,container,false);
        v.startAnimation(anim);
        v.setTranslationX(-5000);
        v.animate().translationX(0).setDuration(500);
        return v;
    }
}
