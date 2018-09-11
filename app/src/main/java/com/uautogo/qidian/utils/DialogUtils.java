package com.uautogo.qidian.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;

import com.uautogo.qidian.R;


public class DialogUtils {


    /**
     * 显示双按钮提示对话框
     */
    public static void showDoubleButtonDialog(Context context, String title, String content,
                                              String positiveButtonText, String negativeButtonText,
                                              DialogInterface.OnClickListener positiveButtonListener,
                                              DialogInterface.OnClickListener negativeButtonListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.setTitle(title)
                .setIcon(R.drawable.iconb1)
                .setMessage(content)
                .setPositiveButton(positiveButtonText, positiveButtonListener)
                .setNegativeButton(negativeButtonText, negativeButtonListener)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alertDialog.show();
    }

}
