package com.softwinner.un.tool.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softwinner.un.tool.Constant;
import com.softwinner.un.tool.R;
import com.softwinner.un.tool.domain.IOCtrlMessage;
import com.softwinner.un.tool.domain.IOCtrlReturnMsg;
import com.softwinner.un.tool.domain.UNDevice;
import com.softwinner.un.tool.model.VideoFile;
import com.softwinner.un.tool.util.FileUtils;
import com.softwinner.un.tool.util.Packet;
import com.softwinner.un.tool.util.SDCardInfo;
import com.softwinner.un.tool.util.UNIOCtrlDefs;
import com.softwinner.un.tool.util.UNLog;
import com.softwinner.un.tool.util.UNTool;
import com.softwinner.un.tool.video.UNVideoViewHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartActivity extends QidianBaseActivity implements View.OnClickListener {

    private final String TAG = "StartActivity";
    public static UNDevice unDevice;

    private RelativeLayout parentView; //RelativeLayout容器
    private RelativeLayout.LayoutParams landscapeParams;//横屏参数
    private RelativeLayout.LayoutParams portraitParams;//竖屏参数
    private UNVideoViewHelper videoHelper;//视频显示画面工具
    private MyUNVideoViewListener videoViewListener;

    private View coverView;
    private List<UNIOCtrlDefs.AW_cdr_get_file_list> tmpFileList = new ArrayList<>();
    private ArrayList<VideoFile> videoFiles = new ArrayList<>();

    private LinearLayout video, photo;
    private ImageView camera_tv;

    private TextView video_number, photo_number;
    private ConnectivityManager cm;

    private TextView count_down_tv;
    private AlertDialog count_down_dialog;

    private ProgressBar storage_pb;
    private TextView used_tv, total_tv;
    private LinearLayout storage_linear;
    private Button format_btn;
    private Button password_btn;

    private boolean hasSearchedDev = false;

    public void isWifiConnected() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.e("wifi========", "==" + wifiInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        UNTool.getInstance().initTool(StartActivity.this);
        UNTool.getInstance().setCallbackListener(callbackListener);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isWifiConnected();
                forceByWiFi();
                handler.sendEmptyMessageDelayed(SEND_SEARCH_DEVICE, 1000); //第一次search  device
            }
        }, 5000);


        storage_pb = (ProgressBar) findViewById(R.id.storage_pb);
        storage_linear = (LinearLayout) findViewById(R.id.storage_linear);
        used_tv = (TextView) findViewById(R.id.used_tv);
        total_tv = (TextView) findViewById(R.id.total_tv);
        coverView = findViewById(R.id.cover_view);
        coverView.setVisibility(View.VISIBLE);
        password_btn = (Button) findViewById(R.id.password_btn);
        password_btn.setOnClickListener(this);
        camera_tv = (ImageView) findViewById(R.id.camera_tv);
        video = (LinearLayout) findViewById(R.id.video_tv);
        photo = (LinearLayout) findViewById(R.id.photo_tv);
        camera_tv.setOnClickListener(this);
        video.setOnClickListener(this);
        photo.setOnClickListener(this);

        format_btn = (Button) findViewById(R.id.format_btn);
        format_btn.setOnClickListener(this);


        video_number = (TextView) findViewById(R.id.video_number_tv);
        photo_number = (TextView) findViewById(R.id.photo_number_tv);

        //init video view
        //获取屏幕的尺寸
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;//宽度height = dm.heightPixels ;//高度
        int height = dm.heightPixels;

        /**
         *1、初始化容器RelativeLayout，负责放置UNVideoViewHelper，并初始化layout横屏、竖屏params。
         */

        parentView = (RelativeLayout) findViewById(R.id.parentview);
        landscapeParams = new RelativeLayout.LayoutParams(height, height * 9 / 16);
        landscapeParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        portraitParams = new RelativeLayout.LayoutParams(width, width * 9 / 16);
        portraitParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        portraitParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        portraitParams.setMargins(0, 15, 0, 0);
        parentView.setLayoutParams(portraitParams);

        /**
         *2、创建VideoViewHelper
         *显示视频显示画面工具类
         */

        videoHelper = new UNVideoViewHelper(this, parentView);

        /**
         *3、 重写&创建Listener，videoViewHelper设置Listener
         */
        videoViewListener = new MyUNVideoViewListener();
        videoHelper.setVideoViewListener(videoViewListener);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
    }

    /**
     * 强制使用wifi进行数据连接，防止记录仪视频数据使用流量通道导致黑屏
     */
    private void forceByWiFi() {
        cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Network[] networks = cm.getAllNetworks();
            for (Network network : networks) {
                if (cm.getNetworkInfo(network).getType() == ConnectivityManager.TYPE_WIFI) {
                    boolean b = cm.bindProcessToNetwork(network);
                    Log.e("绑定成功========", "==" + b);
                    break;
                }
            }
        }
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
     * 固件升级结束后的倒计时
     */
    public void showCountDown() {
        if (count_down_dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.count_down_content, null);
            count_down_tv = (TextView) view.findViewById(R.id.count_down_tv);
            builder.setView(view);
            builder.setCancelable(false);
            count_down_dialog = builder.create();
        }
        count_down_dialog.show();
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            count_down_tv.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            count_down_dialog.setCancelable(false);
            count_down_dialog.setCanceledOnTouchOutside(false);
            count_down_dialog.dismiss();
            StartActivity.this.finish();
        }
    };

    /**
     * 获取设备信息
     *
     * @param rtnMsg
     */
    public void respGetConfig(IOCtrlReturnMsg rtnMsg) {
        UNIOCtrlDefs.AW_cdr_net_config config = new UNIOCtrlDefs.AW_cdr_net_config(rtnMsg.getData());
        String version = "";
        try {
            version = new String(config.firmware_version, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (version.contains("rc"))
            version = version.replace(" rc", ".");

        String server = getSharedPreferences("qidian", MODE_PRIVATE)
                .getString("key_device_version", "V0.0.0");

        server = server.substring(1);
        version = version.substring(1);
        Log.e("设备服务器版本号为======", "=======" + server);
        Log.e("设备目前版本号为======", "=======" + version);

        String[] servers = server.split("\\.");
        String[] currents = version.split("\\.");
        boolean flag = false;
        try {
            if (Integer.parseInt(servers[0]) > Integer.parseInt(currents[0])) {
                flag = true;
            } else {
                if (Integer.parseInt(servers[1]) > Integer.parseInt(currents[1])) {
                    flag = true;
                } else {
                    if (Integer.parseInt(servers[2]) > Integer.parseInt(currents[2])) {
                        flag = true;
                    }
                }
            }

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();

        }

        Log.e("flag=========", "==" + flag);
        Log.e("固件文件存在吗=========", "==" + new File(Constant.FEX_PATH).exists());
        if (flag && new File(Constant.FEX_PATH).exists()) {
            if (SDCardInfo.getTotal() == 0) {
                Toast.makeText(this, "请插入设备SD卡再进行升级", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(OPT_REFRESH_DEVICE);
            } else {
                showUpdatingDialog();
                UNTool.getInstance().sendUploadFile(StartActivity.unDevice.getSid(), Constant.FEX_PATH);//新版本固件传到设备上去
            }
        } else {
            handler.sendEmptyMessage(OPT_REFRESH_DEVICE);
        }
    }

    private MediaPlayer mPlayer;

    /**
     * 拍照播放声音
     */
    private void displaySound() {
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("take_photo.mp3");
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                        descriptor.getStartOffset());
                mPlayer.prepare();
            }
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.camera_tv) {
            //拍照功能
            videoHelper.goShotCut();
            displaySound();
        }
        if (i == R.id.photo_tv) {
            //照片界面
            Intent intent = new Intent(StartActivity.this, PhotoListActivity.class);
            startActivity(intent);
        }
        if (i == R.id.video_tv) {
            //视频界面
            Intent intent = new Intent(this, VideoListActivity.class);
            intent.putExtra("videos", videoFiles);
            startActivityForResult(intent, RETURN_FROM_VIDEO);
            closeVideo();
            UNTool.getInstance().switchRecord(false);
        }
        if (i == R.id.format_btn) {
            showFormatTFCardDialog();
        }
        if (i == R.id.password_btn) {
            Intent intent = new Intent(this, ReviseActivity.class);
            startActivityForResult(intent, CHNAGE_WIFI_PASSWORD);
        }

    }


    /**
     * 监听画面显示状态
     *
     * @author AW-Roy
     */
    class MyUNVideoViewListener implements UNVideoViewHelper.UNVideoViewListener {
        /**
         * 第一帧显示（一般用户更新UI界面）
         */
        @Override
        public void videoViewShow() {
            // TODO Auto-generated method stub
            handler.sendEmptyMessage(VIDEO_BEGIN_SHOW);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    coverView.setVisibility(View.GONE);
                    findViewById(R.id.control_linear).setVisibility(View.VISIBLE);
                    storage_linear.setVisibility(View.VISIBLE);
                    storage_pb.setVisibility(View.VISIBLE);
                }
            }, 500);
        }

        /**
         * I Frame返回（每个I Frame均回调此处，可做心跳监听）
         */

        @Override
        public void videoViewShowing() {
            // TODO Auto-generated method stub

        }

        /**
         * 视频结束回调（异常退出或正常退出，返回命令RTSP_STREAM_CLOSE比较准时）
         */

        @Override
        public void videoViewEnd() {
            // TODO Auto-generated method stub
            handler.sendEmptyMessage(VIDEO_END_SHOW);
        }

        /**
         * videoViewHelper.goShotCut()调用截图，截图完成后回调此处，可获取Bitmap
         */

        @Override
        public void videoViewShotCutEnd() {
            // TODO Auto-generated method stub
            try {
                String downloadDir = FileUtils.getFolderDir(FileUtils.PICTURE_DIR).getAbsolutePath();
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
                String name = sdf.format(new Date());
                FileOutputStream fos = new FileOutputStream(downloadDir + "/" + name + ".jpg");
                videoHelper.getShotCut().compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Uri localUri = Uri.parse("file://" + downloadDir + "/" + name + ".jpg");
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                localIntent.setData(localUri);
                StartActivity.this.sendBroadcast(localIntent);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        photo_number.setText("" + getPicCount());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show();
            parentView.setLayoutParams(landscapeParams);

            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    storage_linear.setVisibility(View.GONE);
                    storage_pb.setVisibility(View.GONE);
                }
            });

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
            parentView.setLayoutParams(portraitParams);

            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            //取消全屏设置
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    storage_linear.setVisibility(View.VISIBLE);
                    storage_pb.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void showVideo() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                UNTool.getInstance().sendStartVideoStream(videoHelper, StartActivity.unDevice.getSid());
            }
        }).start();
    }

    private void closeVideo() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    UNTool.getInstance().sendStopVideoStream(StartActivity.unDevice.getSid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        photo_number.setText(" " + getPicCount());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        closeVideo();
        UNTool.getInstance().unBindUNService(StartActivity.this);
        hideUpdatingDialog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && cm != null)
            cm.bindProcessToNetwork(null);
        //TODO
        if (mWifiManager.getConnectionInfo() != null && mWifiManager.getConnectionInfo().getSSID().contains("启点车连")) {
            mWifiManager.disableNetwork(mWifiManager.getConnectionInfo().getNetworkId());
            mWifiManager.disconnect();
        }
    }

    private void createQRCode() {
        Intent intent = new Intent(StartActivity.this, ConnectFragmentActivity.class);
        startActivityForResult(intent, CONNECT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONNECT_REQUEST_CODE) {
            if (resultCode == CONNECT_RESULT_CODE) {
                UNLog.debug_print(UNLog.LV_DEBUG, TAG, "onActivityResult here!");
                handler.sendEmptyMessageDelayed(OPT_TOTAL_EXIT, 500);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == RETURN_FROM_VIDEO) {
                showVideo();
                UNTool.getInstance().switchRecord(true);
            } else if (requestCode == CHNAGE_WIFI_PASSWORD) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                finish();
            }
        }

    }

    private void exitall() {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "exitall");
        UNTool.getInstance().deInitTool(StartActivity.this);
    }


    private void respGetFileList(IOCtrlReturnMsg rtnMsg) {
        UNIOCtrlDefs.AW_cdr_get_file_list file_list = new UNIOCtrlDefs.AW_cdr_get_file_list(rtnMsg.getData());
        file_list.toString();
        if (tmpFileList == null) {
            tmpFileList = new ArrayList();
        }
        if (file_list.currentIndex == 0) {
            if (tmpFileList.size() > 0) {
                tmpFileList.clear();
            }
        }
        tmpFileList.add(file_list);
        if (file_list.currentIndex == file_list.totalCnt - 1) {
            handleIOCtrlReapCombineFileLists(tmpFileList);
        }
    }

    private void handleIOCtrlReapCombineFileLists(List<UNIOCtrlDefs.AW_cdr_get_file_list> lists) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (UNIOCtrlDefs.AW_cdr_get_file_list file : lists) {
            try {
                localStringBuffer.append(new String(file.filelist, "UTF-8").trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        refreshFileListResp(localStringBuffer.toString());
    }

    private void refreshFileListResp(String fileString) {
        Log.e("videos=======", "===" + fileString);
        if (videoFiles == null) {
            videoFiles = new ArrayList<>();
        }
        videoFiles.clear();
        if (!TextUtils.isEmpty(fileString)) {
            String[] paramString = fileString.split(";");
            for (String temp : paramString) {
                String[] fileTemp = temp.split(":");
                VideoFile videoFile = new VideoFile();
                videoFile.name = fileTemp[0].substring(17);
                if (videoFile.name.startsWith("."))
                    continue;
                videoFile.path = fileTemp[0];
                videoFile.size = fileTemp[1];
                videoFiles.add(videoFile);
            }
        }
        if (videoFiles.size()>0)
            videoFiles.remove(0);
        VideoListActivity.refresh(videoFiles);
        video_number.setText("" + videoFiles.size());
    }

    private void refreshDevice() {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "refreshDevice devices = " + unDevice);
        if (unDevice != null) {
            showVideo();
        }
    }

    private static final int OPT_REFRESH_DEVICE = 0;
    private static final int OPT_TOTAL_EXIT = 1;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                //--------ACTIVITY OPTION----------
                case OPT_REFRESH_DEVICE:
                    refreshDevice();
                    break;
                case OPT_TOTAL_EXIT:
                    exitall();
                    break;

                //----------SEND--------------
                case SEND_SEARCH_DEVICE:
                    if (!hasSearchedDev) {
                        UNTool.getInstance().sendSearchDevice();
                    }
                    break;

                case SEND_CONNECT_DEVICE:
                    sendConnectDevice((UNDevice) msg.obj);
                    break;
                case SEND_IOCTRL_MSG:
                    sendIOCtrlMsg((IOCtrlMessage) msg.obj);
                    break;
                //----------RESP--------------
                case RESP_SEARCH_DEVICE:
                    respSearchDevice((IOCtrlReturnMsg) msg.obj);
                    break;
                case RESP_CONNECT_DEVICE_SUCCESS:
                    respConnectDevSuccess((IOCtrlReturnMsg) msg.obj);
                    break;
                case RESP_CONNECT_DEVICE_FAIL:
                    respConnectDevFail((IOCtrlReturnMsg) msg.obj);
                    break;
                case RESP_GET_FILE_LIST:
                    respGetFileList((IOCtrlReturnMsg) msg.obj);
                    break;
                case UNTool.RESP_DEINIT_SUCCESS:
                    StartActivity.this.finish();
                    break;
                case RESP_GET_CONFIG:
                    respGetConfig((IOCtrlReturnMsg) msg.obj);
                    break;
                //  视频播放回调
                case VIDEO_END_SHOW:
                    break;
                case RESP_UPLOAD_FILE_FINISHED://固件升级上传文件完成
                    respUploadFileFinished();
                    break;
                case RESP_CHECK_TF_CARD_RESP://检查TF卡
                    respCheckTFCard((IOCtrlReturnMsg) msg.obj);
                    break;
                case RESP_CHECK_SHA1_RESP:
                    respCheckSHA1((IOCtrlReturnMsg) msg.obj);
                    break;
                default:
                    break;
            }

        }
    };

    private void respCheckSHA1(IOCtrlReturnMsg obj) {
        int i = Packet.byteArrayToInt_Little(obj.getData());
        Log.e("i=========", "====" + i);
        Log.e("SHA1验证=======", "=======" + (i == 0 ? "通过" : "失败"));

        if (i == 0) {
            UNTool.getInstance().update(StartActivity.this);
            getSharedPreferences("qidian", MODE_PRIVATE).edit().putString(Constant.WIFI_PASSWORD, "1234567890").apply();
            isWiFiClosed();
        } else {
            Toast.makeText(this, "固件损坏 请重新下载", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(OPT_REFRESH_DEVICE);
            getSharedPreferences("qidian", MODE_PRIVATE)
                    .edit().putString("key_device_version", "V0.0.0")
                    .apply();
        }
    }

    private void respRecordOnOff() {
        UNTool.getInstance().formatTFCard();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                respFormatTFCard();
            }
        }, 20000);
    }

    private void respUploadFileFinished() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    new File(Constant.FEX_PATH).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                UNTool.getInstance().checkSHA1();
                UNTool.getInstance().update(StartActivity.this);
                getSharedPreferences("qidian", MODE_PRIVATE).edit().putString(Constant.WIFI_PASSWORD, "1234567890").apply();
                isWiFiClosed();
            }
        }).start();
    }

    private void respCheckTFCard(IOCtrlReturnMsg msg) {
        UNIOCtrlDefs.AW_cdr_tf_capacity capacity = new UNIOCtrlDefs.AW_cdr_tf_capacity(msg.getData()); //rtnMsg.getDate() 返回一些sd卡内存信息
        SDCardInfo.setRemain(capacity.remain);
        SDCardInfo.setTotal(capacity.total);
        Log.e("剩余(MB)=======" + capacity.remain, ",一共(MB)=======" + capacity.total);
        if (capacity.total != 0) {
            double used = (capacity.total - capacity.remain) * 1.0 / 1024;
            double total = capacity.total * 1.0 / 1024;
            DecimalFormat df = new DecimalFormat("0.00");
            String used_str = df.format(used);
            String total_str = df.format(total);
            Log.e("剩余(GB)=======" + used_str, ",一共(GB)=======" + total_str);
            used_tv.setText("已用 : " + used_str + "GB");
            total_tv.setText("总共 : " + total_str + "GB");
            storage_pb.setProgress((int) ((capacity.total - capacity.remain) * 100 / capacity.total));
        }
        UNTool.getInstance().getFileList();
    }


    private void sendConnectDevice(UNDevice device) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "sendConnectDevice() device = " + device);
        UNTool.getInstance().sendConnectDevice(device.getUid(), device.getPassword());
    }

    private void sendIOCtrlMsg(IOCtrlMessage ioctlMsg) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "sendIOCtrlMsg() ioctlMsg = " + ioctlMsg);
        UNTool.getInstance().sendIOCtrlMsg(ioctlMsg);
    }

    private void respSearchDevice(IOCtrlReturnMsg rtnMsg) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respSearchDevice()");
        int num = rtnMsg.getLen() / UNIOCtrlDefs.LanSearchInfo.getTotalSize();//搜索到的设备个数
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "the num of devices is " + num);

        if (handler.hasMessages(SEND_SEARCH_DEVICE)) {
            handler.removeMessages(SEND_SEARCH_DEVICE);
        }
        if (num == 0) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "respSearchDevice() returnMsg to num == 0!");
            hasSearchedDev = false;
            handler.sendEmptyMessageDelayed(SEND_SEARCH_DEVICE, 1000);//如果搜索不到设备就每隔一秒搜一次
            return;
        } else if (num == 1) {
            hasSearchedDev = true;
        }
        UNIOCtrlDefs.LanSearchInfo info;
        String uid = "";
        String ip = "";
        int port = -1;
        //Just one device
        info = new UNIOCtrlDefs.LanSearchInfo(rtnMsg.getData(), 0);
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respSearchDevice() info = " + info);

        //set uid
        try {
            uid = new String(info.UID, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "respSearchDevice() uid 2 String error!");
        }

        if (uid == null || "".equals(uid)) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "respSearchDevice() uid null error!");
            return;
        }

        //set ip
        try {
            ip = new String(info.IP, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respSearchDevice() ip 2 String error!");
        }
        unDevice = new UNDevice(uid, UNDevice.DEFAULT_PWD, -1, ip, port, UNDevice.STATE_INIT);

        Message msg = handler.obtainMessage();
        msg.what = SEND_CONNECT_DEVICE;
        msg.obj = unDevice;
        handler.sendMessage(msg);
    }

    private void respConnectDevSuccess(IOCtrlReturnMsg rtnMsg) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respConnectDevSuccess() rtnMsg = " + rtnMsg);
        unDevice.setSid(rtnMsg.getSid());
        unDevice.setState(UNDevice.STATE_SUCCESS);
        UNTool.getInstance().syncTimetoDev();//同步时间
        UNTool.getInstance().checkTFCard();
        UNTool.getInstance().getConfig();
    }

    /**
     * 监测WiFi连接是否断开
     */
    private void isWiFiClosed() {
        final String ssid = getSharedPreferences("qidian", MODE_PRIVATE)
                .getString(Constant.WIFI_NAME, "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean closed = false;
                while (!closed) {
                    try {
                        Thread.sleep(2000);//每2秒扫描一次WiFi
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<ScanResult> results = mWifiManager.getScanResults();
                    boolean exist = false;
                    for (ScanResult result : results) {
                        if (result.SSID.equals(ssid)) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        hideUpdatingDialog();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showCountDown();
                            }
                        });
                        closed = true;
                    }
                }
            }
        }).start();
    }

    private WifiManager mWifiManager;

    private ProgressDialog mUpgradingDialog;

    /**
     * 显示固件升级中的弹窗
     */
    private void showUpdatingDialog() {
        mUpgradingDialog = new ProgressDialog(this);
        mUpgradingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mUpgradingDialog.setMessage("升级中...请勿退出!!!(过程不耗流量)");
        mUpgradingDialog.setCancelable(false);
        mUpgradingDialog.setCanceledOnTouchOutside(false);
        mUpgradingDialog.show();
    }

    /**
     * 隐藏固件升级中的弹窗
     */
    private void hideUpdatingDialog() {
        if (mUpgradingDialog != null && mUpgradingDialog.isShowing())
            mUpgradingDialog.dismiss();
    }


    private void respConnectDevFail(IOCtrlReturnMsg rtnMsg) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respConnectDevFail() rtnMsg = " + rtnMsg);
        unDevice.setSid(rtnMsg.getSid());
        handler.sendEmptyMessageDelayed(SEND_SEARCH_DEVICE, 1000);
    }

    private ProgressDialog mFormatingDialog;

    /**
     * 显示TF卡格式化过程中的弹窗
     */
    private void showFormatingDialog() {
        mFormatingDialog = new ProgressDialog(this);
        mFormatingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mFormatingDialog.setMessage("格式化TF卡中...");
        mFormatingDialog.setCancelable(false);
        mFormatingDialog.setCanceledOnTouchOutside(false);
        mFormatingDialog.show();
    }

    /**
     * 隐藏TF卡格式化过程中的弹窗
     */
    private void hideFormatingDialog() {
        if (mFormatingDialog != null && mFormatingDialog.isShowing())
            mFormatingDialog.dismiss();
    }

    private void respFormatTFCard() {
        videoFiles.clear();
        video_number.setText("0");
        UNTool.getInstance().checkTFCard();
        hideFormatingDialog();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchRecord(false);
            }
        }, 5000);
    }

    private void showFormatTFCardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("格式化设备TF卡")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showFormatingDialog();
                        switchRecord(true);
                    }
                })
                .setNegativeButton("否", null)
                .setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        dialog.show();
    }

    /**
     * 发送指令通知设备停止或开始录像
     */
    private void switchRecord(boolean pause) {
        int n = 0;
        if (!pause) {
            n = 1;
        }
        IOCtrlMessage msg1 = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_RECORD_ON_OFF,
                UNIOCtrlDefs.AW_cdr_set_cmd.combindContent(n),
                UNIOCtrlDefs.AW_cdr_set_cmd.getTotalSize());
        UNTool.getInstance().sendIOCtrlMsg(msg1);
        if (pause)
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    respRecordOnOff();
                }
            }, 5000);
    }

    //----------------SEND-------------------------
    private static final int SEND_SEARCH_DEVICE = 101;
    private static final int SEND_CONNECT_DEVICE = 102;
    private static final int SEND_IOCTRL_MSG = 103;
    //----------------RESP-------------------------
    private static final int RESP_SEARCH_DEVICE = 201;
    private static final int RESP_CONNECT_DEVICE_SUCCESS = 202;
    private static final int RESP_CONNECT_DEVICE_FAIL = 203;
    private static final int RESP_GET_FILE_LIST = 204;
    private static final int RESP_UPLOAD_FILE_FINISHED = 205;
    private static final int RESP_GET_CONFIG = 206;
    private static final int RESP_CHECK_TF_CARD_RESP = 207;
    private static final int RESP_FORMAT_TF_CARD_RESP = 208;
    private static final int RESP_RECORD_ON_OFF_RESP = 209;
    private static final int RESP_CHECK_SHA1_RESP = 210;
    //----------------其他--------------------------
    private static final int VIDEO_BEGIN_SHOW = -100;
    private static final int VIDEO_END_SHOW = -101;

    //----------------ActivityResult---------------
    private static final int CONNECT_REQUEST_CODE = 1000;
    public static final int CONNECT_RESULT_CODE = 1001;
    private static final int CHNAGE_WIFI_PASSWORD = 1002;
    private static final int RETURN_FROM_VIDEO = 1003;

    private UNTool.UNToolCallbackListener callbackListener = new UNTool.UNToolCallbackListener() {

        @Override
        public void handleUNToolCallback(IOCtrlReturnMsg rtnMsg) {
            // TODO Auto-generated method stub
            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "handleUNToolCallback() type = " + rtnMsg.getIOCTRLType());
            Message msg = handler.obtainMessage();
            msg.obj = rtnMsg;
            msg.what = -1;
            switch (rtnMsg.getIOCTRLType()) {
                case UNIOCtrlDefs.AW_IOTYPE_USER_IPCAM_SEARCH_DEVICE: //搜索返回
                    msg.what = RESP_SEARCH_DEVICE;
                    if (handler.hasMessages(SEND_SEARCH_DEVICE))
                        handler.removeMessages(SEND_SEARCH_DEVICE);
                    break;
                case UNIOCtrlDefs.AW_IOTYPE_USER_IPCAM_CONNECT_SUCESS:
                    msg.what = RESP_CONNECT_DEVICE_SUCCESS;
                    break;
                case UNIOCtrlDefs.AW_IOTYPE_USER_IPCAM_CONNECT_FAILED:
                    msg.what = RESP_CONNECT_DEVICE_FAIL;
                    break;
                case UNIOCtrlDefs.NAT_CMD_GET_CONFIG_RESP:
                    msg.what = RESP_GET_CONFIG;
                    break;
                case UNIOCtrlDefs.NAT_CMD_GET_FILE_LIST_RESP:
                    msg.what = RESP_GET_FILE_LIST;
                    break;
                case UNIOCtrlDefs.AW_IOTYPE_USER_IPCAM_POST_FILE_RESULT:
                    msg.what = RESP_UPLOAD_FILE_FINISHED;
                    break;
                case UNIOCtrlDefs.NAT_CMD_CHECK_TF_CARD_RESP:
                    msg.what = RESP_CHECK_TF_CARD_RESP;
                    break;
                case UNIOCtrlDefs.NAT_CMD_FORMAT_TF_CARD_RESP:
                    msg.what = RESP_FORMAT_TF_CARD_RESP;
                    break;
                case UNIOCtrlDefs.NAT_CMD_RECORD_ON_OFF_RESP:
                    msg.what = RESP_RECORD_ON_OFF_RESP;
                    break;
                case UNIOCtrlDefs.NAT_CMD_CHECK_SHA1_RESP:
                    msg.what = RESP_CHECK_SHA1_RESP;
                    break;
                default:
                    msg.what = rtnMsg.getIOCTRLType();
                    break;
            }
            if (msg.what != -1)
                handler.sendMessage(msg);

        }
    };

}