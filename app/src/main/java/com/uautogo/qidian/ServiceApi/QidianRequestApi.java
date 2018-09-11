package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.model.AppUpdateRespons;
import com.uautogo.qidian.model.BaseResponse;
import com.uautogo.qidian.model.CarInfo;
import com.uautogo.qidian.model.CodeRespons;
import com.uautogo.qidian.model.EtcInfoResponse;
import com.uautogo.qidian.model.EtcResponse;
import com.uautogo.qidian.model.LoginRespons;
import com.uautogo.qidian.model.OrderRespons;
import com.uautogo.qidian.model.UpdateRespons;
import com.uautogo.qidian.model.UploadFileResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by linjing on 2016/9/6.
 */
public class QidianRequestApi {

  private static QidianRequestApi instance = null;
  private QidianService qidianService = null;

  private QidianRequestApi() {
    qidianService = QidianApi.getInstance().getQidianService();
  }

  public static QidianRequestApi getInstance() {
    if (instance == null) {
      synchronized (QidianRequestApi.class) {
        if (instance == null) {
          instance = new QidianRequestApi();
        }
      }
    }
    return instance;
  }

  public void doLogin(String mobile, String code, Callback<LoginRespons> callback) {
    Call<LoginRespons> queryCall = qidianService.doLogin(mobile, code);
    queryCall.enqueue(callback);
  }

  public void requestCode(String mobile, Callback<CodeRespons> callback) {
    Call<CodeRespons> queryCall = qidianService.requestCode(mobile);
    queryCall.enqueue(callback);
  }

  public void checkUpdate(String version, Callback<UpdateRespons> callback) {
    Call<UpdateRespons> queryCall = qidianService.checkUpdate(version);
    queryCall.enqueue(callback);
  }

  public void uploadCarInfo(String userId, String provinceCode, String engineNo, String vin, String hphm, Callback<CarInfo> callback) {
    Call<CarInfo> carInfoCall = qidianService.uploadCarInfo(userId, provinceCode, engineNo, vin, hphm);
    carInfoCall.enqueue(callback);
  }

  public void doPay(int uid, String itemIds, String channel, int num, String receiverName, String receiverMobile, String receiverProvince, String receiverCity, String receiverDistrict, String receiverAddress, Callback<ResponseBody> callback) {
    Call<ResponseBody> doPayCall = qidianService.doPay(uid, itemIds, channel, num, receiverName, receiverMobile, receiverProvince,
            receiverCity, receiverDistrict, receiverAddress);
    doPayCall.enqueue(callback);
  }

  public void getOrders(int id, Callback<OrderRespons> callback) {
    Call<OrderRespons> orders = qidianService.getOrders(id);
    orders.enqueue(callback);
  }

  public void checkAppUpdate(int versionCode, Callback<AppUpdateRespons> callback) {
    Call<AppUpdateRespons> updateResponsCall = qidianService.checkAppUpdate(versionCode);
    updateResponsCall.enqueue(callback);
  }

  public void rePay(int orderId, Callback<ResponseBody> callback) {
    Call<ResponseBody> rePay = qidianService.rePay(orderId);
    rePay.enqueue(callback);
  }

  public void checkAppUpdate(int versionCode, String channelId, Callback<AppUpdateRespons> callback) {
    Call<AppUpdateRespons> updateResponsCall = qidianService.checkAppUpdate(versionCode, channelId);
    updateResponsCall.enqueue(callback);
  }

  //退款接口
  public void refund(Integer userId, Integer orderId, Callback<ResponseBody> callback) {
    Call<ResponseBody> refund = qidianService.refund(userId, orderId);
    refund.enqueue(callback);
  }

  public void queryAllCars(String uid, Callback<CarInfo> callback) {
    Call<CarInfo> allCars = qidianService.queryAllCars(uid);
    allCars.enqueue(callback);
  }

  public void checkEtc(int userId, Callback<EtcResponse> callback) {
    Call<EtcResponse> allCars = qidianService.checkEtc(userId);
    allCars.enqueue(callback);
  }

  public void etcPay(Integer orderId, String channel, Callback<ResponseBody> callback) {
    Call<ResponseBody> etcPay = qidianService.etcPay(orderId, channel);
    etcPay.enqueue(callback);
  }

  public void uploadEtcPhotos(RequestBody action, MultipartBody.Part files, Callback<UploadFileResponse> callback) {
    Call<UploadFileResponse> upload = qidianService.upload(action, files);
    upload.enqueue(callback);
  }

  public void addEtcInfo(int userId, List<String> photosParam, Callback<BaseResponse> callback) {
    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("idCardFrontImg", photosParam.get(0));
    map.put("idCardBackImg", photosParam.get(1));
    map.put("driverLicenseImg", photosParam.get(2));
    map.put("vtlOriginalImg", photosParam.get(3));
    map.put("vtlCopyImg", photosParam.get(4));
    map.put("bankCardImg", photosParam.get(5));
    Call<BaseResponse> baseResponseCall = qidianService.addEtcInfo(map);
    baseResponseCall.enqueue(callback);
  }

  public void updateEtcInfo(int id, List<String> selectmag, Callback<BaseResponse> callback) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("idCardFrontImg", selectmag.get(0));
    map.put("idCardBackImg", selectmag.get(1));
    map.put("driverLicenseImg", selectmag.get(2));
    map.put("vtlOriginalImg", selectmag.get(3));
    map.put("vtlCopyImg", selectmag.get(4));
    map.put("bankCardImg", selectmag.get(5));
    Call<BaseResponse> baseResponseCall = qidianService.updateEtcInfo(map);
    baseResponseCall.enqueue(callback);
  }

  public void deleteEtcInfo(int id, Callback<BaseResponse> callback) {
    Call<BaseResponse> del = qidianService.delepayorderteEtcInfo(id);
    del.enqueue(callback);
  }

  public void getEtcInfo(Integer userId, Callback<EtcInfoResponse> callback) {
    Call<EtcInfoResponse> resp = qidianService.getEtcInfo(userId);
    resp.enqueue(callback);
  }
}
