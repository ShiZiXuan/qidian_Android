package com.uautogo.qidian.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

/**
 * Created by Serenade on 2017/9/21.
 */

public class PListener implements RationaleListener {
    private Context activity;

    public PListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
        final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("权限已被拒绝")
                .setMessage("您已经拒绝过我们申请授权，请您同意授权，否则功能无法正常使用!")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rationale.resume();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rationale.cancel();
                    }
                }).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        .setTextColor(Color.BLACK);
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                        .setTextColor(Color.BLACK);
            }
        });
        alertDialog.show();
    }
}
