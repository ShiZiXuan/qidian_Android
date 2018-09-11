package com.softwinner.un.tool.activity;

import android.content.Intent;
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


public class PhotoListActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ImageView back;
    private PictureAdapter mAdapter;
    private ArrayList<PictureFile> mData;

    private TextView edit, cancel, all;
    private Button delete;
    private boolean in_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_photo);
        back = (ImageView) findViewById(R.id.center_top_bar_left_img);
        back.setOnClickListener(this);
        edit = (TextView) findViewById(R.id.textView3);
        cancel = (TextView) findViewById(R.id.cancel);
        all = (TextView) findViewById(R.id.all);
        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        all.setOnClickListener(this);
        delete = (Button) findViewById(R.id.delete_btn);
        delete.setOnClickListener(this);
        mData = new ArrayList<>();
        try {
            String pictureDir = FileUtils.getFolderDir(FileUtils.PICTURE_DIR).getAbsolutePath();
            File pic_dir = new File(pictureDir);
            String[] photos = pic_dir.list();
            for (String photo : photos) {
                if (photo.endsWith(".jpg") || photo.endsWith(".JPG")) {
                    PictureFile file = new PictureFile();
                    file.path = pictureDir + "/" + photo;
                    file.isSelected = false;
                    mData.add(file);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new PictureAdapter();
        mRecyclerView.setAdapter(mAdapter);
        Collections.reverse(mData);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
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
        int i = v.getId();
        if (i == R.id.center_top_bar_left_img) {
            setResult(RESULT_OK);
            finish();
        } else if (i == R.id.textView3) {
            in_edit = true;
            showEdit(true);
            mAdapter.notifyDataSetChanged();
        } else if (i == R.id.cancel) {
            in_edit = false;
            showEdit(false);
            for (PictureFile file : mData) {
                file.isSelected = false;
            }
            mAdapter.notifyDataSetChanged();
        } else if (i == R.id.all) {
            for (PictureFile file : mData) {
                file.isSelected = !file.isSelected;
            }
            mAdapter.notifyDataSetChanged();
        } else if (i == R.id.delete_btn) {//删除照片
            boolean delete_success = false;
            int size = mData.size();
            for (int j = 0; j < size; j++) {
                PictureFile file = mData.get(j);
                if (file.isSelected) {
                    mData.remove(file);
                    new File(file.path).delete();
                    delete_success = true;
                    size--;
                    j--;
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
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(PhotoListActivity.this).inflate(R.layout.photoed_item, null);
            int width = PhotoListActivity.this.getResources().getDisplayMetrics().widthPixels / 2;
            view.setLayoutParams(new ViewGroup.LayoutParams(width, width * 3 / 4));
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            PictureFile file = mData.get(position);
            String path = file.path;
            Glide.with(PhotoListActivity.this)
                    .load(path)
                    .into(holder.image);
            String name = "20" + path.substring(path.lastIndexOf("/") + 1);
//            name = name.substring(0, 4) + "."
//                    + name.substring(4, 6) + "."
//                    + name.substring(6, 8) + "."
//                    + name.substring(8, 10) + ":"
//                    + name.substring(10, 12) + ":" + name.substring(12, 14);
//            holder.name.setText(name);
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

    private class PictureHolder extends RecyclerView.ViewHolder {
        ImageView image;
//        TextView name;
        CheckBox select;

        public PictureHolder(final View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
//            name = (TextView) itemView.findViewById(R.id.name);
            select = (CheckBox) itemView.findViewById(R.id.select);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) itemView.getTag();
                    PictureFile file = mData.get(position);
                    if (in_edit) {
                        file.isSelected = !file.isSelected;
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Intent intent = new Intent(PhotoListActivity.this, ImageActivity.class);
                        intent.putExtra("position", position);
                        ArrayList<String> list = new ArrayList<String>();
                        for (PictureFile pictureFile : mData) {
                            list.add(pictureFile.path);
                        }
                        intent.putExtra("data", list);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public class PictureFile {
        String path;
        boolean isSelected;
    }
}