package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.NewETCResponse;

public class PayNotificationActivity extends BaseActivity {
    private Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_notification);
        final NewETCResponse.DataBean data = (NewETCResponse.DataBean) getIntent().getSerializableExtra("data");
        pay = (Button) findViewById(R.id.pay_btn);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayNotificationActivity.this,ETCListActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
