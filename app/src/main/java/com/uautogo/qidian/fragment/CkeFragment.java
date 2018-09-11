package com.uautogo.qidian.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.MakeMoneyActivity;
import com.uautogo.qidian.activity.MoneyDetailActivity;
import com.uautogo.qidian.activity.ProblemActivity;
import com.uautogo.qidian.model.CarMoney;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.math.BigDecimal;

/**
 * Created by uuun on 2018/8/21.
 */

public class CkeFragment extends BaseFragment {
    private View view;
    private Button rechange;
    private Button makeMoney;
    private TextView toMoneyDetail,problem;
    private PopupWindow mPopupWindow;
    private ImageView back;
    private TextView balance;
    private String ckeAddress;
    private boolean state;
    private BigDecimal money;
    private int flag;//0未设置密码,1有密码
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_car_money,container,false);
        initView(view);
        initMethod();
        //initData();
        x.Ext.init(getActivity().getApplication());//Xutils初始化
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    private void initData() {
        String url = ConfigUrl.URL_GETCARMONEY;
        RequestParams params = new RequestParams(url);
        String userId = SharedPreferencesUtils.getString(getActivity().getApplicationContext(), SharedPreferencesUtils.Key.KEY_USER_ID);
        params.addQueryStringParameter("userId", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("我的车币------->",s);
                CarMoney bean = new Gson().fromJson(s, CarMoney.class);
                state = bean.getData().isPayStatus();
                money = BigDecimal.valueOf(bean.getData().getWalletIntegral());
                if(state){
                    flag = 1;
                }else{
                    flag = 0;
                }
                SharedPreferencesUtils.putString(getActivity(),"secretFlag",flag+"");
                if(bean.getData().getWalletIntegral()==0.0){

                    balance.setText(0+ "个");
                }else{
                    balance.setText(money+ "个");
                }

                SharedPreferencesUtils.putString(getActivity(),"ckemoney",money+"");
                ckeAddress = bean.getData().getCkeAddress();

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void initMethod() {

        rechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

        makeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log.e("flag----->",flag+"");
                Intent intent = new Intent(getActivity(), MakeMoneyActivity.class);
                intent.putExtra("money", money+"");
                intent.putExtra("flag",flag);
                startActivity(intent);
            }
        });

        toMoneyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MoneyDetailActivity.class);
                startActivity(intent);
            }
        });
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProblemActivity.class);
                intent.putExtra("web","cke");
                startActivity(intent);
            }
        });

    }

    private void showPop() {

        View view1 = View.inflate(getActivity(), R.layout.pop_car_money, null);

        //创建popupwindow为全屏

        mPopupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //设置动画,就是style里创建的那个j
        mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
        //设置popupwindow的位置,这里直接放到屏幕上就行
        mPopupWindow.showAsDropDown(view1, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        ImageView cancel = (ImageView) view1.findViewById(R.id.pop_cancel);
        final TextView webTv = (TextView) view1.findViewById(R.id.pop_webTv);
        Button copy = (Button) view1.findViewById(R.id.pop_copy);
        TextView where = (TextView) view1.findViewById(R.id.rechange_where);
        where.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        where.getPaint().setAntiAlias(true);//抗锯齿
        webTv.setText(ckeAddress);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", webTv.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getActivity(), "复制成功!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initView(View view) {
        problem = view.findViewById(R.id.carMoney_problem);
        rechange = (Button) view.findViewById(R.id.rechange);
        makeMoney = (Button) view.findViewById(R.id.makeMoney);
        toMoneyDetail = (TextView) view.findViewById(R.id.toMoneyDetail);
        back = (ImageView) view.findViewById(R.id.car_back);
        back.setVisibility(View.GONE);
        balance = (TextView) view.findViewById(R.id.carMoney_balance);
    }
}
