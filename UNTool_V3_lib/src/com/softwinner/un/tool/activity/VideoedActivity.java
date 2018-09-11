package com.softwinner.un.tool.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softwinner.un.tool.R;
import com.softwinner.un.tool.util.DividerGridItemDecoration;
import com.softwinner.un.tool.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.softwinner.un.tool.R.id.center_top_bar_left_img;

/**
 * 已下载的视频
 */
public class VideoedActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<VideoedFile> mData;
    private VideoAdapter mAdapter;
    private TextView edit, all, cancel;
    private boolean in_edit;
    private ImageView back;
    private Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoed);
        mData = new ArrayList<>();
        back = (ImageView) findViewById(center_top_bar_left_img);
        back.setOnClickListener(this);
        all = (TextView) findViewById(R.id.all);
        cancel = (TextView) findViewById(R.id.cancel);
        delete = (Button) findViewById(R.id.delete_btn);
        delete.setOnClickListener(this);
        all.setOnClickListener(this);
        cancel.setOnClickListener(this);
        try {
            String videoDir = FileUtils.getFolderDir(FileUtils.DOWNLOAD_DIR).getAbsolutePath();
            File video_dir = new File(videoDir);
            String[] videos = video_dir.list();
            for (String video : videos) {
                if (video.endsWith(".mov") || video.endsWith(".MOV")) {
                    VideoedFile videoedFile = new VideoedFile();
                    videoedFile.path = videoDir + "/" + video;
                    mData.add(videoedFile);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new VideoAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_videoed);
        Collections.reverse(mData);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration());

        edit = (TextView) findViewById(R.id.textView3);
        edit.setOnClickListener(this);
    }

    private void showEdit(boolean show) {
        if (show) {
            back.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.VISIBLE);
            all.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            all.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.textView3) {
            in_edit = true;
            showEdit(true);
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.cancel) {
            in_edit = false;
            showEdit(false);
            for (VideoedFile file : mData) {
                file.isSelected = false;
            }
            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.all) {
            for (VideoedFile file : mData) {
                file.isSelected = !file.isSelected;
            }
            mAdapter.notifyDataSetChanged();
        } else if (id == center_top_bar_left_img) {
            finish();
        } else if (id == R.id.delete_btn) {//删除视频
            boolean delete_success = false;
            int size = mData.size();
            for (int i = 0; i < size; i++) {
                VideoedFile file = mData.get(i);
                if (file.isSelected) {
                    mData.remove(file);
                    new File(file.path).delete();
                    delete_success = true;
                    size--;
                    i--;
                }
            }

            mAdapter.notifyDataSetChanged();
            String content = "";
            if (delete_success)
                content = "删除成功";
            else
                content = "请选择要删除的文件";
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
            if (mData.size() == 0)
                showEdit(false);
        } else if (id == center_top_bar_left_img) {
            setResult(RESULT_OK);
            finish();//返回按钮
        }

    }

    private class VideoedFile {
        String path;
        boolean isSelected;
    }

    private class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

        @Override
        public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(VideoedActivity.this).inflate(R.layout.item_photo_list, null);
            int width = VideoedActivity.this.getResources().getDisplayMetrics().widthPixels / 2;
            view.setLayoutParams(new ViewGroup.LayoutParams(width, width * 3 / 4));
            return new VideoHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoHolder holder, int position) {
            VideoedFile file = mData.get(position);
            String path = file.path;
            Glide.with(VideoedActivity.this)
                    .load(path)
                    .into(holder.image);
            String name = path.substring(path.lastIndexOf("/") + 1).replace("_", "");
            name = name.substring(0, 4) + "."
                    + name.substring(4, 6) + "."
                    + name.substring(6, 8) + " "
                    + name.substring(8, 10) + ":"
                    + name.substring(10, 12) + ":" + name.substring(12, 14);
            holder.name.setText(name);
            holder.itemView.setTag(position);
            if (in_edit)
                holder.select.setVisibility(View.VISIBLE);
            else
                holder.select.setVisibility(View.GONE);
            holder.select.setChecked(file.isSelected);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class VideoHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        CheckBox select;

        public VideoHolder(final View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            select = (CheckBox) itemView.findViewById(R.id.select);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) itemView.getTag();
                    VideoedFile file = mData.get(position);
                    if (in_edit) {
                        file.isSelected = !file.isSelected;
                        mAdapter.notifyDataSetChanged();
                    } else {
                        String path = file.path;
                        Intent intent = new Intent(VideoedActivity.this, PlayVideoActivity.class);
                        intent.setData(Uri.parse(path));
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
