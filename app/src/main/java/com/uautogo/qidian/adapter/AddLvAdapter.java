package com.uautogo.qidian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.AdressActivity;
import com.uautogo.qidian.model.AddressBean;

import java.util.List;

/**
 * Created by uuun on 2018/8/7.
 */

public class AddLvAdapter extends BaseAdapter {
    private List<AddressBean>list;
    private Context mContext;

    public AddLvAdapter(List<AddressBean> list, Context mContext) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.add_lv_item,viewGroup,false);
            holder.name = view.findViewById(R.id.add_lv_name);
            holder.phone = view.findViewById(R.id.add_lv_phone);
            holder.add = view.findViewById(R.id.add_lv_add);
            holder.but = view.findViewById(R.id.add_lv_remakeAdd);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final AddressBean bean = list.get(i);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getPhone());
        holder.add.setText(bean.getAddress()+bean.getAddressDetail());
        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("address--------->",bean.getName());
                Intent intent = new Intent(mContext,AdressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address",bean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
    class ViewHolder{
        TextView name,phone,add;
        Button but;
    }
}
