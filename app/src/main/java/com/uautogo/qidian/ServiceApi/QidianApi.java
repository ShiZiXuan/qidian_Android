package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.BuildConfig;
import com.uautogo.qidian.data.GsonHelper;
import com.uautogo.qidian.data.OkHttpManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QidianApi {
//https://www.uautogo.com/tomqd/

    private static final String QIDIAN_HOST_DEBUG = "http://api.uautogo.com/";
    //"http://219.234.181.235:9000";
    private static final String QIDIAN_HOST_RELEASE = "http://api.uautogo.com/";
    private static volatile QidianApi mInstance;
    private QidianService mQidianService;

    private QidianApi() {
        initService();
    }

    public static QidianApi getInstance() {
        if (mInstance == null) {
            synchronized (QidianApi.class) {
                if (mInstance == null) {
                    mInstance = new QidianApi();
                }
            }
        }
        return mInstance;
    }

    protected String getHost() {
        if (BuildConfig.DEBUG) {
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
        mQidianService = retrofit.create(QidianService.class);
    }

    public QidianService getQidianService() {
        return mQidianService;
    }
}
