package com.ypk.library.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author ypk
 * 创建日期：2021/9/15  14:48
 * 描述：
 */
public class DisplayUtil {

    public static int dp2px(Context context, Float dpValue) {
        if (context == null) return (int) (dpValue * 1.5f + 0.5f);
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, Float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, Float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
