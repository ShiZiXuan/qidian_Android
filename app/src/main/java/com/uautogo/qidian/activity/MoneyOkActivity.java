package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uautogo.qidian.R;

import java.util.Calendar;

public class MoneyOkActivity extends AppCompatActivity {
    private ImageView back;
    private Button back_bt;
    private TextView tv,time,problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_ok);
        initView();
        initMethod();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back_iv);
        back_bt = (Button) findViewById(R.id.but);
        tv = findViewById(R.id.makeMoney_cke);
        time = findViewById(R.id.moneyok_time);
        problem = findViewById(R.id.moneyok_problem);

        Bundle bundle = getIntent().getExtras();   //得到传过来的bundle
        String money = bundle.getString("money");

        tv.setText("本次提现车克:"+money+"cke");
        time.setText("预计到账时间:"+getTime()+"前");
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
                finish();
            }
        });
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoneyOkActivity.this,ProblemActivity.class);
                intent.putExtra("web","cke");
                startActivity(intent);
            }
        });
    }
    private String getTime(){
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH)+1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);

        String time = year+"年"+month+"月"+day+"日 "+(hour+3)+":"+minute+":"+second;
        return time;
    }
}
