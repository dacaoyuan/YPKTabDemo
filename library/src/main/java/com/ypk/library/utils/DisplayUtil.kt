package com.ypk.library.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by 陈岗不姓陈 on 2017/10/30.
 *
 *
 */
object DisplayUtil {
    @JvmStatic
    fun dp2px(context: Context?, dpValue: Float): Int {
        if (context == null) return (dpValue * 1.5f + 0.5f).toInt()
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * sp转px
     */
    @JvmStatic
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, context.resources.displayMetrics
        ).toInt()
    }

    /**
     * px转sp
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }
}