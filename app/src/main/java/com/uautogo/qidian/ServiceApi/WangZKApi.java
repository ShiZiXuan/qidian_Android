package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.BuildConfig;
import com.uautogo.qidian.data.GsonHelper;
import com.uautogo.qidian.data.OkHttpManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class WangZKApi {

    private static final String QIDIAN_HOST_DEBUG = "https://etc.uautogo.com/";
    private static final String QIDIAN_HOST_RELEASE = "https://etc.uautogo.com/";
    private static volatile WangZKApi mInstance;
    private WangZKService mWangZKService;

    private WangZKApi() {
        initService();
    }

    public static WangZKApi getInstance() {
        if (mInstance == null) {
            synchronized (QidianApi.class) {
                if (mInstance== null) {
                    mInstance = new WangZKApi();
                }
            }
        }
        return mInstance;
    }

    protected String getHost() {
        if (BuildConfig.DEBUG){
             return QIDIAN_HOST_DEBUG;
        }
        return QIDIAN_HOST_RELEASE;
    }

    protected void initService() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(
                GsonConverterFactory.create(GsonHelper.getInstance()))
                .client(OkHttpManager.getInstance().getNormalClient())
                .baseUrl(getHost())
                .build();
        mWangZKService = retrofit.create(WangZKService.class);
    }

    public WangZKService getWangZKService() {
        return mWangZKService;
    }
}
