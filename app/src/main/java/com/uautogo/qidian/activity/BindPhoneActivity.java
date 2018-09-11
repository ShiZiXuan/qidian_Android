package com.uautogo.qidian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uautogo.qidian.MainActivity;
import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.model.WXRegisterResponse;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用户手机号注册
 */

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {
    private EditText login_phone_edit;
    private EditText login_code;
    private TextView login_send_code;
    private Button login_login_btn;
    private CountDownTimer timer;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        uid = getIntent().getIntExtra("uid", 0) + "";
        Log.e("=========", uid);
        initview();
    }

    private void initview() {
        login_phone_edit = (EditText) findViewById(R.id.login_phone_edit);
        login_code = (EditText) findViewById(R.id.login_code);
        login_send_code = (TextView) findViewById(R.id.login_send_code);
        login_login_btn = (Button) findViewById(R.id.login_login_btn);
        login_code.setOnClickListener(this);
        login_send_code.setOnClickListener(this);
        login_login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn: {
                String phoneNumber = login_phone_edit.getText().toString().trim();//trim去掉字符串中前后的空白
                String vcode = login_code.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtil.show(BindPhoneActivity.this, R.string.login_tips_empty_phone_number);
                    return;
                }
                if (phoneNumber.length() != 11) {
                    ToastUtil.show(BindPhoneActivity.this, R.string.login_tips_wrong_phone_number);
                    return;
                }
                if (TextUtils.isEmpty(vcode)) {
                    return;
                }
                if (vcode.length() != 6) {
                    return;
                }
                WangZKRequestApi.getInstance().wxRegister(uid, phoneNumber, vcode, new Callback<WXRegisterResponse>() {
                    @Override
                    public void onResponse(Call<WXRegisterResponse> call, Response<WXRegisterResponse> response) {
                        WXRegisterResponse resp = response.body();
                        if (resp.getCode() == 0) {
                            final int userId = resp.getData().getUser().getId();
                            final String phoneNumber = resp.getData().getUser().getMobile();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MobclickAgent.onEvent(BindPhoneActivity.this, "c_login_suc");//友盟统计登录成功次数
                                    SharedPreferencesUtils.putString(BindPhoneActivity.this, SharedPreferencesUtils.Key.KEY_USER_ID, "" + userId);
                                    SharedPreferencesUtils.putString(BindPhoneActivity.this, SharedPreferencesUtils.Key.KEY_USER_PHONE, "" + phoneNumber);

                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(login_login_btn.getApplicationWindowToken(), 0);

                                    Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
                                    BindPhoneActivity.this.startActivity(intent);
                                    finish();
                                    setResult(RESULT_OK);
                                }
                            });
                        } else {
                            ToastUtil.show(BindPhoneActivity.this, resp.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<WXRegisterResponse> call, Throwable t) {

                    }
                });
                break;
            }
            case R.id.login_send_code:
                String phoneNumber = login_phone_edit.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtil.show(BindPhoneActivity.this, R.string.login_tips_empty_phone_number);
                    return;
                }
                if (phoneNumber.length() != 11) {
                    ToastUtil.show(BindPhoneActivity.this, R.string.login_tips_wrong_phone_number);
                    return;
                }
                startTimer();
                WangZKRequestApi.getInstance().getVcode(phoneNumber);
                break;
        }
    }

    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    login_send_code.setClickable(false);
                    login_send_code.setText("" + millisUntilFinished / 1000L + "" + getString(R.string.login_tips_resend_code));
                }

                @Override
                public void onFinish() {
                    login_send_code.setClickable(true);
                    login_send_code.setText(getString(R.string.login_tips_send_code));
                }
            };
        }
        timer.start();
    }

}
