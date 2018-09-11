package com.softwinner.un.tool.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softwinner.un.tool.R;

import java.util.List;

import static com.softwinner.un.tool.activity.WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA;



public class WifiListActivity extends QidianBaseActivity {

    private WifiManager wifiManager;
    List<ScanResult> list;
    private MyAdapter myAdapter;
    private WifiAutoConnectManager autoConnectManager;
    private Handler handler;
    private static final int WIFI_OPEN = 100;
    private ProgressDialog progressDialog;
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.getConnectionInfo() != null && (wifiManager.getConnectionInfo().getSSID().contains("Action")||wifiManager.getConnectionInfo().getSSID().contains("启点车连"))){
            Intent intent = new Intent(WifiListActivity.this,StartActivity.class);
            startActivity(intent);
            finish();
        }
        autoConnectManager = new WifiAutoConnectManager(this);
        autoConnectManager.setListenner(new WifiAutoConnectManager.WifiConnectListenner() {
            @Override
            public void connectFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WifiListActivity.this, "请在设置页连接记录仪WIFI", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        finish();
                    }
                });
            }

            @Override
            public void connectSuc() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(WifiListActivity.this, "连接成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(WifiListActivity.this,StartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查找Wifi");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progressDialog.dismiss();
                init();
            }
        };
        if (!wifiManager.isWifiEnabled()) {
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isOpen = wifiManager.setWifiEnabled(true);
                    if (isOpen) {
                        Message message = Message.obtain();
                        message.what = WIFI_OPEN;
                        handler.sendMessageDelayed(message,8000);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WifiListActivity.this, "请在设置页打开Wifi", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                finish();
                            }
                        });
                    }
                }
            }).start();

        } else {
            init();
        }

    }

    private void init() {
        findViewById(R.id.top_bar_left_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = wifiManager.getScanResults();
        myAdapter = new MyAdapter(this, list);
        ListView listView = (ListView) findViewById(R.id.wifi_list);

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanResult result = list.get(position);
                //TODO
                if (result.SSID.contains("Action") || result.SSID.contains("启点车连")) {
                    autoConnectManager.connect(result.SSID, "1234567890", WIFICIPHER_WPA);
                } else {
                    Toast.makeText(WifiListActivity.this, "请连接启点车连Wifi", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public class MyAdapter extends BaseAdapter {

        LayoutInflater inflater;
        List<ScanResult> list;

        public MyAdapter(Context context, List<ScanResult> list) {
            // TODO Auto-generated constructor stub
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            view = inflater.inflate(R.layout.item_wifi_list, null);
            ScanResult scanResult = list.get(position);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(scanResult.SSID);
            return view;
        }

    }

}
