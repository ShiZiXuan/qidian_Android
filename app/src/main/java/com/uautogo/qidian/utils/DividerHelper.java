package com.uautogo.qidian.utils;

import android.support.v7.widget.OrientationHelper;

/**
 * Created by serenade on 17-12-13.
 */

public class DividerHelper {
    public static boolean isLastColumn(int index, int span, int total, int orientation) {
        if (orientation == OrientationHelper.VERTICAL) {//纵向滚动
            if ((index + 1) % span == 0)
                return true;
        } else {//横向滚动
            if (total % span == 0)
                total -= span;
            else
                total -= total % span;
            if (index >= total)
                return true;
        }
        return false;
    }

    public static boolean isLastRow(int index, int span, int total, int orientation) {
        if (orientation == OrientationHelper.VERTICAL) {//纵向滚动
            if (total % span == 0)
                total -= span;
            else
                total -= total % span;
            if (index >= total)
                return true;
        } else {//横向滚动
            if ((index + 1) % span == 0)
                return true;
        }
        return false;
    }
}
