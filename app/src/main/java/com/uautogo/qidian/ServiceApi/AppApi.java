package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.data.GsonHelper;
import com.uautogo.qidian.data.OkHttpManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApi {

  private static final String JUHE_HOST = "http://v.juhe.cn";
  private static volatile AppApi mInstance;
  private AppService mCarService;

  private AppApi() {
    initService();
  }

  public static AppApi getInstance() {
    if (mInstance == null) {
      synchronized (AppApi.class) {
        if (mInstance == null) {
          mInstance = new AppApi();
        }
      }
    }
    return mInstance;
  }


  protected String getHost() {
    return JUHE_HOST;
  }

  protected void initService() {
    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(
        GsonConverterFactory.create(GsonHelper.getInstance()))
        .client(OkHttpManager.getInstance().getNormalClient())
        .baseUrl(getHost())
        .build();
    mCarService = retrofit.create(AppService.class);
  }

  public AppService getCarService() {
    return mCarService;
  }
}
