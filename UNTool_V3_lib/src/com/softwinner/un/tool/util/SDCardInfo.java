package com.softwinner.un.tool.util;

/**
 * Created by baienda on 2017/9/28.
 */

public class SDCardInfo {
    private static long remain;
    private  static long total;

    public static long getRemain() {
        return remain;
    }

    public static void setRemain(long r) {
        remain = r;
    }

    public static long getTotal() {
        return total;
    }

    public static void setTotal(long t) {
        total = t;
    }

}
