package com.uautogo.qidian.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Jeremy on 2017/6/3.
 */

public class UpdateUtil {

    public static String getCurrentVersion(Context context) {
        String version = SharedPreferencesUtils.getString(context, SharedPreferencesUtils.Key.KEY_DEVICE_VERSION, "V0.0.0");
        return version;
    }

    public static boolean isNeedUpdate(String serverCode, Context context) {
        String server = serverCode.substring(1);
        String current = getCurrentVersion(context).substring(1);
        String[] servers = server.split("\\.");
        String[] currents = current.split("\\.");
        boolean flag = false;
        try {
            if (Integer.parseInt(servers[0]) > Integer.parseInt(currents[0])) {
                flag = true;
            } else {
                if (Integer.parseInt(servers[1]) > Integer.parseInt(currents[1])) {
                    flag = true;
                } else {
                    if (Integer.parseInt(servers[2]) > Integer.parseInt(currents[2])) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            flag = false;
            Log.e("出错了==========", "==" + e.getMessage());
            e.printStackTrace();
        }

        Log.e("是否需要升级=======","===="+flag);
        return flag;
    }
}