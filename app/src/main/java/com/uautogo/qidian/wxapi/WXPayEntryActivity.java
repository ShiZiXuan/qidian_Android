//package com.uautogo.qidian.wxapi;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//    @Override
//    public void onReq(BaseReq req) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.e("=============", "onPayFinish, errCode = " + resp.errCode);
//    }
//}

package com.uautogo.qidian.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.PaySuccessActivity;
import com.uautogo.qidian.utils.SharedPreferencesUtils;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    private String orderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        String appId = SharedPreferencesUtils.getString(this, "appId");
        api = WXAPIFactory.createWXAPI(this, appId);
        api.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //Log.e("weChatPayPay", baseResp.errCode + "");
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                Toast.makeText(this, "恭喜您支付成功", Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(WXPayEntryActivity.this,PaySuccessActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
            }
                finish();
            }
        }

}
