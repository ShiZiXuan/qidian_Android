package com.uautogo.qidian.data;

import android.content.Context;
import android.util.Log;

import com.uautogo.qidian.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpManager {

  private static final int DEFAULT_NORMAL_CACHE_SIZE = 50;//50MB

  private static volatile OkHttpManager sInstance;
  private OkHttpClient mNormalClient;
  private static final List<Protocol> protocols = Util.immutableList(Protocol.HTTP_1_1);

  private OkHttpManager(Context context) {

    String cacheDir = context.getCacheDir().getAbsolutePath();
    String okCacheDir = cacheDir + "/okhttp";
    File file = new File(okCacheDir);
    if (!file.exists() && !file.mkdir()) {
      file = new File(cacheDir);
    }
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
      @Override
      public void log(String message) {
        Log.w("================", "" + message);
      }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    mNormalClient = new OkHttpClient.Builder().retryOnConnectionFailure(false)
            .addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Connection", "close");
                return chain.proceed(builder.build());
              }

            })

            .addInterceptor(loggingInterceptor)
            .cache(new Cache(file, DEFAULT_NORMAL_CACHE_SIZE * 1024 * 1024))
            .protocols(protocols)
            .build();
  }

  public static OkHttpManager getInstance() {
    if (null == sInstance) {
      synchronized (OkHttpManager.class) {
        if (null == sInstance) {
          sInstance = new OkHttpManager(MyApplication.getInstance());
        }
      }
    }
    return sInstance;
  }


  public OkHttpClient getNormalClient() {
    return mNormalClient;
  }

}