package com.uautogo.qidian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.MainActivity;
import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.QidianRequestApi;
import com.uautogo.qidian.data.ResultCallback;
import com.uautogo.qidian.fragment.Myfragment;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.LoginRespons;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import retrofit2.Call;

/**
 * Created by Jeremy on 2017/5/28.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int WX_REGISTER = 1;
    private EditText phoneEdit;
    private EditText codeEdit;
    private TextView codeBtn;
    private ImageView weixin_login_tv;
    private Button loginBtn;
    private CountDownTimer timer;
    private InputMethodManager manager;
    private IWXAPI api;
    private boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        api = WXAPIFactory.createWXAPI(this, "wxf9a97962ab078a3a",true);
        //将应用的appid注册到微信
        api.registerApp("wxf9a97962ab078a3a");
        x.Ext.init(getApplication());//Xutils初始化
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {
        phoneEdit = (EditText) findViewById(R.id.login_phone_edit);
        codeEdit = (EditText) findViewById(R.id.login_code);
        codeBtn = (TextView) findViewById(R.id.login_send_code);
        loginBtn = (Button) findViewById(R.id.login_login_btn);
        weixin_login_tv = (ImageView) findViewById(R.id.weixin_login_tv);
        weixin_login_tv.setOnClickListener(this);
        codeBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    codeBtn.setClickable(false);
                    codeBtn.setText("" + millisUntilFinished / 1000L + "" + getString(R.string.login_tips_resend_code));
                }
                @Override
                public void onFinish() {
                    codeBtn.setClickable(true);
                    codeBtn.setText(getString(R.string.login_tips_send_code));
                }
            };
        }
        timer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_send_code: {
                clickSendCode();
                codeEdit.requestFocus();
                MobclickAgent.onEvent(LoginActivity.this, "c_login_code_click");//友盟统计获取验证码点击的次数
                break;
            }

            case R.id.login_login_btn: {
                if(TextUtils.isEmpty(codeEdit.getText())){
                    Toast.makeText(LoginActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }else{
                    clickLogin();
                }

                break;
            }

            case R.id.top_bar_left_img: {
                finish();
                break;
            }
            case R.id.weixin_login_tv: {
                loginByThirdPlatform();
                break;
            }
            default:
                break;
        }
    }
    private void clickLogin() {
        final String phoneNumber = phoneEdit.getText().toString().trim();
        String codeNumber = codeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.show(LoginActivity.this, R.string.login_tips_empty_phone_number);
            return;
        }
        if (phoneNumber.length() != 11) {
            ToastUtil.show(LoginActivity.this, R.string.login_tips_wrong_phone_number);
            return;
        }
        if (TextUtils.isEmpty(codeNumber)) {
            return;
        }
        if (codeNumber.length() != 6) {
            return;
        }
        MobclickAgent.onEvent(LoginActivity.this, "c_login_click");//友盟统计登录按钮点击次数
        QidianRequestApi.getInstance().doLogin(phoneNumber, codeNumber, new ResultCallback<LoginRespons>() {
            @Override
            protected void onSuccess(LoginRespons data) {
                if (data == null) {
                    return;
                }
                if (data.code == 0 && data.data != null) {
                    final int userId = data.data.user.id;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MobclickAgent.onEvent(LoginActivity.this, "c_login_suc");//友盟统计登录成功次数
                            SharedPreferencesUtils.putString(LoginActivity.this, SharedPreferencesUtils.Key.KEY_USER_ID, "" + userId);
                            SharedPreferencesUtils.putString(LoginActivity.this, SharedPreferencesUtils.Key.KEY_USER_PHONE, "" + phoneNumber);

                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(loginBtn.getApplicationWindowToken(), 0);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            finish();
                        }
                    });
                }
                if (data.code == 1) {
                    Toast.makeText(LoginActivity.this, data.msg, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginRespons> call, Throwable t) {
                super.onFailure(call, t);
                MobclickAgent.onEvent(LoginActivity.this, "c_login_fail");//友盟统计登录失败次数
            }
        });
    }
    private void clickSendCode() {
        String phoneNumber = phoneEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.show(LoginActivity.this, R.string.login_tips_empty_phone_number);
            return;
        }
        if (phoneNumber.length() != 11) {
            ToastUtil.show(LoginActivity.this, R.string.login_tips_wrong_phone_number);
            return;
        }
        if(flag){//获取二维码超过三次
            Toast.makeText(LoginActivity.this,"今日验证码发送量已达上线,请明日再来",Toast.LENGTH_SHORT).show();
        }else{
            startTimer();
            String url = ConfigUrl.URL_GETCODE;
            RequestParams params = new RequestParams(url);
            params.addParameter("mobile",phoneNumber);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.e("获取二维码------>",s);
                    try {
                        JSONObject obj = new JSONObject(s);
                        int code = obj.getInt("code");
                        String msg = obj.getString("msg");
                        if(code==0){
                            Toast.makeText(LoginActivity.this,"获取成功!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                            flag = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
//        QidianRequestApi.getInstance().requestCode(phoneNumber, new ResultCallback<CodeRespons>() {
//            @Override
//            protected void onSuccess(CodeRespons data) {
//                Log.e("获取二维码---->",data.toString());
//                if (data.code == 0) {
//                    MobclickAgent.onEvent(LoginActivity.this, "c_login_code_suc");//友盟统计获取验证码成功次数
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CodeRespons> call, Throwable t) {
//                super.onFailure(call, t);
//                MobclickAgent.onEvent(LoginActivity.this, "c_login_code_fail");//友盟统计获取验证码失败次数
//            }
//        });

    }

    private void loginByThirdPlatform() {
        SharedPreferencesUtils.putString(LoginActivity.this,"isWxLogin","yes");
        Bundle bundle = new Bundle();

        bundle.putString("wxLogin", "ok");
        Myfragment fragment = new Myfragment();
        fragment.setArguments(bundle);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
        req.state = "wechat_sdk_微信登录";
        api.sendReq(req);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        if (requestCode == WX_REGISTER && resultCode == RESULT_OK)
//            finish();
        if(resultCode == 0){
           Toast.makeText(LoginActivity.this,"登陆成功!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
