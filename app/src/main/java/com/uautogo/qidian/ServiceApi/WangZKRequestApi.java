package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.model.AlipayResponse;
import com.uautogo.qidian.model.NewETCResponse;
import com.uautogo.qidian.model.UploadFileResponse;
import com.uautogo.qidian.model.WXRegisterResponse;
import com.uautogo.qidian.model.WXResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WangZKRequestApi {
    private static WangZKRequestApi instance = null;
    private WangZKService service = null;

    private WangZKRequestApi() {
        service = WangZKApi.getInstance().getWangZKService();
    }

    public static WangZKRequestApi getInstance() {
        if (instance == null) {
            synchronized (QidianRequestApi.class) {
                if (instance == null) {
                    instance = new WangZKRequestApi();
                }
            }
        }
        return instance;
    }

    public void uploadEtcPhotos(RequestBody action, MultipartBody.Part files, Callback<UploadFileResponse> callback) {
        Call<UploadFileResponse> upload = service.upload(action, files);
        upload.enqueue(callback);
    }

    public void uploadEtcInfo(Map<String, Object> map, Callback<ResponseBody> callback) {
        Call<ResponseBody> upload = service.uploadEtcInfo(map);
        upload.enqueue(callback);
    }

    public void checkETC(int userId, Callback<NewETCResponse> callback) {
        Call<NewETCResponse> call = service.checkETC(userId);
        call.enqueue(callback);
    }

    public void doPay(int userId, int rid, String channel, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = service.doPay(userId, rid, channel);
        call.enqueue(callback);
    }

    public void payByWX(int userId, int rid, String channel, Callback<WXResponse> callback) {
        Call<WXResponse> call = service.payByWX(userId, rid, channel, "0", 0);
        call.enqueue(callback);
    }

    public void payByAlipay(int userId, int rid, String channel, Callback<AlipayResponse> callback) {
        Call<AlipayResponse> call = service.payByAlipay(userId, rid, channel, "0", 0);
        call.enqueue(callback);
    }


    public void wxRegister(String uid, String mobile, String vcode, Callback<WXRegisterResponse> callback) {
        Call<WXRegisterResponse> call = service.wxRegister(uid, mobile, vcode, 0);
        call.enqueue(callback);
    }

    public void wxLogin(String openId, String unionId, Callback<WXRegisterResponse> callback) {
        Call<WXRegisterResponse> call = service.wxLogin(openId, unionId, 0);
        call.enqueue(callback);

    }

    public void getCertificationCode(String mobile) {
        service.getCertificationCode(mobile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public void getVcode(String mobile) {
        service.getVcode(mobile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getEtcBindVcode(String mobile) {
        service.getEtcBindVcode(mobile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void bindEtc(String mobile, String vcode, Callback<ResponseBody> callback) {
        service.bindEtc(mobile, vcode).enqueue(callback);
    }

    public void queryEtcStatus(String userId, Callback<ResponseBody> callback) {
        service.queryEtcStatus(userId).enqueue(callback);
    }

    public void getFingerOrder(Callback<ResponseBody> callback) {
        service.getFingerOrder().enqueue(callback);
    }

    public void loadOrder(String timeMeberidTransno) {
        service.loadOrder(timeMeberidTransno).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void uploadFirstPage(Map<String, Object> map, Callback<ResponseBody> callback) {
        service.uploadFirstPage(map).enqueue(callback);
    }

    public void uploadSecondPage(Map<String, Object> map, Callback<ResponseBody> callback) {
        service.uploadSecondPage(map).enqueue(callback);
    }

    public void buyEtcByWX(Map<String, String> map, Callback<WXResponse> callback) {
        Call<WXResponse> call = service.buyEtcByWX(map);
        call.enqueue(callback);
    }

    public void buyEtcByAli(Map<String, String> map, Callback<AlipayResponse> callback) {
        Call<AlipayResponse> call = service.buyEtcByAli(map);
        call.enqueue(callback);
    }

}
