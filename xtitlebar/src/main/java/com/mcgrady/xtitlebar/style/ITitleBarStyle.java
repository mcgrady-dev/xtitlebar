package com.mcgrady.xtitlebar.style;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by mcgrady on 2019/1/22.
 */
public interface ITitleBarStyle {

    Context getContext();

    int getStatusBarColor();

    /**
     * 标题栏背景
     */
    int getTitleBarColor();

    /**
     * 标题栏高度（默认为ActionBar的高度）
     */
    int getTitleBarHeight();

    int getBottomLineColor();

    /**
     * 左边文本颜色
     */
    int getLeftTextColor();

    /**
     * 左标题文本大小
     */
    float getLeftTextSize();

    /**
     * 左标题背景
     */
    Drawable getLeftBackground();

    int getCenterTextColor();

    float getCenterTextSize();

    int getCenterSubTextColor();

    float getCenterSubTextSize();

    /**
     * 右标题文本颜色
     */
    int getRightTextColor();

    /**
     * 右标题文本大小
     */
    float getRightTextSize();

    Drawable getRightBackground();

    boolean isShowBottomLine();


}
