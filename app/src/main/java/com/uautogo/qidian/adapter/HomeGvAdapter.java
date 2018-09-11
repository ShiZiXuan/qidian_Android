package com.uautogo.qidian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.HomeGvBean;

import java.util.List;

/**
 * Created by uuun on 2018/8/10.
 */

public class HomeGvAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeGvBean> list;

    public HomeGvAdapter(Context mContext, List<HomeGvBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.home_gv_item,viewGroup,false);
            holder.tv = view.findViewById(R.id.home_gv_name);
            holder.iv =view.findViewById(R.id.home_gv_iv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        HomeGvBean bean = list.get(i);
        holder.tv.setText(bean.getName());
        holder.iv.setImageResource(bean.getIv());
        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
