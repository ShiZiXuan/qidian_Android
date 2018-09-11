package com.uautogo.qidian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.CarInfoAdapter;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.MyCarBean;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jeremy on 2017/6/3.
 * 我的车辆
 */

public class CarInfoActivity extends BaseActivity {
    private ListView car_list;
    private CarInfoAdapter mAdapter;
    private List<MyCarBean.DataBean> list = new ArrayList<>();
    private ImageView iv;
    private String imgUrl = "http://image.uautogo.com/06.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        initView();
    }

    private void initView() {
        findViewById(R.id.center_top_bar_left_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        car_list = findViewById(R.id.car_list);
        iv = findViewById(R.id.car_iv);

        loadData();
    }
    public void loadData() {
        String url = ConfigUrl.URL_GETALLCAR;
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(this, SharedPreferencesUtils.Key.KEY_USER_ID));
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("我的车辆--->",s);
                MyCarBean bean = new Gson().fromJson(s, MyCarBean.class);
                list= bean.getData();
                if(list.size()==0){
                    iv.setVisibility(View.VISIBLE);
                    car_list.setVisibility(View.GONE);

                }else{
                    iv.setVisibility(View.GONE);
                    car_list.setVisibility(View.VISIBLE);
                    mAdapter = new CarInfoAdapter(CarInfoActivity.this,list);
                    car_list.setAdapter(mAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", userId+"");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
