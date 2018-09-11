package tw.com.a_i_t.IPCamViewer.FileBrowser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tw.com.a_i_t.IPCamViewer.CameraCommand;
import tw.com.a_i_t.IPCamViewer.FileBrowser.Model.FileNode;
import tw.com.a_i_t.IPCamViewer.R;

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2018-02-08 16:06:18
 */

public class PictureAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<FileNode> mFileList;

    public PictureAdapter(LayoutInflater inflater, ArrayList<FileNode> fileList) {
        mInflater = inflater;
        mFileList = fileList;
    }

    @Override
    public int getCount() {

        return mFileList == null ? 0 : mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList == null ? null : mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PictureHolder holder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.photoed_item, viewGroup,false);
            holder = new PictureHolder(view);
            view.setTag(holder);
        } else {
            holder = (PictureHolder) view.getTag();
        }
        String url = "http://" + CameraCommand.getCameraIp()/*ip*/ + mFileList.get(i).mName;
        url = url.replace("\\", "/");
        Log.e("imageUrl==============", "====" + url);
        Glide.with(mInflater.getContext()).load(url).into(holder.image);
        return view;
    }

    private class PictureHolder {
        ImageView image;
        //        TextView name;
        CheckBox select;

        public PictureHolder(final View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
//            name = (TextView) itemView.findViewById(R.id.name);
            select = (CheckBox) itemView.findViewById(R.id.select);
            select.setVisibility(View.INVISIBLE);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = (int) itemView.getTag();
//                    PictureFile file = mData.get(position);
//                    if (in_edit) {
//                        file.isSelected = !file.isSelected;
//                        mAdapter.notifyDataSetChanged();
//                    } else {
//                        Intent intent = new Intent(PhotoListActivity.this, ImageActivity.class);
//                        intent.putExtra("position", position);
//                        ArrayList<String> list = new ArrayList<String>();
//                        for (PictureFile pictureFile : mData) {
//                            list.add(pictureFile.path);
//                        }
//                        intent.putExtra("data", list);
//                        startActivity(intent);
//                    }
//                }
//            });
        }
    }
}
