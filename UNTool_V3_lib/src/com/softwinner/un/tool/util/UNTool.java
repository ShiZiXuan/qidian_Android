package com.softwinner.un.tool.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.softwinner.un.tool.Constant;
import com.softwinner.un.tool.domain.IOCtrlMessage;
import com.softwinner.un.tool.domain.IOCtrlReturnMsg;
import com.softwinner.un.tool.service.UNService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import static android.content.Context.WIFI_SERVICE;
import static com.softwinner.un.tool.activity.StartActivity.unDevice;
import static com.softwinner.un.tool.util.UNJni.jni_uploadFile;

public class UNTool {

    private static final String TAG = "UNTool";

    private static UNTool instance;

    public synchronized static UNTool getInstance() {
        if (instance == null) {
            instance = new UNTool();
        }
        return instance;
    }

    private boolean isInitTool = false;
    private boolean isStartService = false;
    private boolean isOfflineMode = false;

    public static final int RESP_DEINIT_SUCCESS = 2468;

    private UNToolCallbackListener callbackListener;

    public UNToolCallbackListener getCallbackListener() {
        return callbackListener;
    }

    public void setCallbackListener(UNToolCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    private Context context;

    /**
     * 发消息到Service（服务器端）
     */
    private Messenger mMessenger;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {

            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "onServiceConnected()");

            if (mMessenger == null) {
                //使用Service返回的bindler方法得到一个信使对象
                //其实就远程（Service）的信使对象
                mMessenger = new Messenger(service);
                try {
                    //新建一个message消息
                    Message msg = Message.obtain(null, UNService.SEND_REGISTER_CLIENT);
                    //把信使对象添加到消息中，当Service端接收到信息的时候同事野可以获取到客户端的信使，
                    //可以往客户端信使添加消息，完成Service端和客户端相互通信
                    msg.replyTo = mSerMessenger;
                    //把消息发送到服务器端
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    // In this case the service has crashed before we could even do anything with it
                }
            } else {
                UNLog.debug_print(UNLog.LV_DEBUG, TAG, "onServiceConnected() mMessenger not null");
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "onServiceDisconnected()");
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
        }
    };
    ;

    //Public----------------------------------

    /**
     * 开启服务器，建立Activity与服务器之间的而连接
     *
     * @param context
     */
    public void initTool(Activity context) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "initTool() context = " + context);

        if (!isInitTool) {
            if (!isStartService) {
                startUNService(context);
            }
            bindUNService(context);
            this.context = context;
            isInitTool = true;
        }
    }

    /**
     * deinitTool()
     */
    public void deInitTool(Activity context) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "deinitTool() context = " + context);

        this.context = context;

        if (mMessenger == null) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "deInitTool() mMessenger = null");
            return;
        }

        Message msg = Message.obtain(null, UNService.SEND_DEINIT_SERVICE);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendSearchDevice() {

        if (mMessenger == null) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "searchDevice() mMessenger = null");
            return;
        }

        Message msg = Message.obtain(null, UNService.SEND_SEARCH_DEVICE);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendConnectDevice(String uid, String pwd) {

        if (mMessenger == null) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "searchDevice() mMessenger = null");
            return;
        }

        Message msg = Message.obtain(null, UNService.SEND_CONNECT_DEVICE);
        Bundle bundle = new Bundle();
        bundle.putString(UNIOCtrlDefs.UID_TAG, uid);
        bundle.putString(UNIOCtrlDefs.PWD_TAG, pwd);
        msg.setData(bundle);

        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendDisConnectDevice(int sid) {

        if (mMessenger == null) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "searchDevice() mMessenger = null");
            return;
        }

        Message msg = Message.obtain(null, UNService.SEND_DISCONNECT_DEVICE);
        msg.arg1 = sid;

        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param ioCtrlMessage
     * @return
     */
    public boolean sendIOCtrlMsg(IOCtrlMessage ioCtrlMessage) {

        Message msg = Message.obtain(null, UNService.SEND_SEND_IOCTRL_MSG);
        msg.obj = ioCtrlMessage;
        try {
            mMessenger.send(msg);
            return true;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param videoHelper
     * @param sid
     */
    public void sendStartVideoStream(Object videoHelper, int sid) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "startIpcamStream() sid = " + sid);
        Message msg = Message.obtain(null, UNService.SEND_START_VIDEO_STREAM);
        msg.arg1 = sid;
        msg.obj = videoHelper;
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param sid
     */
    public void sendStopVideoStream(int sid) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopVideoStream() sid = " + sid);
        Message msg = Message.obtain(null, UNService.SEND_STOP_VIDEO_STREAM);
        msg.arg1 = sid;
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    //Private---------------------------------

    /**
     * 启动一个（某个Actvity）Service
     *
     * @param context
     */
    private void startUNService(Context context) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "startUNService context = " + context);
        context.startService(new Intent(context, UNService.class));
        isStartService = true;
    }

    /**
     * @param context
     */
    private void stopUNService(Context context) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopUNService context = " + context);
        Intent intent = new Intent(context, UNService.class);
        context.stopService(intent);
        isStartService = false;

    }

    /**
     *
     */
    private void stopUNServiceResp(Context context) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopUNServiceResp()");
        stopUNService(context);
        mMessenger = null;
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 某个Activuity绑定UNService，让Activity与UNService进行通信
     * mServiceConnection包含了Activity想Unservice做的事情
     *
     * @param context
     */
    private void bindUNService(Context context) {
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "bindUNService context = " + context);
        context.bindService(new Intent(context, UNService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * @param context
     */
    public void unBindUNService(Context context) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "unBindUNService context = " + context);
        if (isStartService) {
            try {
                context.unbindService(mServiceConnection);
            } catch (Exception e) {

            }
        }
    }

    /**
     * @param rtnMsg
     */
    private void respIOCtrlMsg(IOCtrlReturnMsg rtnMsg) {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "sendIOCtrlMsgResp() rtnMsg = " + rtnMsg);
        if (isOfflineMode) {
            return;
        }

        if (null != callbackListener) {
            callbackListener.handleUNToolCallback(rtnMsg);
        } else {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "sendIOCtrlMsgResp() callbackListener = NULL");
        }
    }

    private void respDeInitService() {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respDeInitService()");
        stopUNService(context);

        if (callbackListener != null) {
            IOCtrlReturnMsg rtnMsg = new IOCtrlReturnMsg(-1, null, RESP_DEINIT_SUCCESS, null, 0);
            callbackListener.handleUNToolCallback(rtnMsg);
        }

    }

    //handle the message return by service
    private Handler mSerRtnHandler = new Handler() {

        public void handleMessage(Message msg) {
            UNLog.debug_print(UNLog.LV_DEBUG, TAG, "mSerRtnHandler handleMessage msg what = " + msg.what);

            switch (msg.what) {
                case UNService.RESP_CALLBACK_MSG:
                    respIOCtrlMsg((IOCtrlReturnMsg) msg.obj);
                    break;
                case UNService.RESP_DEINIT_SERVICE:
                    respDeInitService();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * Service 向UNTool发消息
     */
    private Messenger mSerMessenger = new Messenger(mSerRtnHandler);


    /**
     * Callback Interface
     */

    public interface UNToolCallbackListener {

        public void handleUNToolCallback(IOCtrlReturnMsg rtnMsg);

    }

    ;


    public static final int NAT_CMD_SET_TIME = 0xA011;

    /**
     * 更新时间
     */
    public void syncTimetoDev() {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "syncTimetoDev()");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String msg = year + " ," +
                month + " ," +
                day + " ," +
                hour + " ," +
                min + " ," +
                sec;
        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "syncTimetoDev() msg = " + msg);

        byte[] data = UNIOCtrlDefs.AW_cdr_set_time.combindContent(year, month, day, hour, min, sec);

        IOCtrlMessage ioMsg = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_SET_TIME, data, UNIOCtrlDefs.AW_cdr_set_time.getTotalSize());
        sendIOCtrlMsg(ioMsg);
    }


    public void sendUploadFile(int sid, String filename) {
        int i = jni_uploadFile(sid, filename);
        Log.e("上传文件=============", "====" + i);
        Log.e("i======" + i, "sid=" + sid + ",filename" + filename);

    }

    /**
     * 固件升级
     */

    public String intToIp(int i) {
        return ((i >> 24) & 0xFF) + "."
                + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    }

    public static final int NAT_CMD_UPDATE_CDR = 0XA029;

    public void update(Context context) {
        //发送升级指令到cdr
        byte[] ipaddress = null;
        byte[] port = null;
        try {
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            ipaddress = intToIp(wifiInfo.getIpAddress()).getBytes("UTF-8"); //把String转byte
            port = "8888".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "handleSendGetThumbNail() path 2 byte[] error!");
            e.printStackTrace();
        }

        IOCtrlMessage msg = new IOCtrlMessage(
                unDevice.getSid(),
                NAT_CMD_UPDATE_CDR,
                UNIOCtrlDefs.AW_cdr_ota_adress.combindContent(ipaddress, port),
                UNIOCtrlDefs.AW_cdr_ota_adress.getTotalSize()
        );
        //旧的OTA升级
        sendIOCtrlMsg(msg);
    }

    /**
     * 发送指令通知设备格式化TF卡
     */
    public void formatTFCard() {
        IOCtrlMessage msg1 = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_FORMAT_TF_CARD, null, 0);
        sendIOCtrlMsg(msg1);
    }

    /**
     * 检测固件完整性
     */
    public void checkSHA1() {
        try {
            String sha1 = FileSafeCode.getSha1(new File(Constant.FEX_PATH));
            sendIOCtrlMsg(new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_CHECK_SHA1, sha1.getBytes(), sha1.getBytes().length));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送指令通知设备检查TF卡
     */
    public void checkTFCard() {
        IOCtrlMessage msg = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_CHECK_TF_CARD, null, 0);
        sendIOCtrlMsg(msg);
    }


    /**
     * 发送指令获取视频列表
     */
    public void getFileList() {
        if (unDevice != null) {
            IOCtrlMessage msg = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_GET_FILE_LIST, null, 0);
            sendIOCtrlMsg(msg);
        }
    }

    /**
     * 获取固件信息
     */
    public void getConfig() {
        IOCtrlMessage msg = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_GET_CONFIG, null, 0);
        sendIOCtrlMsg(msg);
    }

    /**
     * 发送指令通知设备停止或开始录像
     *
     * @param pause true开始  false停止
     */

    public void switchRecord(boolean pause) {
        int n = 0;
        if (pause)
            n = 1;
        IOCtrlMessage msg = new IOCtrlMessage(unDevice.getSid(), UNIOCtrlDefs.NAT_CMD_RECORD_ON_OFF,
                UNIOCtrlDefs.AW_cdr_set_cmd.combindContent(n),
                UNIOCtrlDefs.AW_cdr_set_cmd.getTotalSize());
        UNTool.getInstance().sendIOCtrlMsg(msg);
    }

}
