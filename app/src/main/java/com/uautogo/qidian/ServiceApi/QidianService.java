package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.model.AppUpdateRespons;
import com.uautogo.qidian.model.BaseResponse;
import com.uautogo.qidian.model.CarInfo;
import com.uautogo.qidian.model.CodeRespons;
import com.uautogo.qidian.model.EtcInfoResponse;
import com.uautogo.qidian.model.EtcResponse;
import com.uautogo.qidian.model.LoginRespons;
import com.uautogo.qidian.model.NewETCResponse1;
import com.uautogo.qidian.model.OrderRespons;
import com.uautogo.qidian.model.UpdateRespons;
import com.uautogo.qidian.model.UploadFileResponse;

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
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface QidianService {
    @POST("/api/user/login")
    @FormUrlEncoded
    Call<LoginRespons> doLogin(@Field("mobile") String mobile, @Field("vcode") String code);

    @POST("/api/user/vcode")
    @FormUrlEncoded
    Call<CodeRespons> requestCode(@Field("mobile") String mobile);

    @POST("/api/user/vcode")
    @FormUrlEncoded
    Call<NewETCResponse1> getETCList(@Field("userId") int id, @Field("type") int type, @Field("pn") int pn, @Field("pSize") int pSize);

    @POST("/api/cars/addCar")
    @FormUrlEncoded
    Call<CarInfo> uploadCarInfo(@Field("userId") String userId, @Field("provinceCode") String provinceCode, @Field("engineNo") String engineNo, @Field("vin") String vin, @Field("hphm") String hphm);

    @GET("/api/buy/orders")
    Call<OrderRespons> getOrders(@Query("id") int id);

    @GET("/api/device/checkUpgrade")
    Call<UpdateRespons> checkUpdate(@Query("versionNo") String version);

    @GET("/api/app/checkUpgrade")
    Call<AppUpdateRespons> checkAppUpdate(@Query("versionCode") int versionCode);

    @POST("/api/buy/repay")
    @FormUrlEncoded
    Call<ResponseBody> rePay(@Field("orderId") int orderId);

    @GET("/api/app/checkUpgrade")
    Call<AppUpdateRespons> checkAppUpdate(@Query("versionCode") int versionCode, @Query("channelId") String channelId);

    @POST("/api/buy/xcjly")
    @FormUrlEncoded
    Call<ResponseBody> doPay(@Field("uid") int uid
            , @Field("itemIds") String itemIds
            , @Field("channel") String channel
            , @Field("num") int num
            , @Field("receiverName") String receiverName
            , @Field("receiverMobile") String receiverMobile
            , @Field("receiverProvince") String receiverProvince
            , @Field("receiverCity") String receiverCity
            , @Field("receiverDistrict") String receiverDistrict
            , @Field("receiverAddress") String receiverAddress);

    //退款接口
    @POST("/api/customer/add")
    @FormUrlEncoded
    Call<ResponseBody> refund(@Field("userId") Integer userId, @Field("orderId") Integer orderId);

    @GET("/api/cars/userId/{id}")
    Call<CarInfo> queryAllCars(@Path("id") String id);

    @GET("/api/etc/list")
    Call<EtcResponse> checkEtc(@Query("userId") Integer userId);


    @POST("/api/etc/pay")
    @FormUrlEncoded
    Call<ResponseBody> etcPay(@Field("orderId") Integer orderId,@Field("channel") String  channel);

    /**
     * ETC照片上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("api/file/upload")
    Call<UploadFileResponse> upload(@Part("action") RequestBody action, @Part MultipartBody.Part file);

    /**
     * 新增一个申请信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/etc/certificate/add")
    Call<BaseResponse> addEtcInfo(@FieldMap Map<String, Object> map);


    /**
     * 更新一个申请信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/etc/certificate/update")
    Call<BaseResponse> updateEtcInfo(@FieldMap Map<String, Object> map);

    /**
     * 查询用户的申请信息
     *
     * @param userId
     * @return
     */
    @GET("api/etc/certificate/select")
    Call<EtcInfoResponse> getEtcInfo(@Query("userId") int userId);

    /**
     * 删除一个申请信息
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("api/etc/certificate/delete")
    Call<BaseResponse> delepayorderteEtcInfo(@Field("id") int id);
}