package com.uautogo.qidian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.AllMsgActivity;
import com.uautogo.qidian.activity.CarMoneyActivity;
import com.uautogo.qidian.activity.ETCListActivity;
import com.uautogo.qidian.activity.FuelcardActivity;
import com.uautogo.qidian.adapter.HomeGvAdapter;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.EtcInfoResponse;
import com.uautogo.qidian.model.HomeGvBean;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.view.FiveTextView;
import com.uautogo.qidian.view.MyGridview;
import com.uautogo.qidian.widget.GlideImageLoader;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends BaseFragment{
    private View rootView;
    private EtcInfoResponse.Data data;
    private int etcCode = 400;
    private MyGridview gv;
    private HomeGvAdapter adapter;
    private List<HomeGvBean> list = new ArrayList<>();
    private String[] nameArr = new String[]{"油卡充值","ETC办理","ETC账单","我的车克"};
    private int[] ivArr = new int[]{R.drawable.home_fuelcard,R.drawable.home_etc,R.drawable.home_etclist,R.drawable.home_cke};
    private FiveTextView gonggao;
    private List<Integer>images = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initView();
        initMethod();
        return rootView;
    }
    private void initMethod() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if(i==0){
                    //加油卡
                    intent = new Intent(getActivity(), FuelcardActivity.class);
                    startActivity(intent);
                }else if(i==1){
                    //智慧ETC
                    intent = new Intent(getActivity(), AllMsgActivity.class);
                    intent.putExtra("etcCode",etcCode+"");
                    startActivity(intent);
                }else if(i==2){
                    intent = new Intent(getActivity(), ETCListActivity.class);
                    intent.putExtra("etcCode",etcCode+"");
                    startActivity(intent);
                }else{
                    intent = new Intent(getActivity(), CarMoneyActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    private void initView() {

        images.add(R.drawable.home_img6);
        images.add(R.drawable.home_img2);
        images.add(R.drawable.home_img1);

        gonggao = rootView.findViewById(R.id.gonggao);
        gonggao.initScrollTextView(getActivity().getWindowManager(), "智慧ETC 先通行,后付款,享受专属优惠!", 3);//scoll_content为滚动的内容
        //1为滚动的速度，越大滚动越快
        gonggao.setText("");
        gonggao.starScroll();
        Banner banner = rootView.findViewById(R.id.banner);

        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        gv = rootView.findViewById(R.id.home_gv);
        for(int i=0;i<4;++i){
            list.add(new HomeGvBean(nameArr[i],ivArr[i]));
        }
        adapter = new HomeGvAdapter(getActivity(),list);
        gv.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String url = ConfigUrl.URL_GETETCDETAIL;
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity().getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("首页------>",s);
                try {

                    JSONObject obj = new JSONObject(s);
                    JSONObject data = obj.getJSONObject("data");
                    etcCode = data.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
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
