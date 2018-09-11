package com.softwinner.un.tool.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.softwinner.un.tool.R;
import com.softwinner.un.tool.activity.StartActivity;
import com.softwinner.un.tool.util.FileUtils;
import com.softwinner.un.tool.util.ToastUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by lucky-django on 16/11/2.
 */

public class DownloadService extends Service {

    private static final int NOTIFICATION_ID = 66;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;
    private Handler mHandler;
    private File mDownloadFile;
    private boolean mWorking = false;
    private boolean isApk = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getData() != null) {
            if (!mWorking) {
                mWorking = true;
            } else {
                ToastUtils.showMessage(getString(R.string.download_working));
                return START_STICKY_COMPATIBILITY;
            }
            final String requestUrl = intent.getData().toString();
            if (requestUrl.endsWith("apk")) {
                isApk = true;
            }
            mHandler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    download(requestUrl);
                }
            }).start();
        } else {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void download(String requestUrl) {
        String downloadDir = "";
        try {
            downloadDir = FileUtils.getFolderDir(FileUtils.DOWNLOAD_DIR).getAbsolutePath();
        } catch (IOException e) {

        }
        final String fileName;
        if (requestUrl.endsWith(".fex"))
            fileName = "full_img.fex";
        else
            fileName = FileUtils.getFileNameFromUrl(requestUrl);
        final String downloadText = getString(R.string.download_file, fileName);
        mDownloadFile = new File(downloadDir + "/" + fileName);
        if (mDownloadFile.exists()) {
            mDownloadFile.delete();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showMessage(getString(R.string.download_start, fileName));
            }
        });
        initNotification(downloadText);
        OkHttpManager.getInstance()
                .downloadFile(requestUrl, mDownloadFile, new OkHttpManager.OnFileDownloadListener() {
                            @Override
                            public void onProgress(final int progress) {

                            }

                            @Override
                            public void OnSuccess() {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //ToastUtils.showMessage(getString(R.string.download_complete));
                                        ToastUtils.showMessage("下载至" + mDownloadFile.getPath());
                                    }
                                });
                                mManager.notify(NOTIFICATION_ID,
                                        mBuilder.setContentText(getString(R.string.download_complete))
                                                .setProgress(0, 0, false)
                                                .setSmallIcon(R.drawable.video)
                                                .build());
                                if (mDownloadFile.exists() && mDownloadFile.isFile()) {
                                    if (mDownloadFile.getName().endsWith(".fex") || mDownloadFile.getName().endsWith(".FEX")) {
                                        sendBroadcast(new Intent("gujian_download_finished"));
                                    }
                                    if (isApk) {
                                        sendBroadcast(new Intent("app_download_finished"));
                                    }

//                  Intent intent = new Intent(Intent.ACTION_VIEW);
//                  intent.setDataAndType(Uri.fromFile(mDownloadFile),
//                      "application/vnd.android.package-archive");
//                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  startActivity(intent);

//                  VideoFile file = videoFiles.get(position);
//                  Intent it = new Intent(Intent.ACTION_VIEW);
//                  Uri uri = Uri.parse("http://192.168.100.1"+file.path);
//                  it.setDataAndType(uri, "video/mp4");
//                  startActivity(it);

                                    if (isApk) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(mDownloadFile),
                                                "application/vnd.android.package-archive");
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else if (mDownloadFile.getName().endsWith(".fex") || mDownloadFile.getName().endsWith(".FEX")) {
                                    } else {
                                        Intent intent = new Intent();
                                        intent.setAction(android.content.Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(mDownloadFile), "video/*");
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }

                                }
                                stopForeground(true);
                                stopSelf();
                            }

                            @Override
                            public void onFail() {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showMessage(getString(R.string.download_failed));
                                    }
                                });
                                mManager.notify(NOTIFICATION_ID,
                                        mBuilder.setContentText(getString(R.string.download_failed))
                                                .setProgress(0, 0, false)
                                                .setSmallIcon(R.drawable.video)
                                                .build());
//                                sendBroadcast(new Intent("gujian_download_finished"));
                                //发送广播取消正在下载中的dialog
                                sendBroadcast(new Intent("download_failed"));
                                stopForeground(true);
                                stopSelf();
                            }
                        }

                );
    }

    public static final String EXTRA_REFRESH = "refresh";

    private void initNotification(String downloadText) {
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(downloadText)
                .setContentText(getString(R.string.new_file))
                .setProgress(0, 0, true)
                .setSmallIcon(R.drawable.video);
        Intent i = new Intent(this, StartActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        i.putExtra(EXTRA_REFRESH, true);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        mBuilder.setContentIntent(pi);
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }
}
