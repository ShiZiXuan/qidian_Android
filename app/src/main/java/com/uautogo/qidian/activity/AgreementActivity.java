package com.uautogo.qidian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uautogo.qidian.R;

/**
 * ECT协议页面
 */

public class AgreementActivity extends BaseActivity implements View.OnClickListener {
    private CheckBox agreement_checkbox;
    private Button ok_btn;
    private RelativeLayout illegal_top_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        init();
    }

    private void init() {
        agreement_checkbox = (CheckBox) findViewById(R.id.agreement_checkbox);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        illegal_top_bar = (RelativeLayout) findViewById(R.id.illegal_top_bar);
        illegal_top_bar.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.illegal_top_bar:
                finish();
                break;
            case R.id.ok_btn:
                if (!agreement_checkbox.isChecked()){
                    Toast.makeText(this, "同意协议后才能继续", Toast.LENGTH_SHORT).show();
                    return;
                }
//                startActivity(new Intent(this,CardActivity.class));
                break;

        }
    }
}
