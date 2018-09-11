package com.softwinner.un.tool.activity;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.softwinner.un.tool.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ConnectFragmentActivity extends FragmentActivity implements OnClickListener{

	private ViewPager pager; //页面切换器
	private ArrayList<Fragment> fragmentsList;//fragment列表
	private ConnectWiFiFragment one;
	private ConnectAPFragment   two;
	private UNFragmentPagerAdapter fragmentPagerAdapter;
	
	private Button  btnWiFi;
	private Button  btnAP;
	
	public static int SCREEN_WIDTH = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		
		WindowManager wm = this.getWindowManager();
		SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
		
		closeAp();
		
		//初始化Fragment切换器
        pager = (ViewPager)findViewById(R.id.viewpager);
        one   = new ConnectWiFiFragment();
        two   = new ConnectAPFragment();
        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(one);
        fragmentsList.add(two);
        pager.setOffscreenPageLimit(2);
        fragmentPagerAdapter = new UNFragmentPagerAdapter(getSupportFragmentManager(),fragmentsList);
        pager.setAdapter(fragmentPagerAdapter);
        pager.setCurrentItem(0);
        
        btnWiFi = (Button) findViewById(R.id.cnt_btnwifi);
        btnWiFi.setOnClickListener(this);
        btnAP   = (Button) findViewById(R.id.cnt_btnap);
        btnAP.setOnClickListener(this);
        
        btnWiFi.setEnabled(false);
        
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

	private boolean closeAp(){
		
		WifiManager wifiManager = (WifiManager)this.getSystemService(WIFI_SERVICE);
        
        try {  
            //热点的配置类  
            WifiConfiguration apConfig = new WifiConfiguration();  
                        
            //通过反射调用设置热点  
            Method method = wifiManager.getClass().getMethod(  
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);  
            //返回热点打开状态  
            return (Boolean) method.invoke(wifiManager, apConfig, false);  
            
        } catch (Exception e) {  
            return false;  
        }  
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int i = arg0.getId();
		if (i == R.id.cnt_btnwifi) {
			btnWiFi.setEnabled(false);
			btnAP.setEnabled(true);
			pager.setCurrentItem(0);


		} else if (i == R.id.cnt_btnap) {
			btnWiFi.setEnabled(true);
			btnAP.setEnabled(false);
			pager.setCurrentItem(1);


		} else {
		}
	}
	
}
