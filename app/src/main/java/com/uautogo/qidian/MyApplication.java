package com.uautogo.qidian;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.softwinner.un.tool.ApplicationProxy;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Jeremy on 2017/5/13.
 */

public class MyApplication extends Application {
    private IWXAPI mWXApi;

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static MyApplication instance = null;
    private static String UM_SERECT = "593284f104e205086d001752";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ApplicationProxy.getInstance().setApplication(this);
//        UMConfigure.setLogEnabled(true);
//        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, UM_SERECT, "official"));

        UMShareAPI.get(this);

        UMConfigure.init(this,"","",UMConfigure.DEVICE_TYPE_PHONE,"");


/**
 * 设置wifi与
 * 3g同步
 */
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            final ConnectivityManager cm;
//            cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkRequest.Builder req = new NetworkRequest.Builder();
//            req.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
//            req.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
//            cm.requestNetwork(req.build(), new ConnectivityManager.NetworkCallback() {
//                @TargetApi(Build.VERSION_CODES.M)
//                @Override
//                public void onAvailable(Network network) {
//                    //here you can use bindProcessToNetwork
////                    cm.bindProcessToNetwork(network);
//                    socketFactory = network.getSocketFactory();
//                }
//
//            });
//        }

    }

    {
        PlatformConfig.setWeixin("wxf9a97962ab078a3a", "765349d605be1da004b0eb44b93388d5");
        PlatformConfig.setSinaWeibo("3790241706", "e4bd1c198320243be1fe8db4f06b7f2e", "https://sns.whalecloud.com/sina2/callback");
    }

    public static MyApplication getInstance() {
        return instance;
    }
}