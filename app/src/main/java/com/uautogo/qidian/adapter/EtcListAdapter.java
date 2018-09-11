package com.uautogo.qidian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.NewETCResponse1;

import java.util.List;

/**
 * Created by linjing on 2017/11/7.
 */

public class EtcListAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewETCResponse1.DataBean.ListBean> mList;

    public EtcListAdapter(Context context, List<NewETCResponse1.DataBean.ListBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.two_etc_item, parent, false);
            holder.time_tv = (TextView) convertView.findViewById(R.id.etc_item_time);
            holder.money_tv = (TextView) convertView.findViewById(R.id.etc_item_money);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        NewETCResponse1.DataBean.ListBean bean = mList.get(position);
        holder.time_tv.setText(bean.getTrsTime()+"");
        holder.money_tv.setText(bean.getAmount()+"元");
        return convertView;
    }

    static class ViewHolder {
        TextView time_tv, money_tv;
    }
}
