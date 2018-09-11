package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.LoginActivity;


public class GuideFragment extends Fragment {
    ImageView image_iv,go_main_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt("index");
        View view = inflater.inflate(R.layout.fragment_guide, null, false);
        image_iv = (ImageView) view.findViewById(R.id.image_iv);
        go_main_tv = (ImageView) view.findViewById(R.id.go_main_tv);
        switch (index) {
            case 1:
                Glide.with(this).load(R.drawable.splash_1).into(image_iv);
                break;
            case 2:
                Glide.with(this).load(R.drawable.splash_2).into(image_iv);
                break;
            case 3:
                Glide.with(this).load(R.drawable.splash_3).into(image_iv);
                go_main_tv.setVisibility(View.VISIBLE);
                break;
        }
        go_main_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
