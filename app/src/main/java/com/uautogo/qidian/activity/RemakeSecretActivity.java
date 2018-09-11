package com.uautogo.qidian.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RemakeSecretActivity extends AppCompatActivity {
    private ImageView back;
    private Button back_bt,two_code;
    private EditText phone,twoCode_et,pass1,pass2;
    private CountDownTimer timer;
    private InputMethodManager manager;
    private String phone1,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remake_secret);
        initView();
        initMethod();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        x.Ext.init(getApplication());//Xutils初始化
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.remakeSecret_back);
        back_bt = (Button) findViewById(R.id.remakeSecret_back_bt);
        two_code = (Button) findViewById(R.id.getTwoCode);
        phone = (EditText) findViewById(R.id.makeSecret_phone);
        twoCode_et = (EditText) findViewById(R.id.makeSecret_card);
        pass1 = (EditText) findViewById(R.id.makeSecret_pass);
        pass2 = (EditText) findViewById(R.id.makeSecret_pass2);

        phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        twoCode_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        pass1.setInputType(InputType.TYPE_CLASS_NUMBER);
        pass2.setInputType(InputType.TYPE_CLASS_NUMBER);
        phone1 = SharedPreferencesUtils.getString(this, SharedPreferencesUtils.Key.KEY_USER_PHONE);

        //phone1 = "15901136784";
        phone.setText(phone1);
    }

    private void initMethod() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassward();

            }
        });
        two_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSendCode();
                two_code.requestFocus();
                //MobclickAgent.onEvent(RemakeSecretActivity.this, "c_login_code_click");//友盟统计获取验证码点击的次数
            }
        });
    }
    private void clickSendCode() {

        String url = ConfigUrl.URL_PHONECODE;
        RequestParams params = new RequestParams(url);

        params.addQueryStringParameter("mobile", phone1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(RemakeSecretActivity.this,"验证码获取成功!",Toast.LENGTH_SHORT).show();
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
        startTimer();

    }
    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    two_code.setClickable(false);
                    two_code.setText("" + millisUntilFinished / 1000L + "" + getString(R.string.login_tips_resend_code));
                }

                @Override
                public void onFinish() {
                    two_code.setClickable(true);
                    two_code.setText(getString(R.string.login_tips_send_code));
                }
            };
        }

        timer.start();
    }

    private void checkPassward() {
        if(TextUtils.isEmpty(pass1.getText()+"")){
            Toast.makeText(RemakeSecretActivity.this,"请输入密码!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass2.getText()+"")){
            Toast.makeText(RemakeSecretActivity.this,"请再次输入密码!",Toast.LENGTH_SHORT).show();
        }else if(pass1.getText().length()<6){
            Toast.makeText(RemakeSecretActivity.this,"请输入6位密码!",Toast.LENGTH_SHORT).show();
        } else{

            int ps = Integer.parseInt(pass1.getText()+"");
            int ps1 = Integer.parseInt(pass2.getText()+"");
            if(ps==ps1){
                checkCode();
            }else{
                Toast.makeText(RemakeSecretActivity.this,"两次输入密码不一致!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkCode() {
        code = twoCode_et.getText().toString();
        String userId = SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID);
        String pw = pass1.getText().toString();

        String url = ConfigUrl.URL_RESETPW;
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("userId", userId);
        params.addQueryStringParameter("restPassword", pw);
        params.addQueryStringParameter("restMobile", phone1);
        params.addParameter("restVcode", code);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
               Toast.makeText(RemakeSecretActivity.this,"重置成功!",Toast.LENGTH_SHORT).show();
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
