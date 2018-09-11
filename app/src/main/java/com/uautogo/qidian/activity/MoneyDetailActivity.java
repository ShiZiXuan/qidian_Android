package com.uautogo.qidian.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.DetailLvAdapter;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.CostDetailBean;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MoneyDetailActivity extends AppCompatActivity {
    private ListView lv;
    private DetailLvAdapter adapter;
    private List<CostDetailBean.DataBean.ListBean> list;
    private ImageView back,iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_detail);
        initView();
        initMethod();
        initData();
    }

    private void initData() {
        String url = ConfigUrl.URL_COSTDETAIL;
        RequestParams params = new RequestParams(url);
        int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
        params.addParameter("userId", userId);
        params.addParameter("type", -1);
        params.addParameter("trsDate", -1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("账单明细---->",s);
                CostDetailBean bean = new Gson().fromJson(s,CostDetailBean.class);
                list = bean.getData().getList();
                if(list.size()==0){
                    iv.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.GONE);
                }else{
                    iv.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    adapter  = new DetailLvAdapter(list,MoneyDetailActivity.this);
                    lv.setAdapter(adapter);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initMethod() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.detail_back);
        lv = (ListView) findViewById(R.id.detail_lv);
        iv = findViewById(R.id.money_iv);
        list = new ArrayList<>();
    }
}
