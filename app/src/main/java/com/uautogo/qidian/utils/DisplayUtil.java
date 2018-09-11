package com.uautogo.qidian.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtil {
  public static int DEFAULT_WIDTH = 480;
  public static int DEFAULT_HEIGHT = 800;

  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  public static float getDensity(Context context, float pxValue) {
    return context.getResources().getDisplayMetrics().density;
  }

  public static final int getScreenWidth(final Context context) {
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.widthPixels == 0?DEFAULT_WIDTH:outMetrics.widthPixels;
  }

  public static final int getScreenHeight(final Context context) {
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.heightPixels == 0?DEFAULT_HEIGHT:outMetrics.heightPixels;
  }

  public static Drawable getDrawable(Context context, int resId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      return context.getDrawable(resId);
    } else {
      return context.getResources().getDrawable(resId);
    }
  }
}
