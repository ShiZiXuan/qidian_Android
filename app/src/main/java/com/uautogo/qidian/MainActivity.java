package com.uautogo.qidian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.softwinner.un.tool.service.DownloadService;
import com.uautogo.qidian.ServiceApi.QidianRequestApi;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.BaseActivity;
import com.uautogo.qidian.activity.PayNotificationActivity;
import com.uautogo.qidian.data.ResultCallback;
import com.uautogo.qidian.fragment.CkeFragment;
import com.uautogo.qidian.fragment.HomeFragment;
import com.uautogo.qidian.fragment.Myfragment;
import com.uautogo.qidian.fragment.ShareFragemnt;
import com.uautogo.qidian.model.AppUpdateRespons;
import com.uautogo.qidian.model.NewETCResponse;
import com.uautogo.qidian.model.UpdateRespons;
import com.uautogo.qidian.service.EtcService;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.uautogo.qidian.utils.UpdateUtil;
import com.uautogo.qidian.view.UpdateDialog;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends BaseActivity {
    private UpdateReceiver mReceiver;
    private ProgressDialog mGujianDialog;
    private ProgressDialog mAppDialog;
    private FragmentManager manager;
    private android.app.FragmentTransaction transaction;
    private Fragment[] fgs = {new HomeFragment(),new ShareFragemnt(),new CkeFragment(), new Myfragment()};
    private RadioButton buttons[];
    private RadioGroup rg;
    public static boolean isPaying = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, EtcService.class));

        initDialog();
        if (!isPaying){
            checkEtc();
        }
        Log.e("!!!!!!!!!用户ID-->",SharedPreferencesUtils.getString(this, SharedPreferencesUtils.Key.KEY_USER_ID));
        rg = findViewById(R.id.home_rg);
        manager = getFragmentManager();

        IntentFilter filter = new IntentFilter();
        filter.addAction("gujian_download_finished");
        filter.addAction("app_download_finished");
        filter.addAction("download_failed");
        mReceiver = new UpdateReceiver();
        registerReceiver(mReceiver, filter);
        transaction = manager.beginTransaction();
        transaction.add(R.id.home_layout, fgs[0]).commit();
        for (int i = 1; i < 4; i++) {
            manager.beginTransaction().add(R.id.home_layout, fgs[i]).hide(fgs[i]).commit();
        }
        initRadioButton();
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
    protected void initRadioButton() {
        int[] imgs = {R.drawable.home_home_active, R.drawable.home_share_unactive, R.drawable.home_cke_unactive, R.drawable.home_my_unactive};
        buttons = new RadioButton[imgs.length];
        for (int i = 0; i < buttons.length; ++i) {
            buttons[i] = (RadioButton) rg.getChildAt(i);
            buttons[i].setChecked(false);
            buttons[i].setTextColor(Color.parseColor("#ffffff"));
        }
        buttons[0].setTextColor(Color.parseColor("#f4ea2a"));
        buttons[0].setChecked(true);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                android.app.FragmentTransaction transaction = manager.beginTransaction();
                for (int i = 0; i < 4; i++) {
                    transaction.hide(fgs[i]);
                }
                switch (checkId) {
                    case R.id.home_rb1:
                        //首页
                        transaction.show(fgs[0]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[2]);
                        transaction.hide(fgs[3]);
                        buttons[0].setTextColor(Color.parseColor("#f4ea2a"));
                        buttons[1].setTextColor(Color.parseColor("#ffffff"));
                        buttons[2].setTextColor(Color.parseColor("#ffffff"));
                        buttons[3].setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case R.id.home_rb2:
                        //分享
                        transaction.show(fgs[1]);
                        transaction.hide(fgs[0]);
                        transaction.hide(fgs[2]);
                        transaction.hide(fgs[3]);
                        buttons[1].setTextColor(Color.parseColor("#f4ea2a"));
                        buttons[0].setTextColor(Color.parseColor("#ffffff"));
                        buttons[3].setTextColor(Color.parseColor("#ffffff"));
                        buttons[2].setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case R.id.home_rb3:
                        //车克
                        transaction.show(fgs[2]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[0]);
                        transaction.hide(fgs[3]);
                        buttons[2].setTextColor(Color.parseColor("#f4ea2a"));
                        buttons[1].setTextColor(Color.parseColor("#ffffff"));
                        buttons[0].setTextColor(Color.parseColor("#ffffff"));
                        buttons[3].setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case R.id.home_rb4:
                        //我的
                        transaction.show(fgs[3]);
                        transaction.hide(fgs[1]);
                        transaction.hide(fgs[0]);
                        transaction.hide(fgs[2]);
                        buttons[3].setTextColor(Color.parseColor("#f4ea2a"));
                        buttons[1].setTextColor(Color.parseColor("#ffffff"));
                        buttons[2].setTextColor(Color.parseColor("#ffffff"));
                        buttons[0].setTextColor(Color.parseColor("#ffffff"));
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void initDialog() {
        mAppDialog = new ProgressDialog(this);
        mGujianDialog = new ProgressDialog(this);
        mAppDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mGujianDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mAppDialog.setMessage("应用正在下载中...");
        mGujianDialog.setMessage("固件正在下载中...");
        mAppDialog.setCancelable(false);
        mGujianDialog.setCancelable(false);
        mAppDialog.setCanceledOnTouchOutside(false);
        mGujianDialog.setCanceledOnTouchOutside(false);
    }


    private UpdateRespons gujian;
    private AppUpdateRespons app;

    private void downloadGuJian(boolean isDialogShow) {
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.setData(Uri.parse(gujian.data.updateUrl));
        startService(intent);
        if (isDialogShow)
            mGujianDialog.show();
        else
            mAppDialog.show();
    }


    private void downloadApp(boolean isDialogShow) {
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        intent.setData(Uri.parse(app.data.getUpdateUrl()));
        startService(intent);
        if (isDialogShow)
            mAppDialog.show();
    }


    private void checkAppUpdate() {
        int versionCode = 0;
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        QidianRequestApi.getInstance().checkAppUpdate(versionCode, new ResultCallback<AppUpdateRespons>() {
            @Override
            public void onResponse(Call<AppUpdateRespons> call, Response<AppUpdateRespons> response) {
                app = response.body();
                Log.e("gujian.data.versionNo==", "==" + gujian.data.versionNo);
                if (UpdateUtil.isNeedUpdate(gujian.data.versionNo, MainActivity.this)) {//有新固件

                    String dialog_title = "";
                    String dialog_content = "";
                    if (app.data.getUpdateFlag() == 1) {//有新的app
//                        dialog_content = "固件新版本" + gujian.data.versionNo + "，应用新版本" + app.data.getVersionCode();
                        dialog_title = "应用新版本" + app.data.getVersionName();
                        dialog_content = app.data.getUpgradeInfo();
                    } else {//没有新的app
                        dialog_title = "固件新版本" + gujian.data.versionNo;
                    }
                    final UpdateDialog dialog = new UpdateDialog(MainActivity.this);
                    dialog.setTitle(dialog_title)
                            .setContent(TextUtils.isEmpty(dialog_content) ? "" : dialog_content)
                            .setYesClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    downloadGuJian(app.data.getUpdateFlag() != 1);
                                    dialog.dismiss();
                                }
                            })
                            .setNoClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //取消
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {//没有新固件
                    if (app.data.getUpdateFlag() == 1) {//有新的app
                        String dialog_content = app.data.getUpgradeInfo();
                        final UpdateDialog dialog = new UpdateDialog(MainActivity.this);
                        dialog.setTitle("应用新版本" + app.data.getVersionName())
                                .setContent(TextUtils.isEmpty(dialog_content) ? "" : dialog_content)
                                .setYesClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        downloadApp(true);
                                        dialog.dismiss();
                                    }
                                })
                                .setNoClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //取消
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppUpdateRespons> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if ("gujian_download_finished".equals(action)) {
                SharedPreferencesUtils.putString(MainActivity.this, SharedPreferencesUtils.Key.KEY_DEVICE_VERSION, gujian.data.versionNo);
                if (mGujianDialog != null && mGujianDialog.isShowing())
                    mGujianDialog.dismiss();

                if (app.data.getUpdateFlag() == 1)
                    downloadApp(false);

            } else if ("app_download_finished".equals(action)) {
                mAppDialog.dismiss();
            } else if ("download_failed".equals(action)) {
                if (mGujianDialog.isShowing())
                    mGujianDialog.dismiss();
                if (mAppDialog.isShowing())
                    mAppDialog.dismiss();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }
}
