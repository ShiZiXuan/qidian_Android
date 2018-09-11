package com.uautogo.qidian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.FuelcardBean;

import java.util.List;

/**
 * Created by uuun on 2018/8/8.
 */

public class FuelcardGvAdapter extends BaseAdapter {
    private List<FuelcardBean> list;
    private Context mContext;

    public FuelcardGvAdapter(List<FuelcardBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.fuelcard_gv_item,viewGroup,false);
            holder.tv = view.findViewById(R.id.fuelcard_gv_bt);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        FuelcardBean bean = list.get(i);
        holder.tv.setText(bean.getMoney());

        return view;
    }
    class ViewHolder{
        Button tv;
    }
}
