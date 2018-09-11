package com.uautogo.qidian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.MyCarBean;

import java.util.List;

/**
 * Created by linjing on 2017/9/23.
 */

public class CarInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyCarBean.DataBean> list;

    public CarInfoAdapter(Context mContext, List<MyCarBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarInfoHolder holder = null;
        if(convertView == null){
            holder= new CarInfoHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_car_info,parent,false);
            holder.number = convertView.findViewById(R.id.car_item_number);
            holder.type = convertView.findViewById(R.id.car_item_type);
            holder.carNumber = convertView.findViewById(R.id.car_item_code);
            holder.carModule = convertView.findViewById(R.id.car_item_module);
            holder.state = convertView.findViewById(R.id.car_item_state);
            convertView.setTag(holder);
        }else {
            holder = (CarInfoHolder) convertView.getTag();
        }
        MyCarBean.DataBean bean = list.get(position);
        holder.number.setText("车牌号:"+bean.getBidCarNumber());
        holder.type.setText("车型:"+bean.getBidModel());
        holder.carNumber.setText("车牌识别代码"+bean.getBidCarDistinguishCode());
        holder.carModule.setText("型号:"+bean.getBidVehicleType());
        if(bean.getBidAuditEtcStatus()==0){
            holder.state.setText("办理中");
            holder.state.setTextColor(Color.parseColor("#FFD54B"));
        }else if(bean.getBidAuditEtcStatus()==1){
            holder.state.setText("办理成功");
            holder.state.setTextColor(Color.BLACK);
        }else{
            holder.state.setText("办理失败");
            holder.state.setTextColor(Color.RED);
        }

        return convertView;
    }

    static class CarInfoHolder{
        TextView number,type,carNumber,carModule,state;
    }
}
