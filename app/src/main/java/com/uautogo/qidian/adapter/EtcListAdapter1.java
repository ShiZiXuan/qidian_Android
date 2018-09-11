package com.uautogo.qidian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.NewETCResponse2;

import java.util.List;

/**
 * Created by uuun on 2018/8/15.
 */

public class EtcListAdapter1 extends BaseAdapter {
    private Context mContext;
    private List<NewETCResponse2.DataBean.ListBean> mList;

    public EtcListAdapter1(Context context, List<NewETCResponse2.DataBean.ListBean> list) {
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
        EtcListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new EtcListAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.two_etc_item1, parent, false);
            holder.time_tv = (TextView) convertView.findViewById(R.id.etc_item_time);
            holder.money_tv = (TextView) convertView.findViewById(R.id.etc_item_money);

            convertView.setTag(holder);
        } else {
            holder = (EtcListAdapter.ViewHolder) convertView.getTag();
        }


        NewETCResponse2.DataBean.ListBean bean = mList.get(position);
        holder.time_tv.setText(bean.getTrsTime());
        double money = bean.getAmount()/100;
        holder.money_tv.setText(money+"元");
        return convertView;
    }

    static class ViewHolder {
        TextView time_tv, money_tv;
    }
}
