package com.uautogo.qidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.CarQueryData;

/**
 * Created by Jeremy on 2017/5/13.
 */

public class QueryResultAdapter extends RecyclerView.Adapter<QueryResultAdapter.QueryHolder> {
    private Context mContext;
    private List<CarQueryData> datas = new ArrayList<>();

    public QueryResultAdapter(Context context){
        mContext = context;
    }

    public void setData(List<CarQueryData> list){
        if (list != null && list.size() > 0){
            datas.clear();
            datas.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public QueryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.car_illegal_query_item, parent, false);
        return new QueryHolder(view);
    }

    @Override
    public void onBindViewHolder(QueryHolder holder, int position) {
        CarQueryData data = datas.get(position);
        if(data != null){
            holder.time.setText(data.date);
            holder.area.setText(data.area);
            holder.action.setText(data.act);
            holder.money.setText(data.money);
            holder.fen.setText(data.fen);
            if (holder.handle.equals("0")){
                holder.handle.setText(mContext.getString(R.string.illegal_query_data_handled));
            }else {
                holder.handle.setText(mContext.getString(R.string.illegal_query_data_no_handled));
            }


        }

    }

    @Override
    public int getItemCount() {
        return datas == null?0:datas.size();
    }

    public class QueryHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView area;
        private TextView action;
        private TextView money;
        private TextView fen;
        private TextView handle;

        public QueryHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.illegal_query_data_time);
            area = (TextView) itemView.findViewById(R.id.illegal_query_data_area);
            action = (TextView) itemView.findViewById(R.id.illegal_query_data_action);
            money = (TextView) itemView.findViewById(R.id.illegal_query_data_money);
            fen = (TextView) itemView.findViewById(R.id.illegal_query_data_fen);
            handle = (TextView) itemView.findViewById(R.id.illegal_query_data_handle);
        }
    }


}
