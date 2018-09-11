package com.softwinner.un.tool.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.softwinner.un.tool.Constant;
import com.softwinner.un.tool.R;
import com.softwinner.un.tool.domain.IOCtrlMessage;
import com.softwinner.un.tool.util.UNIOCtrlDefs;
import com.softwinner.un.tool.util.UNTool;

/**
 * 修改密码界面
 */
public class ReviseActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout center_top_bar;
    private EditText oldpassword_et, newpassword_et, surepassword_et;
    private Button sure_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
        center_top_bar = (RelativeLayout) findViewById(R.id.center_top_bar);
        oldpassword_et = (EditText) findViewById(R.id.oldpassword_et);
        newpassword_et = (EditText) findViewById(R.id.newpassword_et);
        surepassword_et = (EditText) findViewById(R.id.surepassword_et);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        center_top_bar.setOnClickListener(this);
        oldpassword_et.setOnClickListener(this);
        newpassword_et.setOnClickListener(this);
        surepassword_et.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.center_top_bar) {
            finish();
        }
        if (i == R.id.sure_btn) {
            String savedOld = getSharedPreferences("qidian", MODE_PRIVATE).getString(Constant.WIFI_PASSWORD, "1234567890");
            Log.e("=====","====="+savedOld);
            String oldPwd = oldpassword_et.getText().toString().trim();//获取EditText的文字   trim是去除字符串前后的空格 但是不会去除中间的空格
            String newPwd = newpassword_et.getText().toString().trim();
            String surePwd = surepassword_et.getText().toString().trim();
            if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(surePwd)) {//如果三个有一个没填 那么就给予提示
                Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
                return;//如果代码执行到 return表示这个方法强行结束  就是return下面的代码都不会执行了
            } else if (oldPwd.length() < 8 || newPwd.length() < 8 || surePwd.length() < 8) {//密码长度不够
                Toast.makeText(this, "密码长度不够", Toast.LENGTH_SHORT).show();
                return;//如果代码执行到 return表示这个方法强行结束  就是return下面的代码都不会执行了
            } else if (!newPwd.equals(surePwd)) {//如果两次输入的新密码不一样  同样给提示  然后终止代码继续往下走
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;//如果代码执行到 return表示这个方法强行结束  就是return下面的代码都不会执行了
            } else if (oldPwd.equals(newPwd)) {
                Toast.makeText(this, "新密码与原密码相同", Toast.LENGTH_LONG).show();
                return;
            }

            if (!savedOld.equals(oldPwd)) {
                Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            getSharedPreferences("qidian", MODE_PRIVATE).edit()
                    .putString(Constant.WIFI_PASSWORD, newPwd).apply();//保存新密码

            //以下是修改密码的代码

            String wifiname = getSharedPreferences("qidian", MODE_PRIVATE).getString("device_wifi_name", "启点车连");
            Log.e("linjing","===="+wifiname);
            UNTool.getInstance().sendIOCtrlMsg(new IOCtrlMessage(StartActivity.unDevice.getSid(),

                    UNIOCtrlDefs.NAT_CMD_SET_WIFI_PWD,
                    UNIOCtrlDefs.AW_cdr_wifi_setting.combindContent(wifiname.getBytes(), oldPwd.getBytes(), newPwd.getBytes()),
                    UNIOCtrlDefs.AW_cdr_wifi_setting.getTotalSize()));
            setResult(RESULT_OK);
            finish();

        }
    }
}