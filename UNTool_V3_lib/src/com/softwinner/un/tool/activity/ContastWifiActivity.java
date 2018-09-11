package com.softwinner.un.tool.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softwinner.un.tool.R;
import com.softwinner.un.tool.util.FileUtils;

import java.io.File;
import java.util.List;

import static com.softwinner.un.tool.activity.WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA;

/**
 * 主页与wifi列表的连接界面
 */
public class ContastWifiActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView video_tv, photo_tv, video_number_tv, photo_number_tv;
    private ImageView back;
    private WifiManager wifiManager;
    private LinearLayout wifi_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contast_wifi);
        wifi_tv = (LinearLayout) findViewById(R.id.wifi_tv);
        video_tv = (TextView) findViewById(R.id.videoed_tv);
        photo_tv = (TextView) findViewById(R.id.photoed_tv);
//        video_number_tv = (TextView) findViewById(R.id.videoed_number_tv);
//        photo_number_tv = (TextView) findViewById(R.id.photoed_number_tv);
        back = (ImageView) findViewById(R.id.center_top_bar_left_img);
        back.setOnClickListener(this);
        wifi_tv.setOnClickListener(this);
        video_tv.setOnClickListener(this);
        photo_tv.setOnClickListener(this);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        photo_number_tv.setText("" + getPicCount());
//        video_number_tv.setText("" + getVideoCount());

        video_tv.setText("视频(" + getVideoCount() + ")");
        photo_tv.setText("照片(" + getPicCount() + ")");
    }

    /**
     * 获取图片的数量
     *
     * @return
     */
    public int getPicCount() {
        int n = 0;
        try {
            String pictureDir = FileUtils.getFolderDir(FileUtils.PICTURE_DIR).getAbsolutePath();
            File pic_dir = new File(pictureDir);
            String[] photos = pic_dir.list();
            for (String photo : photos) {
                if (photo.endsWith(".jpg") || photo.endsWith(".JPG"))
                    n++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * 获取视频的数量
     *
     * @return
     */
    public int getVideoCount() {
        int n = 0;
        try {
            String videoDir = FileUtils.getFolderDir(FileUtils.DOWNLOAD_DIR).getAbsolutePath();
            File video_dir = new File(videoDir);
            String[] videos = video_dir.list();
            for (String video : videos) {
                if (video.endsWith(".mov") || video.endsWith(".MOV"))
                    n++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.wifi_tv) {//点击wifi
//            startActivity(new Intent(this, WifiListActivity.class));
//            wifiManager.disconnect();
            connectWiFi();
        }
        if (i == R.id.videoed_tv) {//点击已下载视频
            startActivity(new Intent(this, VideoedActivity.class));
        }
        if (i == R.id.photoed_tv) {//点击已下载照片
            startActivity(new Intent(this, PhotoListActivity.class));
        }
        if (i == R.id.center_top_bar_left_img) {//返回键退出界面
            setResult(RESULT_OK);
            finish();
        }

    }

    private void connectWiFi() {
        List<ScanResult> list = wifiManager.getScanResults();
        WifiAutoConnectManager autoConnectManager = new WifiAutoConnectManager(this);
        autoConnectManager.setListenner(new WifiAutoConnectManager.WifiConnectListenner() {
            @Override
            public void connectFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ContastWifiActivity.this, "请在设置页连接记录仪WIFI", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }

                });

            }

            @Override
            public void connectSuc() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ContastWifiActivity.this, "正在连接启点WiFi", Toast.LENGTH_LONG).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    boolean connected = false;
                                    while (!connected) {
                                        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                                        Network[] networks = cm.getAllNetworks();
                                        for (Network network : networks) {
                                            if (cm.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_WIFI) {
                                                connected = true;
                                                Intent intent = new Intent();
                                                intent.setAction("action.mainwifiactivity");
                                                startActivity(intent);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }).start();
                    }
                });
            }
        });

        if (wifiManager.getConnectionInfo() != null && (wifiManager.getConnectionInfo().getSSID().contains("Action") || wifiManager.getConnectionInfo().getSSID().contains("启点车连"))) {
            String ssid = wifiManager.getConnectionInfo().getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
            getSharedPreferences("qidian", MODE_PRIVATE)
                    .edit().putString("device_wifi_name", ssid).commit();
//            Intent intent = new Intent(ContastWifiActivity.this, StartActivity.class);
//            startActivity(intent);

            Intent intent = new Intent();
            intent.setAction("action.mainwifiactivity");
            startActivity(intent);

        } else {

            String ssid = getSharedPreferences("qidian", MODE_PRIVATE)
                    .getString("device_wifi_name", "");

            if (!TextUtils.isEmpty(ssid)) {
                Log.e("ssid=========", "===" + ssid);

                boolean find = false;
                for (ScanResult result : list) {
                    Log.e("result.SSID=========", "===" + result.SSID);

                    if (ssid.equals(result.SSID)) {
                        String pwd = getSharedPreferences("qidian", MODE_PRIVATE).getString("device_wifi_password", "1234567890");

                        autoConnectManager.connect(result.SSID, pwd, WIFICIPHER_WPA);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "没有搜索到设备WiFi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }
    }
}