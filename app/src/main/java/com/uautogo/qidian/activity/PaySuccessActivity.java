package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uautogo.qidian.R;

/**
 * 支付宝微信支付成功页面
 */

public class PaySuccessActivity extends AppCompatActivity {
    private TextView toDetail;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();
        initMethod();
    }

    private void initMethod() {
        toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaySuccessActivity.this,MoneyDetailActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toDetail = findViewById(R.id.toCKEDetail);
        back = findViewById(R.id.pay_cancel_iv);
    }
}
