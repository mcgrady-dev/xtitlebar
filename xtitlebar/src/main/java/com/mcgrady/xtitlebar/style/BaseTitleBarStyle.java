package com.mcgrady.xtitlebar.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.mcgrady.xtitlebar.R;
import com.mcgrady.xtitlebar.util.Utils;

/**
 * Created by mcgrady on 2020/9/15.
 */
public abstract class BaseTitleBarStyle implements ITitleBarStyle {

    protected Context context;

    private BaseTitleBarStyle() {}

    public BaseTitleBarStyle(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.titlebar_background;
    }

    @Override
    public int getTitleBarHeight() {
        int actionBarHeight = Utils.getActionBarHeight(context);
        return actionBarHeight == 0 ? Utils.dp2px(56) : actionBarHeight;
    }

    @Override
    public float getLeftTextSize() {
        return Utils.sp2px(14);
    }

    @Override
    public float getRightTextSize() {
        return Utils.sp2px(14);
    }

    @Override
    public float getCenterTextSize() {
        return Utils.sp2px(16);
    }

    @Override
    public float getCenterSubTextSize() {
        return Utils.sp2px(10);
    }


    protected Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
