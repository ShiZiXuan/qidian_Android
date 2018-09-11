package com.uautogo.qidian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.uautogo.qidian.R;

/**
 * Created by Jeremy on 2017/6/3.
 * 关于我们
 */

public class AboutUs extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.center_top_bar_left_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
