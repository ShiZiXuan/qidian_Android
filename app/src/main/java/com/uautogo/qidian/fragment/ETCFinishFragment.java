package com.uautogo.qidian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.uautogo.qidian.adapter.EtcListAdapter1;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.NewETCResponse2;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import org.xutils.x;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uuun on 2018/8/11.
 */

public class ETCFinishFragment extends Fragment {
    private View view;
    private ListView mEtcList;
    private EtcListAdapter1 mAdapter;
    private LinearLayout ll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_etcfinish,container,false);
        initView(view);
        initMethod();
        x.Ext.init(getActivity().getApplication());//Xutils初始化
        return view;
    }

    private void initMethod() {
//        mEtcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), ETCMapMsgActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void initView(View view) {
        mEtcList = view.findViewById(R.id.etc_list_lv1);
        ll = view.findViewById(R.id.finish_ll);
        loadData();
    }

    public void loadData() {

        String url = ConfigUrl.URL_GETETCCOST;
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                NewETCResponse2 bean = new Gson().fromJson(s, NewETCResponse2.class);
                List<NewETCResponse2.DataBean.ListBean> list = bean.getData().getList();
                if(list.size()!=0){
                    mAdapter = new EtcListAdapter1(getActivity(),list);
                    mEtcList.setAdapter(mAdapter);
                }else{
                    ll.setVisibility(View.VISIBLE);
                    mEtcList.setVisibility(View.GONE);
                }
                mAdapter = new EtcListAdapter1(getActivity(),list);
                mEtcList.setAdapter(mAdapter);
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
                map.put("type", "1");
                map.put("pn", "1");
                map.put("pSize", "20");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
