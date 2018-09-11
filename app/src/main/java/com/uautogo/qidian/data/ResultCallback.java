package com.uautogo.qidian.data;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultCallback<T> implements Callback<T> {

  @Override
  public void onResponse(Call<T> call, Response<T> response) {
    if (response.isSuccessful()) {
      onSuccess(response.body());
    } else {
      onResponseFailure(response);
    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable t) {
    onRequestFailure(t);
  }

  /** 发送请求失败 */
  protected void onRequestFailure(Throwable t) {
    Log.e("ResultCallback","onRequestFailure");
  }

  /** 服务器返回错误 */
  protected void onResponseFailure(Response<T> response) {
    onResponseFailure(response);
  }

  /** 请求成功 */
  protected void onSuccess(T data) {
  }

}
