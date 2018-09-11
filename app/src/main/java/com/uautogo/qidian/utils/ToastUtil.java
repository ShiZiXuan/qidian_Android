package com.uautogo.qidian.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jeremy on 2017/5/28.
 */

public class ToastUtil {
    public static void show(Context context,String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }
    public static void show(Context context,int resId){
        Toast.makeText(context,context.getString(resId),Toast.LENGTH_SHORT).show();
    }
}
