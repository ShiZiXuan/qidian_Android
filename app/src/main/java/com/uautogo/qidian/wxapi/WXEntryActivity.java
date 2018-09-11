package com.uautogo.qidian.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.MainActivity;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.BindPhoneActivity;
import com.uautogo.qidian.model.WXRegisterResponse;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.WXAccessTokenEntity;
import com.uautogo.qidian.utils.WXBaseRespEntity;
import com.uautogo.qidian.utils.WXUserInfo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * description ：
 * project name：CCloud
 * author : Vincent
 * creation date: 2017/6/9 18:13
 *
 * @version 1.0
 */

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler{

    /**
     * 微信登录相关
     */
    private IWXAPI api;
    private String openId,unionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, "wxf9a97962ab078a3a",true);
        //将应用的appid注册到微信
        api.registerApp("wxf9a97962ab078a3a");

       // api.handleIntent(getIntent(), this);
        Log.d("!!!!!!!!!!!!!!","------------------------------------");
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
                Log.e("!!!!!!!!!!!!!!","参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

        Log.e("!!!!!!!!!!!!!!","baseReq:"+ JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //发送成功
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
//                        code = ((SendAuth.Resp) baseResp).code; //即为所需的code
                        doLogin(baseResp);

                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                       // Toast.makeText(WXEntryActivity.this,"分享成功!",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
                switch (baseResp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        Toast.makeText(WXEntryActivity.this, "登录取消了", Toast.LENGTH_SHORT).show();
                        break;
//                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
//                        Toast.makeText(WXEntryActivity.this, "分享取消了", Toast.LENGTH_SHORT).show();
//                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                break;
            default://发送返回

                break;
        }

    }

    private void doLogin(BaseResp baseResp) {
        WXBaseRespEntity entity = JSON.parseObject(JSON.toJSONString(baseResp), WXBaseRespEntity.class);
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                OkHttpUtils.get().url("https://api.weixin.qq.com/sns/oauth2/access_token")
                        .addParams("appid", "wxf9a97962ab078a3a")
                        .addParams("secret", "e465fea08908897efe11425667156db6")
                        .addParams("code", entity.getCode())
                        .addParams("grant_type", "authorization_code")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(okhttp3.Call call, Exception e, int id) {
                                Log.e("!!!!!!!!!!", "请求错误..");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("!!!!!!!!!!", "response:" + response);
                                WXAccessTokenEntity accessTokenEntity = JSON.parseObject(response, WXAccessTokenEntity.class);
                                if (accessTokenEntity != null) {
                                    if ("yes".equals(SharedPreferencesUtils.getString(WXEntryActivity.this, "isWxLogin"))) {
                                        getUserInfo(accessTokenEntity);
                                        Toast.makeText(getApplicationContext(), "登陆成功!", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Log.e("!!!!!!!!!!", "获取失败");
                                }
                            }
                        });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Log.e("!!!!!!!!!!", "发送取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Log.e("!!!!!!!!!!", "发送被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                result = "签名错误";
                Log.e("!!!!!!!!!!", "签名错误");
                break;
        }
    }

    /**
     * 获取个人信息
     * @param accessTokenEntity
     */
    private void getUserInfo(WXAccessTokenEntity accessTokenEntity) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        OkHttpUtils.get()
                .url("https://api.weixin.qq.com/sns/userinfo")
                .addParams("access_token",accessTokenEntity.getAccess_token())
                .addParams("openid",accessTokenEntity.getOpenid())//openid:授权用户唯一标识
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("!!!!!!!!!!!!","获取错误..");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("!!!!!!!!!!!!","userInfo:"+response);
                        final WXUserInfo wxResponse = JSON.parseObject(response,WXUserInfo.class);
                        Log.e("!!!!!!!!!!!!","微信登录资料已获取，后续未完成");
                        Intent intent = getIntent();

                        WXEntryActivity.this.setResult(0,intent);
                        openId = wxResponse.getOpenid();
                        unionId = wxResponse.getUnionid();
                        WangZKRequestApi.getInstance().wxLogin(openId, unionId, new Callback<WXRegisterResponse>() {
                            @Override
                            public void onResponse(Call<WXRegisterResponse> call, Response<WXRegisterResponse> response) {
                                final WXRegisterResponse resp = response.body();
                                Log.e("微信回调------>",resp.getCode()+"");
                                if (resp.getCode() == 1000) {//未微信注册
                                    Intent intent = new Intent(WXEntryActivity.this, BindPhoneActivity.class);
                                    intent.putExtra("uid", resp.getData().getUser().getId());
                                    startActivityForResult(intent, 1);
                                } else {//已微信注册
                                    final int userId = resp.getData().getUser().getId();
                                    final String phoneNumber = resp.getData().getUser().getMobile();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (TextUtils.isEmpty(resp.getData().getUser().getMobile())){
                                                Intent intent = new Intent(WXEntryActivity.this, BindPhoneActivity.class);
                                                intent.putExtra("uid", resp.getData().getUser().getId());
                                                startActivityForResult(intent, 1);
                                            }else {
                                                MobclickAgent.onEvent(WXEntryActivity.this, "c_login_suc");//友盟统计登录成功次数
                                                SharedPreferencesUtils.putString(WXEntryActivity.this, SharedPreferencesUtils.Key.KEY_USER_ID, "" + userId);
                                                SharedPreferencesUtils.putString(WXEntryActivity.this, SharedPreferencesUtils.Key.KEY_USER_PHONE, "" + phoneNumber);
                                                Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                                                WXEntryActivity.this.startActivity(intent);
                                            }
                                        }

                                    });
                                }
                                SharedPreferencesUtils.putString(WXEntryActivity.this,"wxImg",wxResponse.getHeadimgurl());
                                SharedPreferencesUtils.putString(WXEntryActivity.this,"wxName",wxResponse.getNickname());


                            }

                            @Override
                            public void onFailure(Call<WXRegisterResponse> call, Throwable t) {

                            }
                        });
                    }
                });
    }
}
