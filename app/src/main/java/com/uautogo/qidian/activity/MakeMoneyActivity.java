package com.uautogo.qidian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jungly.gridpasswordview.GridPasswordView;
import com.uautogo.qidian.R;
import com.uautogo.qidian.model.CarMoneyBean;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * 车克提现页面
 */

public class MakeMoneyActivity extends AppCompatActivity {
    private EditText money_et, moneyAddress;
    private TextView makeMoney_all;
    private PopupWindow mPopupWindow;
    private TextView makeSecret;
    private GridPasswordView gpvNormalTwice;
    private Button makeMoney;
    private ImageView back;
    private TextView cke;
    private LinearLayout ll;
    private int flag;//判断用户是否设置过密码:0没有设置,1设置过
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_money);
        initView();
        initMethod();
        x.Ext.init(getApplication());//Xutils初始化
    }

    private void initMethod() {
        makeMoney_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopAllMoney();


            }
        });

        makeSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakeMoneyActivity.this, MakeSecretActivity.class);
                intent.putExtra("secretpage","makemoney");
                startActivity(intent);
                finish();
            }
        });
        makeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {//用户没有设置密码
                    showSetPassPop();
                } else if (TextUtils.isEmpty(moneyAddress.getText())) {
                    //输入框获取焦点
                    moneyAddress.setFocusable(true);
                    moneyAddress.setFocusableInTouchMode(true);
                    moneyAddress.requestFocus();

                    //拉起键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(moneyAddress,0);
                }else if(TextUtils.isEmpty(money_et.getText())){
                    money_et.setFocusable(true);
                    money_et.setFocusableInTouchMode(true);
                    money_et.requestFocus();

                    //拉起键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(money_et,0);
                }else if(!moneyAddress.getText().toString().startsWith("0x")||moneyAddress.getText().toString().length()!=42){
                    Toast.makeText(MakeMoneyActivity.this,"钱包地址不正确!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(Double.parseDouble(money)<100){
                        Toast.makeText(MakeMoneyActivity.this,"小于100CKE不能体现哦",Toast.LENGTH_SHORT).show();
                    }else{
                        showPop();
                    }
                }
            }
        });
        money_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //输入框获取焦点
                moneyAddress.setFocusable(true);
                moneyAddress.setFocusableInTouchMode(true);
                moneyAddress.requestFocus();
                //拉起键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(moneyAddress,0);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showPopAllMoney() {
        View view1 = View.inflate(MakeMoneyActivity.this, R.layout.pop_allmoney, null);

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
        final TextView money1 = (TextView) view1.findViewById(R.id.pop_makeMoney);
        TextView cancel = (TextView) view1.findViewById(R.id.pop_cancelMoney);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        money1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                double money2 = Double.parseDouble(money);
                if(money2<100){
                    Toast.makeText(MakeMoneyActivity.this,"小于100CKE不能体现哦",Toast.LENGTH_SHORT).show();
                } else {
                    money_et.setText(money2+"");
                }
            }
        });
    }

    private void showPop() {
//输入车克密码框
        View view1 = View.inflate(MakeMoneyActivity.this, R.layout.input_secret_dialog, null);
        //创建popupwindow为全屏
        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);

        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(getRootView(this), Gravity.CENTER,0,0);
        gpvNormalTwice = (GridPasswordView) view1.findViewById(R.id.pswView);
        TextView forget = (TextView) view1.findViewById(R.id.forgetPassword);
        forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forget.getPaint().setAntiAlias(true);//抗锯齿

        ImageView cancel = (ImageView) view1.findViewById(R.id.pass_cancel);
        TextView money = (TextView) view1.findViewById(R.id.pop_money);
        money.setText(money_et.getText());
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakeMoneyActivity.this, RemakeSecretActivity.class);
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
//输入密码监听
        gpvNormalTwice.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length() == 6) {
                    mPopupWindow.dismiss();
                    String url = ConfigUrl.URL_MAKEMONEY;
                    RequestParams params = new RequestParams(url);
                    int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
                    params.addParameter("userId", userId);
                    params.addParameter("address", moneyAddress.getText() + "");
                    params.addParameter("amount", Double.parseDouble(money_et.getText() + ""));
                    params.addParameter("password", psw);
                    x.http().post(params, new Callback.CommonCallback<String>() {

                        @Override
                        public void onSuccess(String s) {
                            Log.e("全部提现----------->", s);
                            CarMoneyBean bean = new Gson().fromJson(s, CarMoneyBean.class);
                            String msg = bean.getMsg();
                            int code = Integer.parseInt(bean.getCode());

                            if (code == 1) {
                                Toast.makeText(MakeMoneyActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else if (code == 0) {

                                Toast.makeText(MakeMoneyActivity.this, "2~3小时内到账,请耐心等待", Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtils.putString(MakeMoneyActivity.this,"moneyAddress", moneyAddress.getText()+"");

                                Intent intent = new Intent(MakeMoneyActivity.this, MoneyOkActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("money", money_et.getText()+"");
                                intent.putExtras(bundle);
                                startActivity(intent);
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

            }

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String secret = getIntent().getStringExtra("secret");
        if(!TextUtils.isEmpty(secret)){
            flag=1;
        }
        if (flag == 1) {
            ll.setVisibility(View.GONE);
        } else {
            ll.setVisibility(View.VISIBLE);
        }

    }

    private void initView() {
        ll = findViewById(R.id.setPass);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        if (flag == 1) {
            ll.setVisibility(View.GONE);
        } else {
            ll.setVisibility(View.VISIBLE);
        }
        cke = (TextView) findViewById(R.id.cke_makeMoney);
        money_et = (EditText) findViewById(R.id.money_et);
        money_et.setInputType(InputType.TYPE_CLASS_NUMBER);

        makeMoney_all = (TextView) findViewById(R.id.makeMoney_all);
        makeMoney_all.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        makeMoney_all.getPaint().setAntiAlias(true);//抗锯齿
        makeSecret = (TextView) findViewById(R.id.makeSecret);
        makeMoney = (Button) findViewById(R.id.makeMoney_ok);
        back = (ImageView) findViewById(R.id.makeMoney_back);
        moneyAddress = (EditText) findViewById(R.id.moneyAddress);

        String moneyAddress1 = SharedPreferencesUtils.getString(MakeMoneyActivity.this, "moneyAddress");
        money = SharedPreferencesUtils.getString(MakeMoneyActivity.this,"ckemoney");
        Log.e("money----->",SharedPreferencesUtils.getString(this,"ckemoney"));
        if(("0.0").equals(money)){
            cke.setText("可提现车克:0CKE");
        }else{
            cke.setText("可提现车克:" + money + "CKE");
        }
        if(!TextUtils.isEmpty(moneyAddress1)){

            moneyAddress.setText(moneyAddress1);
        }
        this.moneyAddress.setCursorVisible(true);
        this.moneyAddress.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }

    private void showSetPassPop() {
        //设置密码pop
        View view1 = View.inflate(MakeMoneyActivity.this, R.layout.activity_password_img, null);

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
                Intent intent = new Intent(MakeMoneyActivity.this, MakeSecretActivity.class);
                intent.putExtra("secretpage","makemoney");
                startActivity(intent);
                mPopupWindow.dismiss();
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
        mPopupWindow = null;
    }
    private static View getRootView(Activity context)
    {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
    }
