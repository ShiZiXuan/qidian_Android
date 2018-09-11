package com.uautogo.qidian.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.model.OrderRespons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Serenade on 2017/8/31.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> implements View.OnClickListener {
    private Context mContext;
    private List<OrderRespons.Order> mData;
    private OnItemClickListener mOnItemClickListener;
    private Handler mHandler;

    public OrderAdapter(Context context, List mData) {
        this.mContext = context;
        this.mData = mData;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public OrderAdapter.OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        view.setOnClickListener(this);
        return new OrderHolder(view);
    }
    @Override
    public void onBindViewHolder(OrderAdapter.OrderHolder holder, int position) {
        OrderRespons.Order order = mData.get(position);
        boolean paid = false;
        String status = order.getStatus();
        String state = " ";
        boolean showRepayBtn = false;
        switch (status) {
            case "active":
                state = "待支付";
                showRepayBtn = true;
                break;
            case "paid":
                paid = true;
                state = "已支付";
                break;
            case "canceled":
                state = "已取消";
                break;
            case "expired":
                state = "订单过期未支付";
                break;
            case "refund_pending":
                state = "申请退款中";
                break;
            case "refund_refused":
                state = "退款拒绝";
                break;
            case "refund_ok":
                state = "退款成功";
                break;
        }

//        if (showRepayBtn)
//            holder.rePay_btn.setVisibility(View.VISIBLE);
//        else
//            holder.rePay_btn.setVisibility(View.INVISIBLE);
//
//
        long time_number;
        if (paid) {
            time_number = order.getPayTime();
            //holder.refund_btn.setVisibility(View.VISIBLE);
        } else {
            time_number = order.getCtime();
            //holder.refund_btn.setVisibility(View.GONE);
        }
//        int refundStatus = order.getRefundStatus();
//        if (refundStatus==0)
//            holder.refund_btn.setText("退押金");
//        else
//            holder.refund_btn.setText("申请成功");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(time_number));
        holder.time_tv.setText(time);

        holder.number_tv.setText("订单号：" + order.getId());
        holder.money_tv.setText("￥" + order.getAmount());
        holder.itemView.setTag(position);
        holder.title_tv.setText(order.getOrderTitle());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        private TextView title_tv, time_tv, number_tv, money_tv;


        public OrderHolder(final View itemView) {
            super(itemView);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            number_tv = (TextView) itemView.findViewById(R.id.number_tv);
            money_tv = (TextView) itemView.findViewById(R.id.money_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);


//            refund_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DepositActivity.class);
//                    intent.putExtra("order", mData.get((Integer) itemView.getTag()));
//                    mContext.startActivity(intent);
//                }
//            });
//            rePay_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final OrderRespons.Order order = mData.get((Integer) itemView.getTag());
//                    int orderId = order.getId();
//                    Log.e("orderId===========", "==" + orderId);
//                    QidianRequestApi.getInstance().rePay(orderId, new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            String json = null;
//                            try {
//                                json = response.body().string().trim();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            json = json.substring(1, json.length() - 1);
//                            Log.e("===json===", "======" + json);
//                            String data = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
//                            Log.e("===data===", "======" + data);
//                            Pingpp.createPayment((Activity) mContext, data.toString());
//                            Log.e("=================", "===qqqq" +
//                                    "===" + data.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });
//                }
//            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}