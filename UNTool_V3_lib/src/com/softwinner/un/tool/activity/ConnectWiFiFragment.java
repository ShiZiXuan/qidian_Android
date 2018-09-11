package com.softwinner.un.tool.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectWiFiFragment extends Fragment implements OnClickListener{
	
	private static final String TAG = "ConnectWiFiFragment";
	
	private EditText et_ssid;
	private TextView tv_pwd_tips;
	private EditText et_password;
	private Spinner  sp_encryption;
	private Button   btn_generate;
	private String[] encryptions = {"WPA/WPA2", "WEP", "OPEN"};
	private ArrayAdapter arrayAdapter;
	private final String pwdChcek = "[A-Z,a-z,0-9]*";//正则表达式，大小写字母或数字
	private String currentSSID = "";
	private static final int REFRESH_CURRENT_SSID = 100;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_connectwifi, null);
		et_ssid = (EditText) v.findViewById(R.id.wifi_ssid);
		tv_pwd_tips = (TextView) v.findViewById(R.id.wifi_pwd_tips);
		et_password = (EditText) v.findViewById(R.id.wifi_password);
		sp_encryption = (Spinner) v.findViewById(R.id.wifi_spinner);
		btn_generate = (Button) v.findViewById(R.id.wifi_generate);
		btn_generate.setOnClickListener(this);
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
		
		WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
				
		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED){
			
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(true);
				Toast.makeText(getActivity(), "Openning WiFi...", Toast.LENGTH_LONG).show();
				handler.sendEmptyMessageDelayed(REFRESH_CURRENT_SSID, 2000);
			}
			
		}else {
			
			handler.sendEmptyMessage(REFRESH_CURRENT_SSID);
			
		}
		
		registerWiFiConnectReceiver();
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "onResume()");
		if (isOnStop) {
			registerWiFiConnectReceiver();
			handler.sendEmptyMessage(REFRESH_CURRENT_SSID);
			isOnStop = false;
		}
	}
	
	private boolean isOnStop = false;
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isOnStop = true;
		getActivity().unregisterReceiver(wifiReceiver);
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
	
	private String checkInput(){
		
		String tartget = "";
		String ssid = et_ssid.getText().toString().trim();
		if ("".equals(ssid)) {
			Toast.makeText(getActivity(), "SSID can't be null!", Toast.LENGTH_LONG).show();
			et_ssid.setFocusable(true);
			return "";
		}
		
		String encryption = (String)sp_encryption.getSelectedItem();
		String password = "";
		if (encryptions[encryptions.length - 1].equals(encryption)) {
			
			encryption = "";
			password = "";
			
		}else {
			
			password = et_password.getText().toString().trim();
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
	
	private String getCurrentSSID(Context context){
		
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "getCurrentSSID()");
		String currentSSID ;
		WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
		if (wifiManager == null) {
			return "";
		}
		WifiInfo info = wifiManager.getConnectionInfo();
		currentSSID   = info != null ? info.getSSID() : null;
		//hack get wifissid has  ""
		if (currentSSID.contains("\"")) {
		 currentSSID = (String) currentSSID.subSequence(1, currentSSID.length() - 1);
		}
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "getCurrentSSID() currentSSID = " + currentSSID);
		return currentSSID;
		
	}
	
	private void refreshCurrentSSID(){
		
		currentSSID = getCurrentSSID(getActivity());
		et_ssid.setText(currentSSID);
		
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

		int i = arg0.getId();
		if (i == R.id.wifi_generate) {
			generateQRCode();

		} else {
		}
		
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case REFRESH_CURRENT_SSID:
				refreshCurrentSSID();
				break;

			default:
				break;
			}
			
			
		};
	};
	
	private WiFiConnectChangedReceiver      wifiReceiver;
	private void registerWiFiConnectReceiver(){
		
		 UNLog.debug_print(UNLog.LV_DEBUG, TAG, "registerWiFiConnectReceiver()");
		 IntentFilter filter = new IntentFilter();
		 filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		 filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		 filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		 if (wifiReceiver == null) {
			wifiReceiver = new WiFiConnectChangedReceiver();
		 }
		 getActivity().registerReceiver(wifiReceiver, filter);
		
	}
	
	public class WiFiConnectChangedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			  if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
				  UNLog.debug_print(UNLog.LV_ERROR, TAG, " WifiManager.WIFI_STATE_CHANGED_ACTION ");
				  handler.sendEmptyMessageDelayed(REFRESH_CURRENT_SSID, 2000);
			  }
	       }  
	}
	
}
