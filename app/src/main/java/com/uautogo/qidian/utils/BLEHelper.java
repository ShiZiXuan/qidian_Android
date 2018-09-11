package com.uautogo.qidian.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;



public class BLEHelper extends BluetoothGattCallback implements BluetoothAdapter.LeScanCallback {
    public static final String TAG = "===========";
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private static BLEHelper instance;
    BluetoothDevice mDevice;
    private BluetoothGatt mBluetoothGatt;
    private boolean mInitialized;
    public static final String ACTION_BLUETOOTH_DISCONNECTED = "bluetooth_disconnected";
    public static final String ACTION_BLUETOOTH_CONNECTED = "bluetooth_connected";
    public static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private boolean mConnected;//用于蓝牙断开连接时判断是连接断开还是连接失败，因为二者都会进入BluetoothGatt.STATE_DISCONNECTED分支
    private OnConnectStatusListener mOnConnectStatusListener;

    private boolean scanning = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_LESCAN:
                    stopLeScan();
                    break;
            }
        }
    };

    public static BLEHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (BLEHelper.class) {
                if (instance == null) {
                    instance = new BLEHelper(context);
                }
            }
        }
        return instance;
    }

    public static final int STOP_LESCAN = 0;

    private BLEHelper(Context context) {
        mContext = context;
        mInitialized = initialize();
        if (!mInitialized)
            Toast.makeText(context, "设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
        else {
//            //如果蓝牙没有打开  默认打开  用户不会知道
//            if (!mBluetoothAdapter.isEnabled())
//                mBluetoothAdapter.enable();

            //如果蓝牙没有打开  向用户申请打开蓝牙
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.putExtra(BluetoothAdapter.EXTRA_LOCAL_NAME, "启点车联");
                ((Activity) mContext).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
            } else {
                startLeScan(10000);
            }
        }
    }

    public boolean isEnabled(){
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 初始化并检查设备是否支持蓝牙功能
     *
     * @return 是否支持蓝牙
     */
    private boolean initialize() {
        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
            return false;
        mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager == null)
            return false;
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null)
            return false;

        mInitialized = true;
        return true;
    }

    /**
     * 开始扫描蓝牙，该方法比较消耗资源，所以要指定多久之后停止扫描
     *
     * @param millisToStop 时长
     */
    public void startLeScan(long millisToStop) {
        if (!mInitialized)
            return;
        mBluetoothAdapter.startLeScan(this);
        scanning = true;
        mHandler.sendEmptyMessageDelayed(STOP_LESCAN, millisToStop);
    }

    public void stopLeScan() {
        if (!mInitialized)
            return;
        scanning = false;
        mBluetoothAdapter.stopLeScan(this);
    }

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
        if ("qidian".equals(bluetoothDevice.getName())) {
            stopLeScan();//如果找到设备蓝牙那么停止扫描
            mDevice = bluetoothDevice;
            mBluetoothGatt = mDevice.connectGatt(mContext, false, this);//连接蓝牙
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (newState == BluetoothProfile.STATE_CONNECTED) {//连接成功
            Log.e(TAG, "连接成功");
            mConnected = true;
            mContext.sendBroadcast(new Intent(BLEHelper.ACTION_BLUETOOTH_CONNECTED));
            if (mOnConnectStatusListener != null)//连接成功  回调
                mOnConnectStatusListener.onBLEDeviceConnected();
        } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
            if (mConnected) {//mConnected用于区分连接断开还是连接失败
                if (mOnConnectStatusListener != null)//连接断开时  回调
                    mOnConnectStatusListener.onBLEDeviceDisconnected();
                mConnected = false;
            } else {
                Log.e(TAG, "连接失败,尝试重新连接");
            }
            if (!scanning){
                if (status == 0)
                    gatt.connect();
                else
                    mBluetoothGatt = mDevice.connectGatt(mContext, false, this);
            }
        }
    }

    public void setOnConnectStatusListener(OnConnectStatusListener listener) {
        this.mOnConnectStatusListener = listener;
    }

    public void closeConnection() {
        if (!mInitialized)
            return;
        if (mBluetoothGatt != null)
            mBluetoothGatt.close();
    }

    public interface OnConnectStatusListener {
        void onBLEDeviceConnected();

        void onBLEDeviceDisconnected();
    }

    public boolean isConnected() {
        return mConnected;
    }

    public void onDestory() {
        if (!mInitialized)
            return;
        if (mBluetoothGatt != null)
            mBluetoothGatt.close();
        mContext = null;
        mOnConnectStatusListener = null;
    }

    public void setConnected(boolean b){
        mConnected = b;
    }
}
