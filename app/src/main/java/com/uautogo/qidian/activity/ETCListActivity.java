package com.uautogo.qidian.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.AccountVpAdapter;
import com.uautogo.qidian.fragment.ETCFinishFragment;
import com.uautogo.qidian.fragment.ETCLoadingFragment;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.NewETCResponse2;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Linjing on 2017/11/7.
 * ETC信息展示界面(列表)
 */

public class ETCListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back_iv;
    private TextView noNum,noMoney,toWeb,state;
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<String> listName;
    private List<Fragment> list;
    private AccountVpAdapter adapter;
    private String type = "0";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etclist);
        initview();
        initMethod();
        initTab();

    }

    private void initTab() {//tablayout点击事件!
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        //这里就可以根据业务需求处理点击事件了。
                        if(position==0) {
                            type="0";
                            loadData();
                        }else{
                            type="1";
                            loadData();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void initMethod() {
        toWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ETCListActivity.this,ProblemActivity.class);
                intent.putExtra("web","etc");
                startActivity(intent);
            }
        });
    }

    public void initview() {
        LinearLayout ll = findViewById(R.id.etclist_ll);
        ImageView iv = findViewById(R.id.etclist_iv);
        back_iv = (ImageView) findViewById(R.id.back_iv);
        back_iv.setOnClickListener(this);
        tabLayout = findViewById(R.id.etclist_tab);
        vp = findViewById(R.id.etclist_vp);
        state = findViewById(R.id.etc_list_state);
        noNum = findViewById(R.id.etclist_num);
        noMoney = findViewById(R.id.ectlist_noMoney);
        toWeb = findViewById(R.id.etc_toWeb);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setMinimumWidth(100);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(Color.BLACK, Color.parseColor("#ffffff"));
        listName = new ArrayList<>();
        listName.add("待支付");
        listName.add("已完成");
        list = new ArrayList<>();
        list.add(new ETCLoadingFragment());
        list.add(new ETCFinishFragment());
        adapter = new AccountVpAdapter(getSupportFragmentManager(), list, listName);
        vp.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp);
        //checkEtc();
        Intent intent = getIntent();
        String etcCode = intent.getStringExtra("etcCode");
        if("400".equals(etcCode)){//未办理状态
            ll.setVisibility(View.GONE);
            iv.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);
        }
        loadData();
    }

    public void loadData() {
        String url = ConfigUrl.URL_GETETCCOST;
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(this, SharedPreferencesUtils.Key.KEY_USER_ID));
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("ETC账单列表-->",s);
                NewETCResponse2 bean = new Gson().fromJson(s, NewETCResponse2.class);
                noNum.setText(bean.getData().getCountNum()+"");
                if("0".equals(type)){//待支付
                    state.setText("待支付");
                    noMoney.setText("本月未支付订单共计"+bean.getData().getSumAmount()+"元");
                }else{//已完成
                    state.setText("支付完成");
                    noMoney.setText("本月已支付订单共计"+bean.getData().getSumAmount()+"元");
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
                map.put("UserId", userId+"");
                map.put("type", type);
                map.put("pn", "1");
                map.put("pSize", "20");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }


    private String dealTime(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        return str.substring(0, 4) + "年" + str.substring(4, 6) + "月" + str.substring(6, 8) + "日 " +
                str.substring(8, 10) + ":" + str.substring(10, 12) + ":" + str.substring(12);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }


}