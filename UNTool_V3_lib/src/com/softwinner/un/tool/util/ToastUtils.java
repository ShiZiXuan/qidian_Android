package com.softwinner.un.tool.util;

import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.softwinner.un.tool.ApplicationProxy;
import com.softwinner.un.tool.R;

/**
 * Created by zusanxie on 2015/11/22.
 */
public class ToastUtils {
  private static Toast toast;
  private static TextView toastView;

  public static void showMessage(String text) {
    showMessage(text, Toast.LENGTH_SHORT);
  }

  public static void showMessage(int resId, int durationFlag) {
    showMessage(ApplicationProxy.getInstance().getApplication().getResources().getString(resId), durationFlag);
  }

  public static void showMessage(String text, int durationFlag) {
    if (toast == null || toastView == null) {
      toastView =
          (TextView) LayoutInflater.from(ApplicationProxy.getInstance().getApplication()).inflate(R.layout.toast, null);
      toast = new Toast(ApplicationProxy.getInstance().getApplication());
      toast.setView(toastView);
      toast.setDuration(durationFlag);
    }
    toastView.setText(text);
    toast.show();
  }
}
