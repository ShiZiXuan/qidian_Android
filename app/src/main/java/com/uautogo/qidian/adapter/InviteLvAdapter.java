package com.uautogo.qidian.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uautogo.qidian.R;
import com.uautogo.qidian.model.InviteBean;

import java.util.List;

/**
 * Created by uuun on 2018/8/20.
 */

public class InviteLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<InviteBean.DataBean.ListBean> list;

    public InviteLvAdapter(Context mContext, List<InviteBean.DataBean.ListBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.invite_lv_item,viewGroup,false);
            holder.iv = view.findViewById(R.id.invite_iv);
            holder.name = view.findViewById(R.id.invite_name);
            holder.state = view.findViewById(R.id.invite_state);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        InviteBean.DataBean.ListBean bean = list.get(i);


        //0未办理  1办理成功
        int status = bean.getStatus();
        if(status==0){
            holder.state.setText("未办理");
        }else{
            holder.state.setText("办理成功");
        }
        if(!TextUtils.isEmpty(bean.getUserAvatar())){
             Glide.with(mContext).load(bean.getUserAvatar()).into(holder.iv);
        }else{
            holder.iv.setImageResource(R.drawable.share_head);
        }

        holder.name.setText(bean.getUserName());

        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView name,state;
    }
}
