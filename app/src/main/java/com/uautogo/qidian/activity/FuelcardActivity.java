package com.uautogo.qidian.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.FuelcardGvAdapter;
import com.uautogo.qidian.model.AlipayResponse;
import com.uautogo.qidian.model.FuelcardBean;
import com.uautogo.qidian.model.PayResult;
import com.uautogo.qidian.model.WXResponse;
import com.uautogo.qidian.service.EtcService;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.view.MyGridview;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 加油卡充值
 */

public class FuelcardActivity extends AppCompatActivity {
    private MyGridview gv;
    private FuelcardGvAdapter adapter;
    private List<FuelcardBean> list = new ArrayList<>();
    private CheckBox aliPay,wxPay,ckPay;
    private Button pay;
    private String flag="ckPay",flag1="0";
    private EditText name,fuelNum;
    private Spinner spinner;
    private static final String[] fuelType={"中石油","中石化"};
    private ArrayAdapter<String> spinnerAdapter;
    private String furlType1 = "中石油",money="100元";
    private int fuelCardType,fuelCardValue=2;//充值类型,充值金额
    private TextView payMoney;
    private PopupWindow mPopupWindow;
    private GridPasswordView pw;
    private ImageView back;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String code = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if ("9000".equals(code)) {
                    Toast.makeText(FuelcardActivity.this, "支付成功啦", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(FuelcardActivity.this,PaySuccessActivity.class);
                    startActivity(intent);
                   finish();
                }else {
                    Toast.makeText(FuelcardActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuelcard_layout);
        initView();
        initMethod();
        EtcService.isPaying = true;
        x.Ext.init(getApplication());//Xutils初始化
    }

    private void initView() {
        gv = findViewById(R.id.fuelcard_gv);
        aliPay = findViewById(R.id.fuelcard_alipay);
        wxPay = findViewById(R.id.fuelcard_wxPay);
        ckPay = findViewById(R.id.fuelcard_ckePay);
        pay = findViewById(R.id.fuelcard_pay);
        name = findViewById(R.id.fuelcard_name);
        back = findViewById(R.id.back_iv);
        fuelNum = findViewById(R.id.fuelcard_fuelNum);

        fuelNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        payMoney = findViewById(R.id.fuelcard_payMoney);

        spinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,fuelType);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        flag1 = SharedPreferencesUtils.getString(this, "secretFlag");
        Intent intent = getIntent();
        String secret = intent.getStringExtra("secret");
        if(!TextUtils.isEmpty(secret)){
            flag1 = "1";
        }

        list.add(new FuelcardBean("100元"));
        list.add(new FuelcardBean("200元"));
        list.add(new FuelcardBean("500元"));
        list.add(new FuelcardBean("1000元"));
        adapter = new FuelcardGvAdapter(list,this);
        gv.setAdapter(adapter);
        gv.setVerticalSpacing(30);
        Log.e("Fuelcard------->",gv.getChildCount()+"");

        //获取第一个子view
        gv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                Button childAt = (Button) gv.getChildAt(0);
                gv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                childAt.setBackgroundResource(R.drawable.shape);
                childAt.setTextColor(Color.parseColor("#ffffff"));
            }
        });
    }
    private void showSetPassPop() {
        View view1 = View.inflate(FuelcardActivity.this, R.layout.activity_password_img, null);

        //创建popupwindow为全屏

        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        Button toSet = view1.findViewById(R.id.pop_pw_cancel);
        ImageView cancel = view1.findViewById(R.id.setPw_iv);
        toSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FuelcardActivity.this, MakeSecretActivity.class);
                intent.putExtra("secretpage","fulecard");
                startActivity(intent);
                finish();
                mPopupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
    }


    private void initMethod() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Button but1 = (Button) adapterView.getChildAt(i);
                money = but1.getText().toString();
                if(money.equals("100元")){
                    fuelCardValue = 2;
                }else if(money.equals("200元")){
                    fuelCardValue = 3;
                }else if(money.equals("500元")){
                    fuelCardValue = 4;
                }else{
                    fuelCardValue = 5;
                }
                payMoney.setText(money);
                for(int j=0;j<list.size();++j){
                    Button but = (Button) adapterView.getChildAt(j);
                    if(j==i){
                        but.setBackgroundResource(R.drawable.shape);
                        but.setTextColor(Color.parseColor("#ffffff"));
                    }else{
                        but.setBackgroundResource(R.drawable.shape_money);
                        but.setTextColor(Color.parseColor("#000000"));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        ckPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxPay.setChecked(false);
                aliPay.setChecked(false);
                flag = "ckPay";
            }
        });
        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wxPay.setChecked(false);
                ckPay.setChecked(false);
                flag = "aliPay";
            }
        });
        wxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPay.setChecked(false);
                ckPay.setChecked(false);
                flag = "wxPay";
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  if(i==0){
                                                      fuelCardType = 2;
                                                  }else{
                                                      fuelCardType = 1;
                                                  }
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> adapterView) {

                                              }
                                          });
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Boolean check = check();
                        if(check){
                            payMethod();
                        }else{
                           return;
                        }




                    }
                });

    }
    private Boolean check(){
        if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(fuelNum.getText())){
            Toast.makeText(FuelcardActivity.this,"请将信息填写完整!",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }

    private void payMethod() {
        final int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
        String url = "https://etc.uautogo.com/api/etcbuy/payGasCard";
        RequestParams params = new RequestParams(url);
        params.addParameter("userId", userId);
        params.addParameter("name", name.getText().toString());
        params.addParameter("gasCardNum", fuelNum.getText().toString());
        params.addParameter("fuelCardType", fuelCardType);
        params.addParameter("fuelCardValue", fuelCardValue);
        params.addParameter("type", 0);
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
                            PayTask task = new PayTask(FuelcardActivity.this);
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
        } else if (flag.equals("wxPay")) {
            params.addParameter("channel", "wxPay");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.e("微信支付接口----->",s);
                    Gson gson = new Gson();
                    final WXResponse response = gson.fromJson(s, WXResponse.class);
                    final IWXAPI msgApi;
                    WXResponse.DataBean data = response.getData();
                    String appid = data.getAppid();
                    SharedPreferencesUtils.putString(FuelcardActivity.this,"appId",appid);
                    msgApi = WXAPIFactory.createWXAPI(FuelcardActivity.this, appid);
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
        }else{
            if(("0").equals(flag1)){
                //未设置密码
                showSetPassPop();
            }else{
                //设置过密码
                showPop();
            }

        }
    }
    private void showPop() {
        View view1 = View.inflate(FuelcardActivity.this, R.layout.pop_fuelcard_cke, null);

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
                Intent intent = new Intent(FuelcardActivity.this,RemakeSecretActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        onPwdChangedTest();
    }
    private void onPwdChangedTest() {

        pw.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

                if (psw.length() == 6) {
                    mPopupWindow.dismiss();
                    int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
                    String url = "https://etc.uautogo.com/api/etcbuy/payGasCard";
                    RequestParams params = new RequestParams(url);
                    params.addParameter("userId", userId);
                    params.addParameter("name", name.getText().toString());
                    params.addParameter("gasCardNum", fuelNum.getText().toString());
                    params.addParameter("fuelCardType", fuelCardType);
                    params.addParameter("fuelCardValue", fuelCardValue);
                    params.addParameter("type", 0);
                    params.addParameter("channel", "ckePay");
                    params.addParameter("payPassword", psw);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.e("车克密码支付成功--->",s);
                            Intent intent = new Intent(FuelcardActivity.this,CkePayOkActivity.class);
                            startActivity(intent);
                            finish();
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

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EtcService.isPaying = false;
    }
}
