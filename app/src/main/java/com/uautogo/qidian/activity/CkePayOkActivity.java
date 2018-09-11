package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.uautogo.qidian.R;

public class CkePayOkActivity extends AppCompatActivity {
   private Button but;
    private ImageView back;
    private int code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cke_pay_ok);
        initView();
        initMethod();
    }

    private void initView() {
        but = findViewById(R.id.cke_ok_toNext);
        back = findViewById(R.id.back_iv);
        Intent intent = getIntent();
        code = intent.getIntExtra("etcList",0);

    }

    private void initMethod() {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code==1){
                    Intent intent = new Intent(CkePayOkActivity.this,ETCListActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
