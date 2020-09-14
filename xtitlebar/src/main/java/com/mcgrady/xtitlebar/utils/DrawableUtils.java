package com.mcgrady.xtitlebar.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/22
 */

public class DrawableUtils {

    public static void setDrawableLeft(Context context, TextView textView, int resId) {
        setCompoundDrawables(textView, getDrawable(context, resId), null, null, null);
    }

    public static void setDrawableRight(Context context, TextView textView, int resId) {
        setCompoundDrawables(textView,null, null, getDrawable(context, resId), null);
    }

    public static void setDrawableTop(Context context, TextView textView, int resId) {
        setCompoundDrawables(textView,null, getDrawable(context, resId), null, null);
    }
    public static void setDrawableBottom(Context context, TextView textView, int resId) {
        setCompoundDrawables(textView, null, null, null, getDrawable(context, resId));
    }

    public static void resetDrawable(TextView textView) {
        textView.setCompoundDrawables(null, null, null, null);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setCompoundDrawables(TextView textView, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    private static Drawable getDrawable(Context context, int resId) {
        if (resId == -1) {
            return null;
        }
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }
}
