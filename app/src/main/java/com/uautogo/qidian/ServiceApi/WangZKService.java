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
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface WangZKService {
    /**
     * ETC照片上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("upload/upload.action")
    Call<UploadFileResponse> upload(@Part("action") RequestBody action, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/etcInfo/addEtcInfo")
    Call<ResponseBody> uploadEtcInfo(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("api/record/checkBankRecord")
    Call<NewETCResponse> checkETC(@Field("userId") int userId);

    @FormUrlEncoded
    @POST("api/etcbuy/pingpay")
    Call<ResponseBody> doPay(@Field("userId") int userId, @Field("rid") int rid, @Field("channel") String channel);

    @FormUrlEncoded
    @POST("api/etcbuy/pay")
    Call<WXResponse> payByWX(@Field("userId") int userId, @Field("rid") int rid, @Field("channel") String channel,@Field("openId") String openId,@Field("type") int type);

    @FormUrlEncoded
    @POST("api/etcbuy/pay")
    Call<AlipayResponse> payByAlipay(@Field("userId") int userId, @Field("rid") int rid, @Field("channel") String channel,@Field("openId") String openId,@Field("type") int type);

    @FormUrlEncoded
    @POST("api/user/wechatRegister")
    Call<WXRegisterResponse> wxRegister(@Field("uid") String uid, @Field("mobile") String mobile, @Field("vcode") String vcode, @Field("type") int type);
    @FormUrlEncoded
    @POST("api/user/wechatLogin")
    Call<WXRegisterResponse> wxLogin(@Field("openId") String openId, @Field("unionId") String unionId, @Field("type") int type);

    @FormUrlEncoded
    @POST("api/user/wechatRegisterVcode")
    Call<ResponseBody> getVcode(@Field("mobile") String mobile);

    @GET("api/etcInfo/getbankVerifiCode")
    Call<ResponseBody> getCertificationCode(@Query("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/etcInfo/getMobile")
    Call<ResponseBody> getEtcBindVcode(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("api/etcInfo/getMobileSignVcode")
    Call<ResponseBody> bindEtc(@Field("mobile") String mobile, @Field("vcode") String vcode);

    @FormUrlEncoded
    @POST("api/etcInfo/isHandleSuccess")
    Call<ResponseBody> queryEtcStatus(@Field("userId") String userId);

    @POST("api/baofoo/getFingerOrder")
    Call<ResponseBody> getFingerOrder();

    @FormUrlEncoded
    @POST("api/baofoo/loadOrder")
    Call<ResponseBody> loadOrder(@Field("timeMeberidTransno") String timeMeberidTransno);

    @FormUrlEncoded
    @POST("api/etcInfo/insertEtcUserInfo")
    Call<ResponseBody> uploadFirstPage(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("api/etcInfo/insertEtctravelInfo")
    Call<ResponseBody> uploadSecondPage(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("api/etcbuy/payEquipment")
    Call<WXResponse> buyEtcByWX(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("api/etcbuy/payEquipment")
    Call<AlipayResponse> buyEtcByAli(@FieldMap Map<String, String> map);
}
