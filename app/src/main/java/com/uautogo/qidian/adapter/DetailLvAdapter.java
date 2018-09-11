package com.uautogo.qidian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.CostDetailBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by uuun on 2018/7/31.
 */

public class DetailLvAdapter extends BaseAdapter {
    private List<CostDetailBean.DataBean.ListBean> list;
    private Context mContext;

    public DetailLvAdapter(List<CostDetailBean.DataBean.ListBean> list, Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.detail_item,viewGroup,false);
            holder.cost = (TextView) view.findViewById(R.id.detail_item_cost);
            holder.time = (TextView) view.findViewById(R.id.detail_item_time);
            holder.money = (TextView) view.findViewById(R.id.detail_item_money);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        CostDetailBean.DataBean.ListBean bean = list.get(i);
        BigDecimal money = BigDecimal.valueOf(bean.getTrsNum());

        int trsType = bean.getTrsType();
        String type = null;
        if(trsType==0){
            type = "支付奖励积分";
        }else if(trsType==1){
            type = "邀请奖励积分";
        }else if(trsType==2){
            type = "推广增发CKE";
        }else if(trsType==3){
            type = "非控钱包转入CKE";
        }else if(trsType==4){
            type = "CKE提现";
        }else if(trsType==5){
            type = "支付通行费用";
        }else if(trsType==6){
            type = "加油卡充值";
        }
        if(trsType==0||trsType==1||trsType==2||trsType==3){
            holder.money.setText("+"+money);
            holder.money.setTextColor(Color.parseColor("#0099ff"));
        }else{
            holder.money.setText("-"+money);
            holder.money.setTextColor(Color.parseColor("#ff0000"));
        }

        holder.cost.setText(type);
        holder.time.setText(bean.getTrsCreateTime());

        return view;
    }
    class ViewHolder{
        TextView cost,time,money;
    }
}
