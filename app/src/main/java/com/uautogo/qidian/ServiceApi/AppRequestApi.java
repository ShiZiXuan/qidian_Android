package com.uautogo.qidian.ServiceApi;

import com.uautogo.qidian.data.ResultCallback;
import com.uautogo.qidian.model.CarQueryModel;
import com.uautogo.qidian.model.WeatherData;
import com.uautogo.qidian.model.XianXingData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by chuangmeng on 2016/9/6.
 */
public class AppRequestApi {

  private static AppRequestApi instance = null;
  private AppService liveService = null;

  private AppRequestApi() {
    liveService = AppApi.getInstance().getCarService();
  }

  public static AppRequestApi getInstance() {
    if (instance == null) {
      synchronized (AppRequestApi.class) {
        if (instance == null) {
          instance = new AppRequestApi();
        }
      }
    }
    return instance;
  }


  public void queryCarIllegal(String dtype, String city, String hphm, String hpzl, String engineno, String classno, String key, Callback<CarQueryModel> callback) {
    Call<CarQueryModel> queryCall =
            liveService.queryCarIllegal(dtype, city, hphm, hpzl, engineno, classno, key);
    queryCall.enqueue(callback);
  }

  public void queryWeather(String ip,String key,ResultCallback<WeatherData> callback) {
    Call<WeatherData> queryCall =
            liveService.queryWeather(ip, key);
    queryCall.enqueue(callback);
  }

  public void queryXianxing(String key,ResultCallback<XianXingData> callback) {
    Call<XianXingData> queryCall =
            liveService.queryLimit("beijing", key);
    queryCall.enqueue(callback);
  }

  public void queryCitys(String key,ResultCallback<ResponseBody> callback) {
    Call<ResponseBody> queryCall =
            liveService.queryCitys(key);
    queryCall.enqueue(callback);
  }


  public void queryWeatherByCity(String cityname,String key,ResultCallback<WeatherData> callback) {
    Call<WeatherData> queryCall =
            liveService.queryWeatherByCity(cityname, key);
    queryCall.enqueue(callback);
  }

  public void queryIp(String url,Callback<ResponseBody> callback){
    Call<ResponseBody> ip = liveService.queryIp(url);
    ip.enqueue(callback);
  }


//
//  public void getUserExtraInfo(String userId, ResultCallback<UserOtherInfo> callback) {
//    SiteApi.getInstance().getUserExtraInfo(userId).enqueue(callback);
//  }
//
////  public void createChatRoom(Map<String, String> map, ResultCallback<LiveRoomModel> callback) {
////    Call<LiveRoomModel> createRoom = liveService.createChatRoomRemote(map);
////    createRoom.enqueue(callback);
////  }
//
//  public void checkUserValid(String siteId, String phone, ResultCallback<ValidRoom> callback) {
//    Call<ValidRoom> createRoom = liveService.checkUserLive(siteId, phone);
//    createRoom.enqueue(callback);
//  }
//
//  public void checkSiteValid(String siteId, ResultCallback<ValidRoom> callback) {
//    Call<ValidRoom> createRoom = liveService.checkSiteLive(siteId);
//    createRoom.enqueue(callback);
//  }
//
////  public void getAuthToken(String siteId, ResultCallback<ResponseBody> callback) {
////    Call<ResponseBody> getAuth = liveService.getAuthToken(siteId);
////    getAuth.enqueue(callback);
////  }
//
////  public void getSiteLiveInfo(String siteId, String timestamp,
////      ResultCallback<LiveSiteInfo> callback) {
////    Call<LiveSiteInfo> getAuth = liveService.getSiteLiveInfo(siteId, timestamp);
////    getAuth.enqueue(callback);
////  }
//
////  public void getUserInfo(String username, String timestamp, ResultCallback callback) {
////    Call<LiveUserInfo> call = liveService.getUserInfo(username, timestamp);
////    call.enqueue(callback);
////  }
//
//  public void createLiveSteam(String siteId, String liveType, ResultCallback<LiveStream> callback){
//    Call<LiveStream> call = liveService.createSteam(siteId,liveType);
//    call.enqueue(callback);
//  }
//
//  public void sendAckToServer(String siteId, String streamName, String status, ResultCallback<AckResponse> callback){
//    Call<AckResponse> call = liveService.sendAck(siteId,streamName,status);
//    call.enqueue(callback);
//  }
//
//  public void createChatRoom(String siteId, String ownerId, String streamName, ResultCallback<ChatRoomModel> callback){
//    Call<ChatRoomModel> call = liveService.createChatRoom(siteId,ownerId,streamName);
//    call.enqueue(callback);
//  }
//
//  public void getChatRoomMember(String roomId, String siteId, ResultCallback<List<String>> callback){
//    Call<List<String>> call = liveService.getMemberList(roomId,siteId);
//    call.enqueue(callback);
//  }
//
//  public void userJoin(String siteId, String roomId, String memberId, ResultCallback<Void> callback){
//    Call<Void> call = liveService.onUserJoin(siteId,roomId,memberId);
//    call.enqueue(callback);
//  }
//
//  public void userLeave(String siteId, String roomId, String memberId, ResultCallback<Void> callback){
//    Call<Void> call = liveService.onUserLeave(siteId,roomId,memberId);
//    call.enqueue(callback);
//  }

}
