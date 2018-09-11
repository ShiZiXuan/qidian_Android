package com.uautogo.qidian.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.fragment.CarMsgFragment;
import com.uautogo.qidian.fragment.CarOwnerSmgFragment;
import com.uautogo.qidian.fragment.PayFragment;
import com.uautogo.qidian.fragment.SignFragment;
import com.uautogo.qidian.model.BankCardInfo;
import com.uautogo.qidian.model.DriveCardInfo;
import com.uautogo.qidian.model.IDCardFan;
import com.uautogo.qidian.model.IDCardZheng;

/**
 * 车主资料
 */
public class AllMsgActivity extends BaseActivity implements View.OnClickListener{
    Button ok_btn;
    int index = 0;
    Fragment[] mFragments = new Fragment[4];
    ImageView image2_iv, image4_iv, image5_iv,back;
    TextView illegal_top_bar_title_txt;
    public static BankCardInfo bankCardInfo;
    public static IDCardZheng idCardZheng;
    public static IDCardFan idCardFan;
    public static DriveCardInfo driveCardInfo;
    public static String phone_number;
    public static String first_result_number;
    WXPayReceiver mWXPayReceiver = new WXPayReceiver();
    public static String receiverName, receiverMobile, receiverProvince, receiverCity, receiverDistrict, receiverAddress;
    //boolean toSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_mag_act);
        initView();
        Intent intent = getIntent();
        int code = Integer.parseInt(intent.getStringExtra("etcCode"));
        Log.e("ETCCode--->",code+"");
        if(code==300){//购买设备
            mFragments[2] = new PayFragment();
            mFragments[3] = new SignFragment();
            illegal_top_bar_title_txt.setText("车辆资料");
            image2_iv.setImageResource(R.drawable.two_active);
            illegal_top_bar_title_txt.setText("设备付款");
            image4_iv.setImageResource(R.drawable.three_active);
            index = 2;
        }else if(code==333){//等待发货
            mFragments[3] = new SignFragment();
            illegal_top_bar_title_txt.setText("车辆资料");
            image2_iv.setImageResource(R.drawable.two_active);
            illegal_top_bar_title_txt.setText("设备付款");
            image4_iv.setImageResource(R.drawable.three_active);
            illegal_top_bar_title_txt.setText("办理成功");
            image5_iv.setImageResource(R.drawable.four_active);
            ok_btn.setVisibility(View.GONE);
            index = 3;
        }else if(code==400){//未办理
            mFragments[0] = new CarOwnerSmgFragment();
            mFragments[1] = new CarMsgFragment();
            mFragments[2] = new PayFragment();
            mFragments[3] = new SignFragment();
        }else{//车辆信息
            mFragments[1] = new CarMsgFragment();
            mFragments[2] = new PayFragment();
            mFragments[3] = new SignFragment();
            illegal_top_bar_title_txt.setText("车辆资料");
            image2_iv.setImageResource(R.drawable.two_active);
            index = 1;
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_id, mFragments[index]).commit();
        registerReceiver(mWXPayReceiver, new IntentFilter("wx_pay_finished"));
//        toSign = getIntent().getBooleanExtra("toSign", false);
//        if (toSign) {
//            index = 3;
//            goNext();
//        }
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mWXPayReceiver);
        super.onDestroy();
    }
    private void initView() {
        image2_iv = (ImageView) findViewById(R.id.image2_iv);
        //image3_iv = (ImageView) findViewById(R.id.image3_iv);
        image4_iv = (ImageView) findViewById(R.id.image4_iv);
        image5_iv = (ImageView) findViewById(R.id.image5_iv);
        illegal_top_bar_title_txt = (TextView) findViewById(R.id.illegal_top_bar_title_txt);
        back = findViewById(R.id.back_iv);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok_btn) {
            switch (index) {
                case 0:
                    ((CarOwnerSmgFragment) mFragments[0]).showDialog();

                    break;
                case 1:
                    ((CarMsgFragment) mFragments[1]).showDialog();

                    break;
                case 2:
                    ((PayFragment) mFragments[2]).payMethod();

                    break;
                case 3:

                    ((SignFragment) mFragments[3]).show();
                    ok_btn.setVisibility(View.GONE);
                    break;
            }
            ok_btn.setClickable(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ok_btn.setClickable(true);
                }
            },1000);
        }
    }
    public void goNext() {
        index++;
        switch (index) {
            case 1:
                illegal_top_bar_title_txt.setText("车辆资料");
                image2_iv.setImageResource(R.drawable.two_active);
                break;

            case 2:
                illegal_top_bar_title_txt.setText("设备付款");
                image4_iv.setImageResource(R.drawable.three_active);
                break;
            case 3:
                illegal_top_bar_title_txt.setText("办理成功");
                image5_iv.setImageResource(R.drawable.four_active);
                ok_btn.setVisibility(View.GONE);
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_id, mFragments[index]).commit();
    }

    class WXPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            goNext();
        }
    }
}
