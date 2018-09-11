package com.softwinner.un.tool.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.softwinner.un.tool.R;

import java.util.Timer;
import java.util.TimerTask;

public class BeginActivity extends QidianBaseActivity {

	private TextView versionView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin);
		versionView = (TextView) findViewById(R.id.version);
		versionView.setText(getResources().getString(R.string.welcome_tips) + getVersionName());
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gotoConnectActivity();
			}
		};
		
		timer.schedule(task, 500);//启动一个后台线程500毫秒后执行指定任务task（run方法）
	}
	
	
	private void gotoConnectActivity(){
		Intent intent = new Intent(BeginActivity.this, StartActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	private String getVersionName(){
		
           // 获取packagemanager的实例
           PackageManager packageManager = getPackageManager();
           // getPackageName()是你当前类的包名，0代表是获取版本信息
           PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(),0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (packInfo == null) {
			return  "";
		}
        String version = packInfo.versionName;
        return version;
        
	}

	
}
