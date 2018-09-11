package com.uautogo.qidian.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.AllMsgActivity;
import com.uautogo.qidian.model.AlipayResponse;
import com.uautogo.qidian.model.PayResult;
import com.uautogo.qidian.model.WXResponse;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by linjing on 2018/4/23.
 * <p>
 * 设备付款
 */

public class PayFragment extends Fragment {
    private static final int SDK_PAY_FLAG = 1;

    private CheckBox aliPay,wxPay,ckPay;
    public boolean alreadyHad;
    public String payMethod = "wxPay";
    private InputMethodManager imm;
    CityPickerView cityPicker;
    LinearLayout choose_city_ll;
    TextView adress_tv;
    public EditText name_et, number_et, xiangxiadress_et;
    public String provinceName, cityName, districtName;
    private String flag="wxPay";
    private PopupWindow mPopupWindow;
    private GridPasswordView pw;
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

                    //getActivity().finish();
                    ((AllMsgActivity) getActivity()).goNext();
                }else {
                    Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String resultStatus = ((Map<String, String>) msg.obj).get("resultStatus");
            switch (resultStatus) {
                case "9000":
                    ((AllMsgActivity) getActivity()).goNext();
                    break;
//                case "8000":
//                    ToastUtil.show(getActivity(),"正在处理中");
//                    break;
//                case "4000":
//                    ToastUtil.show(getActivity(),"订单支付失败");
//                    break;
//                case "5000":
//                    ToastUtil.show(getActivity(),"重复请求");
//                    break;
//                case "6001":
//                    ToastUtil.show(getActivity(),"已取消支付");
//                    break;
//                case "6002":
//                    ToastUtil.show(getActivity(),"网络连接出错");
//                    break;
//                case "6004":
//                    ToastUtil.show(getActivity(),"正在处理中");
//                    break;
//                default:
//                    ToastUtil.show(getActivity(),"支付失败");
//                    break;
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pay_msg, container, false);
        initView(view);
        initMethod();
        return view;
    }

    private void initMethod() {

        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxPay.setChecked(false);

                flag = "aliPay";
            }
        });
        wxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPay.setChecked(false);

                flag = "wxPay";
            }
        });

    }

    private void initView(View view) {
        aliPay = view.findViewById(R.id.fuelcard_alipay);
        wxPay = view.findViewById(R.id.fuelcard_wxPay);
        adress_tv = view.findViewById(R.id.adress_tv);
        choose_city_ll = (LinearLayout) view.findViewById(R.id.choose_city_ll);
        name_et = (EditText) view.findViewById(R.id.name_et);
        number_et = (EditText) view.findViewById(R.id.number_et);
        xiangxiadress_et = (EditText) view.findViewById(R.id.xiangxiadress_et);

//        choose_city_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cityPicker.show();
//                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
//            }
//        });
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        cityPicker = new CityPickerView.Builder(getActivity())
                .textSize(20)
//                .title("地址选择")
                .title(null)
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#0000FF")
                .backgroundPop(0xa0000000)
                .confirTextColor("#0000FF")
                .cancelTextColor("#0000FF")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                String info = province.getName() + " " + city.getName() + " " + district.getName();
                adress_tv.setText(info);
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                Log.e("===" + province.getName() + "===" + city.getName() + "===", "===" + district.getName() + "===");
                provinceName = province.getName();
                cityName = city.getName();
                districtName = district.getName();
            }

            @Override
            public void onCancel() {

            }
        });

        adress_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityPicker.show();
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        //already_had_cb = (CheckBox) view.findViewById(already_had_cb);

    }
    public void payMethod() {

        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
        int carId = SharedPreferencesUtils.getInt(getActivity(), "carId");
        String url = "https://etc.uautogo.com/api/etcbuy/payEquipment";
        RequestParams params = new RequestParams(url);
        params.addParameter("uid", userId);
        params.addParameter("carId", carId);
        params.addParameter("receiverName",name_et.getText().toString());
        params.addParameter("receiverMobile",number_et.getText().toString());
        params.addParameter("receiverProvince",provinceName);
        params.addParameter("receiverCity",cityName);
        params.addParameter("receiverDistrict",districtName);
        params.addParameter("receiverAddress",xiangxiadress_et.getText().toString());
        params.addParameter("type", 0);
        params.addParameter("openId", "0");
        if (flag.equals("aliPay")) {
            //支付宝支付
            params.addParameter("channel", "aliPay");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {

                    Log.e("支付宝支付接口----->",s);
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
        } else{
            params.addParameter("channel", "wxPay");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.e("微信支付接口----->",s);
                    Gson gson = new Gson();
                    WXResponse bean = gson.fromJson(s, WXResponse.class);
                    final IWXAPI msgApi;
                    WXResponse.DataBean data = bean.getData();
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
                    Log.e("wx请求成功了吗---->",issuccess+"");
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
    }

//    public void pay() {
//        if (alreadyHad) {
//            ((AllMsgActivity) getActivity()).goNext();
//        } else {
//            Map<String, String> map = new HashMap<>();
//            map.put("uid", SharedPreferencesUtils.getString(getActivity().getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
//            map.put("channel", payMethod);
//            map.put("num", "1");
//            map.put("receiverName", AllMsgActivity.receiverName);
//            map.put("receiverMobile", AllMsgActivity.receiverMobile);
//            map.put("receiverProvince", AllMsgActivity.receiverProvince);
//            map.put("receiverCity", AllMsgActivity.receiverCity);
//            map.put("receiverDistrict", AllMsgActivity.receiverDistrict);
//            map.put("receiverAddress", AllMsgActivity.receiverAddress);
//            map.put("type", "0");
//            map.put("openId", "1");
//            if (wxpay_cb.isChecked()) {
//                WangZKRequestApi.getInstance().buyEtcByWX(map, new Callback<WXResponse>() {
//                    @Override
//                    public void onResponse(Call<WXResponse> call, Response<WXResponse> response) {
//                        Constant.isBuyingEtc = true;
//                        WXResponse resp = response.body();
//                        WXResponse.DataBean data = resp.getData();
//                        PayReq request = new PayReq();
//                        request.appId = data.getAppid();
//                        request.partnerId = data.getPartnerid();
//                        request.prepayId = data.getPrepayid();
//                        request.packageValue = data.get_package();
//                        request.nonceStr = data.getNoncestr();
//                        request.timeStamp = data.getTimestamp();
//                        request.sign = data.getSign();
//                        MyApplication.getInstance().getWXApi().sendReq(request);
//                    }
//
//                    @Override
//                    public void onFailure(Call<WXResponse> call, Throwable t) {
//
//                    }
//                });
//            } else {
//                WangZKRequestApi.getInstance().buyEtcByAli(map, new Callback<AlipayResponse>() {
//                    @Override
//                    public void onResponse(Call<AlipayResponse> call, final Response<AlipayResponse> response) {
//                        Runnable payRunnable = new Runnable() {
//                            @Override
//                            public void run() {
//                                PayTask alipay = new PayTask(getActivity());
//                                Map<String, String> result = alipay.payV2(response.body().getData().getSign(), true);
//                                Message msg = new Message();
//                                msg.what = SDK_PAY_FLAG;
//                                msg.obj = result;
//                                mHandler.sendMessage(msg);
//                            }
//                        };
//                        // 必须异步调用
//                        Thread payThread = new Thread(payRunnable);
//                        payThread.start();
//                    }
//
//                    @Override
//                    public void onFailure(Call<AlipayResponse> call, Throwable t) {
//
//                    }
//                });
//            }
//        }
//    }
    public void saveAddress() {
        String address = xiangxiadress_et.getText().toString().trim();
        String name = name_et.getText().toString().trim();
        String phone = number_et.getText().toString().trim();
        if (TextUtils.isEmpty(provinceName) ||
                TextUtils.isEmpty(cityName) ||
                TextUtils.isEmpty(districtName) ||
                TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        AllMsgActivity.receiverName = name;
        AllMsgActivity.receiverMobile = phone;
        AllMsgActivity.receiverAddress = address;
        AllMsgActivity.receiverProvince = provinceName;
        AllMsgActivity.receiverCity = cityName;
        AllMsgActivity.receiverDistrict = districtName;
        ((AllMsgActivity) getActivity()).goNext();
    }
}
