//package pro.qidian.com.qidianproject.activity;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import okhttp3.ResponseBody;
//import pro.qidian.com.qidianproject.Constans;
//import pro.qidian.com.qidianproject.R;
//import pro.qidian.com.qidianproject.ServiceApi.AppRequestApi;
//import pro.qidian.com.qidianproject.Utils;
//import pro.qidian.com.qidianproject.data.ResultCallback;
//import pro.qidian.com.qidianproject.model.WeatherData;
//import retrofit2.Call;
//import retrofit2.Response;
//
///**
// * Created by Jeremy on 2017/5/13.
// */
//
//public class WeatherActivity extends BaseActivity {
//    private TextView resultTxt;
//    private ProgressDialog dialog;
//    private Handler mHandler;
//    private String ip;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather);
//        initView();
//        mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == 1){
//                    queryWeather();
//                }
//            }
//        };
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ip = Utils.getNetIp();
//                Message message = Message.obtain();
//                message.what = 1;
//                mHandler.sendMessage(message);
//            }
//        }).start();
//
//    }
//
//    private void initView() {
//        resultTxt = (TextView) findViewById(R.id.weather_result_txt);
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("正在查询...");
//        dialog.show();
//    }
//
//    private void queryWeather() {
//
//        if (ip == null) {
//            ip = "58.215.185.154";
//        }
//        AppRequestApi.getInstance().queryWeather(ip, Constans.JUHE_KEY_WEATHER, new ResultCallback<W>() {
//            @Override
//            protected void onSuccess(final ResponseBody data) {
//                super.onSuccess(data);
//                if (data != null) {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                resultTxt.setText(data.string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                super.onResponse(call, response);
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                super.onFailure(call, t);
//                dialog.dismiss();
//            }
//        });
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dialog.dismiss();
//    }
//}
