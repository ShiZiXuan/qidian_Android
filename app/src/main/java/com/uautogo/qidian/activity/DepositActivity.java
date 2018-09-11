package com.uautogo.qidian.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.QidianRequestApi;
import com.uautogo.qidian.model.OrderRespons;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 退押金界面
 */
public class DepositActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView address, addressed, phoned, wait, status;
    private LinearLayout phone;
    private ImageView center_top_bar_left_img;
    private Button ok;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        address = (TextView) findViewById(R.id.address_tv);
        addressed = (TextView) findViewById(R.id.addressed_tv);
        phone = (LinearLayout) findViewById(R.id.photo_tv);
        phoned = (TextView) findViewById(R.id.photoed_tv);
        status = (TextView) findViewById(R.id.status_tv);
        wait = (TextView) findViewById(R.id.wait_tv);
        ok = (Button) findViewById(R.id.ok);
        center_top_bar_left_img = (ImageView) findViewById(R.id.center_top_bar_left_img);
        center_top_bar_left_img.setOnClickListener(this);
        final OrderRespons.Order order = (OrderRespons.Order) getIntent().getSerializableExtra("order");
        if (order.getRefundStatus() == 0) {
            ok.setVisibility(View.VISIBLE);
            status.setVisibility(View.GONE);
        } else {
            ok.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
        }
        mHandler = new Handler(Looper.getMainLooper());//在其他线程刷新或是处理消息
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(DepositActivity.this)
                        .setTitle("提示")
                        .setMessage("确认退款?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String id = SharedPreferencesUtils.getString(DepositActivity.this, SharedPreferencesUtils.Key.KEY_USER_ID);
                                int userid = Integer.parseInt(id);
                                QidianRequestApi.getInstance().refund(userid, order.getId(), new Callback<ResponseBody>() {

                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DepositActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                                                ok.setVisibility(View.GONE);
                                                status.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(DepositActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });

                                dialog.dismiss();
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                                .setTextColor(Color.BLACK);
                        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                                .setTextColor(Color.BLACK);
                    }
                });
                alertDialog.show();
            }

        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.center_top_bar_left_img) {
            setResult(RESULT_OK);
            finish();
        }
    }
}