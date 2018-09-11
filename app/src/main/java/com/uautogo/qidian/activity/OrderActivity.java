package com.uautogo.qidian.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pingplusplus.android.Pingpp;
import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.QidianRequestApi;
import com.uautogo.qidian.adapter.OrderAdapter;
import com.uautogo.qidian.model.OrderRespons;
import com.uautogo.qidian.utils.LinearDivider;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的订单
 */

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recycleview;
    private OrderAdapter mAdapter;
    private List<OrderRespons.Order> mData;
    private ImageView back,car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        back = (ImageView) findViewById(R.id.center_top_bar_left_img);
        back.setOnClickListener(this);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        car = findViewById(R.id.car_iv);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        mData = new ArrayList<>();
        mAdapter = new OrderAdapter(this, mData);
        recycleview.setAdapter(mAdapter);
        recycleview.addItemDecoration(new LinearDivider(SystemUtil.dp2px(this,20), Color.parseColor("#f3f5f7")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                load();
            }
        }
    }

    private void load() {
        int id = Integer.parseInt(SharedPreferencesUtils.getString(OrderActivity.this, SharedPreferencesUtils.Key.KEY_USER_ID));
        QidianRequestApi.getInstance().getOrders(id, new Callback<OrderRespons>() {
            public void onResponse(Call<OrderRespons> call, Response<OrderRespons> response) {
                OrderRespons body = response.body();
                List<OrderRespons.Order> data = body.data;
                Log.e("data=============", "====" + data.toString());
                if(data.size()==0){
                    car.setVisibility(View.VISIBLE);
                    recycleview.setVisibility(View.GONE);
                }else{
                    car.setVisibility(View.GONE);
                    recycleview.setVisibility(View.VISIBLE);
                    mData.clear();
                    mData.addAll(data);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<OrderRespons> call, Throwable t) {
                Log.e("error============", "====" + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_top_bar_left_img:
                finish();
                break;

        }
    }
}
