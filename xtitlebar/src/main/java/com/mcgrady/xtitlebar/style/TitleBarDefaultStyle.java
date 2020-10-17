package com.mcgrady.xtitlebar.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

import com.mcgrady.xtitlebar.R;
import com.mcgrady.xtitlebar.SelectorDrawable;

/**
 * Created by mcgrady on 2020/10/10.
 */
public class TitleBarDefaultStyle extends BaseTitleBarStyle {

    public TitleBarDefaultStyle(Context context) {
        super(context);
    }

    @Override
    public int getTitleBarColor() {
        return R.color.titlebar_background;
    }

    @Override
    public int getBottomLineColor() {
        return R.color.titlebar_bottom_line;
    }


    @Override
    public int getLeftTextColor() {
        return R.color.xtitlebar_text_selector;
    }

//    @Override
//    public int getLeftImageRes() {
//        return R.drawable.xtitlebar_reback_selector;
//    }

    @Override
    public Drawable getLeftBackground() {
        // Android 3.0 及以上才可以使用水波纹效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                return getDrawable(typedValue.resourceId);
            }
        }

        return new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x22000000))
                .setPressed(new ColorDrawable(0x22000000))
                .build();
    }


    @Override
    public int getCenterTextColor() {
        return R.color.titlebar_center_text;
    }


    @Override
    public int getCenterSubTextColor() {
        return R.color.titlebar_center_subtext;
    }

    @Override
    public int getRightTextColor() {
        return R.color.xtitlebar_text_selector;
    }

    @Override
    public Drawable getRightBackground() {
        return getLeftBackground();
    }

    @Override
    public boolean isShowBottomLine() {
        return false;
    }

}
