package com.uautogo.qidian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.EtcResponse;

/**
 * Created by linjing on 2017/11/7.
 */

public class EtcChooseAdapter extends BaseAdapter {
    private Context mContext;
    private List<EtcResponse.Data.CarDvrs> mList;

    public EtcChooseAdapter(Context context, List<EtcResponse.Data.CarDvrs> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.etc_item, parent, false);
            holder.number_tv = (TextView) convertView.findViewById(R.id.number_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EtcResponse.Data.CarDvrs carDvrs = mList.get(position);
        holder.number_tv.setText(carDvrs.getDvrId());
        return convertView;
    }

    public static class ViewHolder {

        private TextView number_tv;
    }
}
