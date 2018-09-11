package com.uautogo.qidian.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;

/**
 * Created by Serenade on 2017/10/8.
 */

public class LocationHelper implements AMapLocationListener{
    private static LocationHelper instance;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
//    private  TTSController mTTSController;
    private Context mContext;

    public static double mLongitude;//经度
    public static double mLatitude;//纬度

    private boolean save;//是否保存经纬度
    private boolean show;//是否启动导航

    private LocationHelper(Context context) {
        mContext = context;
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Bahttery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需;调用stopLocation()方法移除请求，定位sdk内部会移除
//        mTTSController=TTSController.getInstance(context.getApplicationContext());
//        mTTSController.init();
    }

    public static LocationHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (LocationHelper.class) {
                if (instance == null) {
                    instance = new LocationHelper(context);
                }
            }
        }
        return instance;
    }

    public void startLocation(boolean save) {
        this.save = save;
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                double latitude = amapLocation.getLatitude();//获取纬度
//                double longitude = amapLocation.getLongitude();//获取经度
//                Log.e("latitude=====" + latitude, ",longitude======" + longitude);
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间

                mLatitude = amapLocation.getLatitude();//获取纬度
                mLongitude = amapLocation.getLongitude();//获取经度
                Log.e("latitude=====" + mLatitude, ",longitude======" + mLongitude);

                if (save) {
                    SharedPreferencesUtils.putString(mContext, SharedPreferencesUtils.Key.KEY_CAR_LONGITUDE, "" + mLongitude);
                    SharedPreferencesUtils.putString(mContext, SharedPreferencesUtils.Key.KEY_CAR_LATITUDE, "" + mLatitude);
                    Log.e("记录位置成功=========", mLatitude + "," + mLongitude);
                    save = false;
                    mContext.sendBroadcast(new Intent(BLEHelper.ACTION_BLUETOOTH_DISCONNECTED));
                }

                if (show) {
                    String longitude = SharedPreferencesUtils.getString(mContext, SharedPreferencesUtils.Key.KEY_CAR_LONGITUDE);
                    String latitude = SharedPreferencesUtils.getString(mContext, SharedPreferencesUtils.Key.KEY_CAR_LATITUDE);
                    if (TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)) {
                        ToastUtil.show(mContext.getApplicationContext(), "没有记录过车的位置");
                        return;
                    }
                    double car_latitude = Double.parseDouble(latitude);
                    double car_longitude = Double.parseDouble(longitude);

                    Poi start = new Poi("我的位置", new LatLng(LocationHelper.mLatitude+0.00001, LocationHelper.mLongitude+0.00001), "");
                    Poi end = new Poi("车的位置", new LatLng(car_latitude, car_longitude), "");
//                    AmapNaviPage.getInstance().showRouteActivity(mContext, new AmapNaviParams(start, null, end, AmapNaviType.WALK, AmapPageType.ROUTE), mTTSController);
                    show = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                if (show){
                    ToastUtil.show(mContext.getApplicationContext(), "导航启动失败");
                }
            }
        }
    }

    public void startGuide() {
        if (!show) {
            startLocation(false);
            ToastUtil.show(mContext.getApplicationContext(), "正在启动导航");
            show = true;
        }
    }

}