package com.uautogo.qidian.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
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

public class MakeSecretActivity extends AppCompatActivity{
    private EditText makePassword,password2;
    private ImageView back;
    private Button makeSecret;
    private int flag=0;//0是提现页面 1是加油卡页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_secret);
        initView();
        initMethod();
        x.Ext.init(getApplication());//Xutils初始化


    }

    private void initView() {

        makePassword = (EditText) findViewById(R.id.makeSecret_pass);
        makePassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        password2 = (EditText) findViewById(R.id.makeSecret_pass2);
        password2.setInputType(InputType.TYPE_CLASS_NUMBER);
        back = (ImageView) findViewById(R.id.secret_back);
        makeSecret = (Button) findViewById(R.id.makeSecret_ok);
        Intent intent = getIntent();
        String secretpage = intent.getStringExtra("secretpage");
        if(("makemoney").equals(secretpage)){
            flag=0;
        }else{
            flag=1;
        }
    }

    private void initMethod() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        makeSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(makePassword.getText()+"")){
                    Toast.makeText(MakeSecretActivity.this,"请输入密码!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password2.getText()+"")){
                    Toast.makeText(MakeSecretActivity.this,"请再次输入密码!",Toast.LENGTH_SHORT).show();
                }else if(makePassword.getText().length()<6){
                    Toast.makeText(MakeSecretActivity.this,"请输入6位密码!",Toast.LENGTH_SHORT).show();
                } else{

                    int ps = Integer.parseInt(makePassword.getText()+"");
                    int ps1 = Integer.parseInt(password2.getText()+"");
                    if(ps==ps1){
                        commit();
                    }else{
                        Toast.makeText(MakeSecretActivity.this,"两次输入密码不一致!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void commit() {
        String url = ConfigUrl.URL_SETPASSWORD;
        RequestParams params = new RequestParams(url);
        int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
        params.addParameter("userId", userId);
        params.addParameter("password", Integer.parseInt(makePassword.getText()+""));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(MakeSecretActivity.this,"设置成功!",Toast.LENGTH_SHORT).show();
                Intent intent= null;
                if(flag==0){
                    intent = new Intent(MakeSecretActivity.this,MakeMoneyActivity.class);
                    intent.putExtra("secret","yes");
                }else{
                    intent = new Intent(MakeSecretActivity.this,FuelcardActivity.class);
                    intent.putExtra("secret","yes");
                }

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
