package com.uautogo.qidian.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.CkePayOkActivity;
import com.uautogo.qidian.activity.RemakeSecretActivity;
import com.uautogo.qidian.adapter.EtcListAdapter;
import com.uautogo.qidian.model.AlipayResponse;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.NewETCResponse1;
import com.uautogo.qidian.model.PayResult;
import com.uautogo.qidian.model.WXResponse;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uuun on 2018/8/11.
 */

public class ETCLoadingFragment extends Fragment {
    private View view;
    private ListView mEtcList;
    private EtcListAdapter mAdapter;
    private List<NewETCResponse1.DataBean.ListBean> list = new ArrayList<>();
    //private LinearLayout ll;
    private PopupWindow mPopupWindow;
    private GridPasswordView pw;
    private String url = ConfigUrl.URL_ETCPAY;
    private LinearLayout ll;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String code = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if ("9000".equals(code)) {
                    Toast.makeText(getActivity(), "支付成功啦", Toast.LENGTH_SHORT).show();
                    mPopupWindow.dismiss();
                    mAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_etcloading, container, false);
        initView(view);
        initMethod();
        x.Ext.init(getActivity().getApplication());//Xutils初始化
        return view;
    }

    private void initMethod() {
        mEtcList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPop(i);
            }
        });
    }

    private void showPop(final int i) {
        View view1 = View.inflate(getActivity(), R.layout.pop_etc_loading, null);
        //创建popupwindow为全屏
        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        ImageView cancel = view1.findViewById(R.id.pop_etc_cancel);
        CheckBox wxPay = view1.findViewById(R.id.fuelcard_wxPay);
        CheckBox aliPay = view1.findViewById(R.id.fuelcard_alipay);
        CheckBox ckePay = view1.findViewById(R.id.fuelcard_ckePay);

        ckePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCKEPop(i);
            }
        });

        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
                RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Log.e("支付结果--->",s);
                        Gson gson = new Gson();
                        final AlipayResponse bean = gson.fromJson(s, AlipayResponse.class);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String orderInfo = bean.getData().getSign();
                                //构造支付类的对象
                                PayTask task = new PayTask(getActivity());
                                //参数1:订单信息 参数2:
                                Map<String, String> result = task.payV2(orderInfo, true);
                                //发送结果
                                Message message = handler.obtainMessage();
                                message.obj = result;
                                message.what = 0;
                                handler.sendMessageAtTime(message, 0);
                            }
                        }).start();
                        mPopupWindow.dismiss();
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
                        map.put("rid", list.get(i).getId()+"");
                        map.put("channel", "aliPay");
                        map.put("type","0");
                        map.put("openId","0");
                        return map;
                    }
                };
                mQueue.add(stringRequest);
            }
        });
        wxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
                RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("支付结果--->",s);
                        Gson gson = new Gson();
                        final WXResponse response = gson.fromJson(s, WXResponse.class);
                        final IWXAPI msgApi;
                        WXResponse.DataBean data = response.getData();
                        String appid = data.getAppid();
                        SharedPreferencesUtils.putString(getActivity(),"appId",appid);
                        msgApi = WXAPIFactory.createWXAPI(getActivity(), appid);
                        // 将该app注册到微信
                        msgApi.registerApp(appid);
                        PayReq request = new PayReq();
                        request.appId = data.getAppid();
                        request.partnerId = data.getPartnerid();
                        request.prepayId = data.getPrepayid();
                        request.packageValue = data.getPackageX();
                        request.nonceStr = data.getNoncestr();
                        request.timeStamp = data.getTimestamp();
                        request.sign = data.getSign();
                        boolean issuccess = msgApi.sendReq(request);
                        mPopupWindow.dismiss();
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
                        map.put("rid", list.get(i).getId()+"");
                        map.put("channel", "wxPay");
                        map.put("type", "0");
                        map.put("openId","0");
                        return map;
                    }
                };
                mQueue.add(stringRequest);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
    }

    private void showCKEPop(int i) {
        View view1 = View.inflate(getActivity(), R.layout.pop_fuelcard_cke, null);
        //创建popupwindow为全屏
        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        pw = view1.findViewById(R.id.pswView);
        TextView forget = view1.findViewById(R.id.forgetPassword);
        forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forget.getPaint().setAntiAlias(true);//抗锯齿

        ImageView cancel = view1.findViewById(R.id.pass_cancel);
        TextView money = view1.findViewById(R.id.pop_money);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RemakeSecretActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        onPwdChangedTest(i);
    }
    private void onPwdChangedTest(final int i) {

        pw.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(final String psw) {
                if (psw.length() == 6) {
                    mPopupWindow.dismiss();
                    final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
                    RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject obj = new JSONObject(s);
                                int code = obj.getInt("code");
                                String msg = obj.getString("msg");
                                if(code==0){
                                    Toast.makeText(getActivity(),"支付成功!",Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                    Intent intent = new Intent(getActivity(), CkePayOkActivity.class);
                                    intent.putExtra("etcList",1);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                                    mPopupWindow.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                            map.put("rid", list.get(i).getId()+"");
                            map.put("channel", "ckePay");
                            map.put("type","0");
                            map.put("payPassword",psw);
                            map.put("openId","0");
                            return map;
                        }
                    };
                    mQueue.add(stringRequest);
                }


            }

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }

    private void initView(View view) {
        mEtcList = view.findViewById(R.id.etc_list_lv);
        ll = view.findViewById(R.id.loadding_ll);
        loadData();
    }
    public void loadData() {
        //Log.e("未支付账单--->","!!!!!!!!");
        String url = ConfigUrl.URL_GETETCCOST;
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));

        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("未支付的账单--->",s);
                NewETCResponse1 bean = new Gson().fromJson(s, NewETCResponse1.class);
                list = bean.getData().getList();
                if(list.size()!=0){
                    mAdapter = new EtcListAdapter(getActivity(),list);
                    mEtcList.setAdapter(mAdapter);
                }else{
                    ll.setVisibility(View.VISIBLE);
                    mEtcList.setVisibility(View.GONE);
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
                map.put("type", "0");
                map.put("pn", "1");
                map.put("pSize", "20");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }
}
