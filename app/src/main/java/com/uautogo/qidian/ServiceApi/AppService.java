package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.model.CarQueryModel;
import com.uautogo.qidian.model.WeatherData;
import com.uautogo.qidian.model.XianXingData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by Jeremy on 2016/9/8.
 */
public interface AppService {
//    http://v.juhe.cn/wz/query?dtype=JSON&callback=&city=NX_YINCHUAN&hphm=%E5%AE%81A79A29&hpzl=02&engineno=8AC0110528&classno=LZWCAAGA7A2143139&key=223f1f127776fceb06ba035ff7488f6b
    @GET("/wz/query")
    Call<CarQueryModel> queryCarIllegal(@Query("dtype") String dtype,@Query("city") String city,@Query("hphm") String hphm,@Query("hpzl") String hpzl,@Query("engineno") String engineno,@Query("classno") String classno,@Query("key") String key);

    @GET("/weather/ip")
    Call<WeatherData> queryWeather(@Query("ip") String ip, @Query("key") String key);


    @GET("/xianxing/index")
    Call<XianXingData> queryLimit(@Query("city") String city, @Query("key") String key);

    @GET("/wz/citys")
    Call<ResponseBody> queryCitys(@Query("key") String key);

    @GET("/weather/index")
    Call<WeatherData> queryWeatherByCity(@Query("cityname") String cityname, @Query("key") String key);

    @GET
    Call<ResponseBody> queryIp(@Url String url);

//  @GET("/easemob/user/getUser") Call<LiveUserInfo> getUserInfo(@Query("userId") String username,
//      @Query("ts") String timestamp);
    //
    //  @GET("/app/config/share/{site_id}") Call<ShareConfig> getShareConfig(
    //          @Path("site_id") String siteId, @Query("share") String share);
    //
    //  @GET("/app/config/share-info/{site_id}") Call<ShareInfo> getShareInfo(
    //          @Path("site_id") String siteId);
    //

//  @GET("/qupaicloud/spaceInfo") Call<LiveSiteInfo> getSiteLiveInfo(@Query("siteId") String siteId,
//      @Query("ts") String timestamp);
//
//  @GET("/qupaicloud/getAuthKey") Call<ResponseBody> getAuthToken(@Query("siteId") String siteId);

//    @GET("/live/store/zhibo/validPhone")
//    Call<ValidRoom> checkUserLive(@Query("siteId") String siteId,
//                                  @Query("phone") String phone);
//
//    @GET("/live/store/zhibo/validSite")
//    Call<ValidRoom> checkSiteLive(@Query("siteId") String siteId);
//
//      @POST("/easemob/room/createChatRoom") @FormUrlEncoded
//      Call<CarQueryModel> queryCarIllegal(
//      @FieldMap Map<String, String> map);
//    @POST("/live/store/stream/ack")
//    @FormUrlEncoded
//    Call<AckResponse> sendAck(@Field("siteId") String siteId, @Field("streamName") String streamName, @Field("status") String status);
//
//    @POST("/live/store/stream/create")
//    @FormUrlEncoded
//    Call<LiveStream> createSteam(@Field("siteId") String siteId, @Field("liveType") String liveType);
//    //
    //  @GET("/app/config/notice/{site_id}") Call<Notice> getNotice(@Path("site_id") String siteId);
    //
    //  @GET("/app/config/push/{site_id}") Call<PushConfig> getPushConfig(@Path("site_id") String siteId);

    /*
    Method Post
  Path /live/store/chat/room/create
  Params siteId, ownerId, streamName
     */
//    @POST("/live/store/chat/room/create")
//    @FormUrlEncoded
//    Call<ChatRoomModel> createChatRoom(@Field("siteId") String siteId, @Field("ownerId") String ownerId, @Field("streamName") String streamName);
//
//    @GET("/live/store/chat/room/members")Call<List<String>> getMemberList(@Query("roomId") String roomId, @Query("siteId") String siteId);
//    @POST("/live/store/chat/room/join")@FormUrlEncoded Call<Void>onUserJoin(@Field("siteId") String siteId, @Field("roomId") String roomId, @Field("memberId") String memberId);
//    @POST("/live/store/chat/room/leave")@FormUrlEncoded Call<Void>onUserLeave(@Field("siteId") String siteId, @Field("roomId") String roomId, @Field("memberId") String memberId);
}