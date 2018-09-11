package com.uautogo.qidian.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.uautogo.qidian.MainActivity;
import com.uautogo.qidian.R;
import com.uautogo.qidian.fragment.GuideFragment;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 2017/5/28.
 * 欢迎界面
 */

public class SplashActivity extends BaseActivity {
    ViewPager guide_vp;
    ImageView splash_img;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        guide_vp = (ViewPager) findViewById(R.id.guide_vp);
        splash_img = (ImageView) findViewById(R.id.splash_img);

        boolean isFirstOpen = SharedPreferencesUtils.getBoolean(this, "isFirstOpen", true);
        if (isFirstOpen) {
            splash_img.setVisibility(View.INVISIBLE);
            guide_vp.setVisibility(View.VISIBLE);
            List<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", i + 1);
                GuideFragment fragment = new GuideFragment();
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
            GuideAdapter adapter = new GuideAdapter(getFragmentManager(), fragments);
            guide_vp.setAdapter(adapter);
            SharedPreferencesUtils.putBoolean(this, "isFirstOpen", false);
        } else {
            splash_img.setVisibility(View.VISIBLE);
            guide_vp.setVisibility(View.INVISIBLE);
            splash_img.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    if (TextUtils.isEmpty(userId)) {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, 2000);
        }
        userId = SharedPreferencesUtils.getString(this, SharedPreferencesUtils.Key.KEY_USER_ID);
    }


    class GuideAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public GuideAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
