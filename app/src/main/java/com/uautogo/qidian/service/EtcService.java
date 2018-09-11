package com.uautogo.qidian.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.PayNotificationActivity;
import com.uautogo.qidian.model.NewETCResponse;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by linjing on 2017/11/13.
 */

public class EtcService extends Service {
    public static boolean isPaying = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           // if (!isPaying)
             //   checkEtc();
          //  handler.sendEmptyMessageDelayed(1, 60 * 1000);//每隔60秒弹出dialog
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        handler.sendEmptyMessageDelayed(1, 60 * 1000);
       // handler.sendEmptyMessage(1);
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkEtc() {
        int userId = Integer.parseInt(SharedPreferencesUtils.getString(getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID));
//        userId = 499;
        WangZKRequestApi.getInstance().checkETC(userId, new Callback<NewETCResponse>() {
            @Override
            public void onResponse(Call<NewETCResponse> call, Response<NewETCResponse> response) {
                NewETCResponse etcResponse = response.body();
                if (etcResponse != null) {
                    List<NewETCResponse.DataBean> data = etcResponse.getData();
                    if (data != null && data.size() > 0) {
                        NewETCResponse.DataBean etc = data.get(0);
                        if (etc != null && etc.getStatus() == 0) {
                            Intent intent = new Intent(getApplicationContext(), PayNotificationActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("data", etc);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NewETCResponse> call, Throwable t) {

            }
        });

    }
}
