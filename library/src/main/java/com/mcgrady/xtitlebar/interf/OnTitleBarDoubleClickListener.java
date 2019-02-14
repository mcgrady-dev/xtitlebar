package com.mcgrady.xtitlebar.interf;

import android.view.View;

import com.mcgrady.xtitlebar.TitleBarClickAction;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/25
 */

public interface OnTitleBarDoubleClickListener {

    void onDoubleClick(View view, TitleBarClickAction action);
}
