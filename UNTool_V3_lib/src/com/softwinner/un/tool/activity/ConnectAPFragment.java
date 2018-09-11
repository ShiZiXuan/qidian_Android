package com.softwinner.un.tool.activity;

import android.graphics.Bitmap;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.softwinner.un.tool.R;
import com.softwinner.un.tool.util.EncodQRCodeHandler;
import com.softwinner.un.tool.util.UNLog;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectAPFragment extends Fragment implements OnClickListener{

	private static final String TAG = "ConnectAPFragment";
	
	private EditText et_apname;
	private EditText et_password;
	private Spinner  sp_encryption;
	private Button   btn_generate;
	private Button   btn_openap;
	private TextView tv_pwd_tips;
	
	private String[] encryptions = {"WPA/WPA2","OPEN"};
	private final String pwdChcek = "[A-Z,a-z,0-9]*";//正则表达式，大小写字母或数字
	private ArrayAdapter arrayAdapter;
	
	private boolean isOpenAp = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_connectap, null);
		
		et_apname     = (EditText) v.findViewById(R.id.ap_name);
		et_password   = (EditText) v.findViewById(R.id.ap_password);
		sp_encryption = (Spinner)  v.findViewById(R.id.ap_spinner);
		btn_generate  = (Button)   v.findViewById(R.id.ap_generate);
		btn_openap    = (Button)   v.findViewById(R.id.ap_openap);
		tv_pwd_tips   = (TextView) v.findViewById(R.id.ap_pwd_tips);
		
		btn_generate.setOnClickListener(this);
		btn_openap.setOnClickListener(this);
		
		 //将可选内容与ArrayAdapter连接起来
		arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, encryptions);
        //设置下拉列表的风格
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
		sp_encryption.setAdapter(arrayAdapter);
		
		sp_encryption.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				String selected = (String)sp_encryption.getSelectedItem();
				if (encryptions[encryptions.length - 1].equals(selected)) {
					showPasswordView(false);
				}else {
					showPasswordView(true);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "onResume()");
	}
	
	private String checkInput(){
		
		String tartget = "";
		String ssid = et_apname.getText().toString().trim();
		if ("".equals(ssid)) {
			Toast.makeText(getActivity(), "AP Name can't be null!", Toast.LENGTH_LONG).show();
			et_apname.setFocusable(true);
			return "";
		}
		
		String encryption = (String)sp_encryption.getSelectedItem();
		String password = et_password.getText().toString().trim();
		if (encryptions[encryptions.length - 1].equals(encryption)) {
			encryption = "";
			password = "";
		}else {
			
			if ("".equals(password)) {
				Toast.makeText(getActivity(), "Password can't be null!", Toast.LENGTH_LONG).show();
				et_password.setFocusable(true);
				return "";
			}else if(8 > password.length()){
				Toast.makeText(getActivity(), "Password at least 8 charater!", Toast.LENGTH_LONG).show();
				et_password.setFocusable(true);
				return "";
			}else {
				Pattern pattern = Pattern.compile(pwdChcek);
				Matcher matcher = pattern.matcher(password);
				if (!matcher.matches()) {
					Toast.makeText(getActivity(), "Password only numbers and letters!", Toast.LENGTH_LONG).show();
					et_password.setFocusable(true);
					return "";
				}
			}
			
		}
		
		if ("".equals(encryption)) {
			
			tartget  = "WIFI:S:" + ssid;
			
		}else {
			
			tartget  = "WIFI:T:" + encryption + ";S:" + ssid + ";P:" + password + ";";
		}
		
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "ssid = " + ssid + " password = " + password + " encryption = " + encryption + "\n target = " + tartget);
		
		return tartget;
	}
	

	private UNQRCodeDialog qrcodeDialog;
	private Bitmap qrcodeBmp;
	private OnClickListener confirmListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (qrcodeDialog != null && qrcodeDialog.isShowing()) {
				 qrcodeDialog.dismiss();
			}
//			Intent intent = new Intent(getActivity(), StartActivity.class);
//			startActivity(intent);
			getActivity().setResult(StartActivity.CONNECT_RESULT_CODE);
			getActivity().finish();
		}
	};
	
	private void showQRCodeDialog(Bitmap bitmap){
		
		if (bitmap == null) {
			return;
		}
		
		if (qrcodeDialog == null) {
			qrcodeDialog = new UNQRCodeDialog(getActivity(), R.style.dialog_qrcode);
		}
		qrcodeDialog.show();
		qrcodeDialog.setQRCode(bitmap);
		qrcodeDialog.setConfirmListener(confirmListener);
	}
	
	private void generateQRCode(){
		
		String target = checkInput();
		
		if (!"".equals(target)) {
			
			if (qrcodeBmp != null) {
				qrcodeBmp.recycle();
			}
			try {
				qrcodeBmp = EncodQRCodeHandler.createQRCode(target, ConnectFragmentActivity.SCREEN_WIDTH - 100);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showQRCodeDialog(qrcodeBmp);
			
		}
		
	}
	
	private boolean openAp(){
		
		String target = checkInput();
		
		if ("".equals(target)) {
		
			return false;
			
		}
		
		WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
        	wifiManager.setWifiEnabled(false);  
		} 
        try {  
            //热点的配置类  
            WifiConfiguration apConfig = new WifiConfiguration();  
            //配置热点的名称(可以在名字后面加点随机数什么的)  
            apConfig.SSID = et_apname.getText().toString().trim(); 
            //配置热点的密码  
            apConfig.preSharedKey = et_password.getText().toString().trim(); 
//          apConfig.allowedKeyManagement.set(3);
            String encryption = (String)sp_encryption.getSelectedItem();
            if (encryptions[encryptions.length - 1].equals(encryption)) {
            	apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            	encryption = "";
			}else {
				apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			}
            
            //通过反射调用设置热点  
            Method method = wifiManager.getClass().getMethod(  
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);  
            //返回热点打开状态  
            return (Boolean) method.invoke(wifiManager, apConfig, true);  
            
        } catch (Exception e) {  
            return false;  
        }  
        
	}
	
	private void showPasswordView(boolean isShow){
		
		if (isShow) {
			if (View.VISIBLE != tv_pwd_tips.getVisibility()) {
				tv_pwd_tips.setVisibility(View.VISIBLE);
				et_password.setText("");
				et_password.setVisibility(View.VISIBLE);
			}
		}else {
			if (View.GONE != tv_pwd_tips.getVisibility()) {
				tv_pwd_tips.setVisibility(View.GONE);
				et_password.setText("");
				et_password.setVisibility(View.GONE);
			}
		}
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.ap_generate){
			generateQRCode();
		}else if (arg0.getId() == R.id.ap_openap){
			openAp();
		}

	}
	
}
