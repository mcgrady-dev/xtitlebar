package com.mcgrady.xtitlebar;

import android.support.annotation.ColorInt;
import android.support.annotation.Px;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/22
 */

public interface ITitleBarStyle {


    @ColorInt int getLfetTextColor();
    @ColorInt int getTitleColor();
    @ColorInt int getRightTextColor();

    @Px int getLfetTextSize();
    @Px int getTitleSize();
    @Px int getRightTextSize();

}
