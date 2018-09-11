package com.uautogo.qidian.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;

import com.uautogo.qidian.R;

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2018-02-01 下午2:56:14
 */
public class CallDialog {
    private AlertDialog mDialog;
    private OnSureListener mListener;

    public CallDialog(Activity activity) {
        init(activity);
    }

    private void init(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.content_call, null, false);
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog = new AlertDialog.Builder(activity)
                .setView(view)
                .create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (mListener != null)
                    mListener.onSure();
            }
        });
    }

    public void setOnSureListener(OnSureListener listener) {
        mListener = listener;
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public interface OnSureListener {
        void onSure();
    }
}
