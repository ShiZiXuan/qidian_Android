package com.softwinner.un.tool.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwinner.un.tool.R;
import com.softwinner.un.tool.model.VideoFile;
import com.softwinner.un.tool.util.DividerGridItemDecoration;
import com.softwinner.un.tool.util.FileUtils;
import com.softwinner.un.tool.util.MyVideoThumbLoader;
import com.softwinner.un.tool.util.ToastUtils;
import com.softwinner.un.tool.util.UNTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class VideoListActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private static ArrayList<VideoFile> videos;
    private static VideoFileAdapter fileAdapter;
    private MyVideoThumbLoader mVideoThumbLoader;
    ProgressDialog dialog;
    private ImageView back;

    private boolean downloading = false;
    private List<String> downloaded_videos;

    private static SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mVideoThumbLoader = new MyVideoThumbLoader();
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                videos.clear();
                fileAdapter.notifyDataSetChanged();
                UNTool.getInstance().checkTFCard();
                mSwipe.setRefreshing(true);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        back = (ImageView) findViewById(R.id.center_top_bar_left_img);
        back.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
//        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        dialog.setIcon(R.drawable.ic_launcher);// 设置提示的title的图标，默认是没有的
        dialog.setTitle("下载中");
        dialog.setMax(100);
        dialog.setButton(ProgressDialog.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloading = false;
                dialog.dismiss();

            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                VideoListActivity.this.dialog.getButton(ProgressDialog.BUTTON_POSITIVE)
                        .setTextColor(Color.BLACK);
            }
        });
        videos = (ArrayList<VideoFile>) getIntent().getSerializableExtra("videos");

        fileAdapter = new VideoFileAdapter(this, videos);
        mRecyclerView.setAdapter(fileAdapter);
        Collections.reverse(videos); //设置时间
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        downloaded_videos = new ArrayList<>();
    }

    public static void refresh(ArrayList<VideoFile> list) {
        if (videos != null) {
            videos.clear();
            videos.addAll(list);
            Collections.reverse(videos); //设置时间
            fileAdapter.notifyDataSetChanged();
            mSwipe.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.center_top_bar_left_img) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private class VideoFileAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context mContext;
        private List<VideoFile> videoFiles = new ArrayList<>();

        public VideoFileAdapter(Context context, List<VideoFile> videos) {
            this.mContext = context;
            videoFiles = videos;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_list_new, null);
            int width = mContext.getResources().getDisplayMetrics().widthPixels / 2;
            view.setLayoutParams(new ViewGroup.LayoutParams(width, width));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int i) {
            final VideoFile file = videoFiles.get(i);
            boolean exist = false;
            for (String downloaded_video : downloaded_videos) {
                if (downloaded_video.equals(file.name)) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                holder.downloaded.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.GONE);
            } else {
                holder.downloaded.setVisibility(View.GONE);
                holder.download.setVisibility(View.VISIBLE);
            }

            String name = file.name.replace("_", "");
            name = name.substring(0, 4) + "." + name.substring(4, 6) + "."
                    + name.substring(6, 8) + "."
                    + name.substring(8, 10) + ":"
                    + name.substring(10, 12) + ":" + name.substring(12, 14);

            holder.name.setText(name);

            holder.icon.measure(0, 0);
            final int width = holder.icon.getMeasuredWidth();
            final int height = holder.icon.getMeasuredHeight();
            Log.e("width=======" + width, ",=======height" + height);
            String path = "http://192.168.100.1" + file.path;
            holder.icon.setTag(path);//绑定imageview
//            mVideoThumbLoader.showThumbByAsynctack(path, holder.icon);//显示视频图片
            holder.itemView.setTag(i);
        }

        @Override
        public int getItemCount() {
            return videoFiles == null ? 0 : videoFiles.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private ImageView download;
        private TextView name, downloaded;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.video_name);
            downloaded = (TextView) itemView.findViewById(R.id.downloaded_tv);
            download = (ImageView) itemView.findViewById(R.id.video_download);
            icon = (ImageView) itemView.findViewById(R.id.video_list_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showMessage("请下载视频进行观看");
//                    VideoFile file = videos.get((Integer) itemView.getTag());
//                    Intent intent = new Intent(VideoListActivity.this, PlayVideoActivity.class);
//                    Uri uri = Uri.parse("http://192.168.100.1" + file.path);
//                    intent.setData(uri);
//                    startActivity(intent);
                }

            });

            download.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final VideoFile file = videos.get((Integer) itemView.getTag());
                    final String path = "http://192.168.100.1" + file.path;
                    final String fileName = FileUtils.getFileNameFromUrl(path);
                    String downloadDir = null;
                    try {
                        downloadDir = FileUtils.getFolderDir(FileUtils.DOWNLOAD_DIR).getAbsolutePath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final File mDownloadFile = new File(downloadDir + "/" + fileName);
                    if (mDownloadFile.exists()) {
                        ToastUtils.showMessage("视频已存在");
                        return;
                    }

                    downloading = true;
//                    Intent intent = new Intent(VideoListActivity.this, DownloadService.class);
//                    intent.setData(Uri.parse("http://192.168.100.1" + file.path));
//                    startService(intent);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            FileOutputStream fos = null;
                            InputStream is = null;
                            try {
                                URL url = new URL(path);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.connect();
                                int total = connection.getContentLength();
                                int current = 0;
                                Log.e("total=======", "==" + total);
                                byte[] buffer = new byte[1024 * 8];
                                int len = 0;
                                int progress = 0;

                                fos = new FileOutputStream(mDownloadFile);
                                if (connection.getResponseCode() == 200) {
                                    is = connection.getInputStream();
                                    while (downloading && (len = is.read(buffer)) != -1) {
                                        fos.write(buffer, 0, len);
                                        fos.flush();
                                        current += len;
                                        float f = current * 1.0f / total;
                                        int percent = (int) (f * 100);
                                        if (progress != percent) {
                                            progress = percent;
                                            Message msg = Message.obtain();
                                            msg.arg1 = percent;
                                            msg.what = 1;
                                            handler.sendMessage(msg);
                                        }
                                    }
                                    is.close();
                                    fos.close();
                                    if (downloading == false) {
                                        mDownloadFile.delete();
                                    } else {
                                        handler.sendEmptyMessage(2);
                                    }
                                    Uri localUri = Uri.parse("file://" + mDownloadFile.getAbsolutePath());
                                    Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                                    localIntent.setData(localUri);
                                    sendBroadcast(localIntent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                try {
                                    if (is != null)
                                        is.close();
                                    if (fos != null)
                                        fos.close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                mDownloadFile.delete();
                            }
                        }
                    }).start();
                    dialog.show();
                }
            });
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int percent = msg.arg1;
                    dialog.setProgress(percent);
                    break;
                case 2:
                    dialog.dismiss();
                    dialog.setProgress(0);
                    fileAdapter.notifyDataSetChanged();
                    final AlertDialog alertDialog = new AlertDialog.Builder(VideoListActivity.this)
                            .setTitle("提示")
                            .setMessage("下载成功！")
                            .setPositiveButton("去观看", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(VideoListActivity.this, VideoedActivity.class));
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
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mVideoThumbLoader.cancelAll();
        super.onDestroy();
    }
}