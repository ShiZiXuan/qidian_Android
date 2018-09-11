package com.softwinner.un.tool.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.softwinner.un.tool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyVideoThumbLoader {
    private ImageView imgView;
    private String path;
    //创建cache
    private LruCache<String, Bitmap> lruCache;

    @SuppressLint("NewApi")
    public MyVideoThumbLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取最大的运行内存
        int maxSize = maxMemory / 4;//拿到缓存的内存大小 35
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //这个方法会在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    public void addVideoThumbToCache(String path, Bitmap bitmap) {
        if (getVideoThumbToCache(path) == null) {
            //当前地址没有缓存时，就添加
            lruCache.put(path, bitmap);
        }
    }

    public Bitmap getVideoThumbToCache(String path) {

        return lruCache.get(path);

    }

    List<AsyncTask> tasks = new ArrayList<>();

    public void showThumbByAsynctack(String path, ImageView imgview) {

        if (getVideoThumbToCache(path) == null) {
            //异步加载
            MyBobAsynctack task = new MyBobAsynctack(imgview, path);
            task.execute(path);
            tasks.add(task);
        } else {
            imgview.setImageBitmap(getVideoThumbToCache(path));
        }
    }
    public void cancelAll() {
        for (AsyncTask task : tasks) {
            task.cancel(true);
        }
    }

    class MyBobAsynctack extends AsyncTask<String, Void, Bitmap> {
        private ImageView imgView;
        private String path;

        public MyBobAsynctack(ImageView imageView, String path) {
            this.imgView = imageView;
            this.path = path;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            if (isCancelled()) {
                return null;
            }
            MediaMetadataRetriever mediaRetriever = null;
            try {
                ThumbnailUtils tu = new ThumbnailUtils();
                mediaRetriever = new MediaMetadataRetriever();
                mediaRetriever.setDataSource(path, new HashMap<String, String>());
                bitmap = mediaRetriever.getFrameAtTime();//获取第一帧的缩略图
                if (isCancelled()) {
                    return null;
                }

                // //直接对Bitmap进行缩略操作，最后一个参数定义为OPTIONS_RECYCLE_INPUT ，来回收资源
                bitmap = tu.extractThumbnail(bitmap, 350, 350,
                        ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                //加入缓存中
                if (getVideoThumbToCache(params[0]) == null) {
                    addVideoThumbToCache(path, bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mediaRetriever != null)
                    mediaRetriever.release();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                return;
            }
            if (imgView.getTag().equals(path)) {//通过 Tag可以绑定 图片地址和 imageView，这是解决Listview加载图片错位的解决办法之一
                imgView.setImageBitmap(bitmap);
            } else {
                imgView.setImageResource(R.drawable.background);
            }
        }
    }
}
