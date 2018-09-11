package com.softwinner.un.tool.service;

import android.content.Context;

import com.softwinner.un.tool.ApplicationProxy;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Dispatcher;
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
  private OkHttpClient mPageClient;
  private OkHttpClient mNormalClient;
  private static final List<Protocol> protocols = Util.immutableList(Protocol.HTTP_1_1);

  private OkHttpManager(Context context) {
    Dispatcher dispatcher = new Dispatcher();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    dispatcher.setMaxRequestsPerHost(20);
    mPageClient = new OkHttpClient.Builder().retryOnConnectionFailure(false)
        .dispatcher(dispatcher)
        .followRedirects(false)
        .followSslRedirects(false)
        .build();

    String cacheDir = context.getCacheDir().getAbsolutePath();
    String okCacheDir = cacheDir + "/okhttp";
    File file = new File(okCacheDir);
    if (!file.exists() && !file.mkdir()) {
      file = new File(cacheDir);
    }
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
        .addInterceptor(loggingInterceptor)
        .cache(new Cache(file, DEFAULT_NORMAL_CACHE_SIZE * 1024 * 1024))
        .protocols(protocols)
        .build();
  }

  public static OkHttpManager getInstance() {
    if (null == sInstance) {
      synchronized (OkHttpManager.class) {
        if (null == sInstance) {
          sInstance = new OkHttpManager(ApplicationProxy.getInstance().getApplication());
        }
      }
    }
    return sInstance;
  }

  public static void init() {
    getInstance();
  }

  public OkHttpClient getPageClient() {
    return mPageClient;
  }

  public OkHttpClient getNormalClient() {
    return mNormalClient;
  }

  /**
   * 下载文件，子线程中调用
   */
  public void downloadFile(String url, File file, OnFileDownloadListener listener) {
    Request request = new Request.Builder().url(url).build();
    try {
      Response response = mNormalClient.newCall(request).execute();
      saveFile(response, file, listener);
    } catch (IOException e) {
      e.printStackTrace();
      if (listener != null) {
        listener.onFail();
      }
    }
  }

  // 保存文件
  private void saveFile(Response response, File file, OnFileDownloadListener listener)
      throws IOException {
    InputStream inStream = null;
    byte[] buffer = new byte[2048];
    int length = 0;
    FileOutputStream outStream = null;
    try {
      inStream = response.body().byteStream();
      long total = response.body().contentLength();
      long sum = 0;

      if (!file.exists()) {
        file.createNewFile();
      }

      outStream = new FileOutputStream(file);
      while ((length = inStream.read(buffer)) != -1) {
        sum += length;
        outStream.write(buffer, 0, length);

        if (listener != null) {
          listener.onProgress((int) (sum * 100 / total));
        }
      }
      outStream.flush();

      if (listener != null) {
        listener.OnSuccess();
      }
    } catch (Exception e) {
      if (listener != null) {
        listener.onFail();
      }
    } finally {
      closeQuietly(inStream);
      closeQuietly(outStream);
    }
  }

  public static void closeQuietly(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  public interface OnFileDownloadListener {
    void onProgress(int progress);// 0~100

    void OnSuccess();

    void onFail();
  }
}