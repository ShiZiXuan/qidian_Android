package com.uautogo.qidian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.AboutUs;
import com.uautogo.qidian.activity.CarInfoActivity;
import com.uautogo.qidian.activity.HeadActivity;
import com.uautogo.qidian.activity.OrderActivity;
import com.uautogo.qidian.activity.SplashActivity;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.ToastUtil;

/**
 * Created by linjing on 2017/9/3.
 */

public class Myfragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ImageView headImg;
    private TextView nickyTxt;
    private String imgUrl;
    private String nicky;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_center, container, false);
        initView();
        return rootView;
    }
    private void initView() {
        headImg = (ImageView) rootView.findViewById(R.id.center_head_iv);
        nickyTxt = (TextView) rootView.findViewById(R.id.center_nike);
        headImg.setOnClickListener(this);
        nickyTxt.setOnClickListener(this);
        rootView.findViewById(R.id.view_check_update).setOnClickListener(this);
        rootView.findViewById(R.id.view_car_cache).setOnClickListener(this);
        rootView.findViewById(R.id.view_about).setOnClickListener(this);
        rootView.findViewById(R.id.view_logout).setOnClickListener(this);
        rootView.findViewById(R.id.view_clear_info).setOnClickListener(this);
        //Log.e("用户微信登录手机号",SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_PHONE));
        if(!TextUtils.isEmpty(SharedPreferencesUtils.getString(getActivity(),"wxImg"))){//用户微信登录
            if("ok".equals(SharedPreferencesUtils.getString(getActivity(),"isHeadReplace"))){//用户更改过头像
             //   Log.e("!!!!!!!!!!!!!!","用户更改过头像");
                imgUrl = "/storage/emulated/0/qidian/data/file/img/head.jpg";
                Glide.with(this).load(imgUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.default_avatar)
                        .into(headImg);
            }else{//微信头像
              //  Log.e("!!!!!!!!!!!!!!","微信头像");
                imgUrl = SharedPreferencesUtils.getString(getActivity(),"wxImg");
                Glide.with(getActivity()).load(imgUrl).into(headImg);
            }
            if("ok".equals(SharedPreferencesUtils.getString(getActivity(),"isNameReplace"))){//用户更改过的名字
                nicky = SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_NICKY, null);
                if (TextUtils.isEmpty(nicky)) {
                    nicky = SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_PHONE);
                }
                nickyTxt.setText(nicky);
            }else{//微信名字
                nicky = SharedPreferencesUtils.getString(getActivity(),"wxName");
                nickyTxt.setText(nicky);
            }
        }else{//手机号登录
            Glide.with(this).load("/storage/emulated/0/qidian/data/file/img/head.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.default_avatar)
                    .into(headImg);
           nicky = SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_NICKY, null);
            if (TextUtils.isEmpty(nicky)) {
                nicky = SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_PHONE);
            }
            nickyTxt.setText(nicky);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.center_head_iv: {
                Intent intent = new Intent(getActivity(), HeadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",nicky);
                bundle.putString("img",imgUrl);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            case R.id.view_check_update: {
                //我的订单
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.view_car_cache: {
                ToastUtil.show(getActivity().getApplicationContext(), "已清除");
                break;
            }
            case R.id.view_about: {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
                break;
            }
            case R.id.view_clear_info: {
                //我的车辆
                Intent intent = new Intent(getActivity(), CarInfoActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.view_logout: {
                SharedPreferencesUtils.putString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID, "");
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
                       }

            default:
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        if (requestCode == 0x1 && resultCode == RESULT_OK) {
//            if (data != null) {
//                SharedPreferencesUtils.putString(getActivity(), SharedPreferencesUtils.Key.KEY_HEAD_URI, data.getData().toString());
//                headImg.setImageURI(data.getData());
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }




}