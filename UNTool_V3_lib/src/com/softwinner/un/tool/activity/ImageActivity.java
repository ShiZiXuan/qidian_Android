package com.softwinner.un.tool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softwinner.un.tool.R;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ArrayList<String> mData;
    private ImageAdapter mAdapter;
    private TextView number;
    private ArrayList<ImageView> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        number = (TextView) findViewById(R.id.number_tv);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        mData = (ArrayList<String>) intent.getSerializableExtra("data");
        number.setText((position + 1) + "/" + mData.size());
        images = new ArrayList<>();
        for (String url : mData) {
            ImageView image = new ImageView(this);
            Glide.with(this).load(url).into(image);
            images.add(image);
        }
        mAdapter = new ImageAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                number.setText((position + 1) + "/" + mData.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = images.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }
    }
}
