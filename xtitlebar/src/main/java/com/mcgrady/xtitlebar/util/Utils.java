package com.mcgrady.xtitlebar.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by mcgrady on 2020/9/14.
 */
public final class Utils {

    /**
     * Value of dp to value of px.
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Value of px to value of dp.
     *
     * @param pxValue The value of px.
     * @return value of dp
     */
    public static int px2dp(final float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Value of sp to value of px.
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Value of px to value of sp.
     *
     * @param pxValue The value of px.
     * @return value of sp
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * Return the navigation bar's height.
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Show the soft input.
     */
    public static void showSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * Show the soft input.
     */
    public static void showSoftInput(@NonNull Activity activity) {
        if (!isSoftInputVisible(activity)) {
            toggleSoftInput(activity);
        }
    }

    /**
     * Show the soft input.
     *
     * @param view The view.
     */
    public static void showSoftInput(Context context, @NonNull final View view) {
        showSoftInput(context, view, 0);
    }

    /**
     * Show the soft input.
     *
     * @param view  The view.
     * @param flags Provides additional operating flags.  Currently may be
     *              0 or have the {@link InputMethodManager#SHOW_IMPLICIT} bit set.
     */
    public static void showSoftInput(Context context, @NonNull final View view, final int flags) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, flags, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput(context);
                }
            }
        });
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(@NonNull final Activity activity) {
        Window window = activity.getWindow();
        View view = window.getCurrentFocus();
        if (view == null) {
            View decorView = window.getDecorView();
            View focusView = decorView.findViewWithTag("keyboardTagView");
            if (focusView == null) {
                view = new EditText(window.getContext());
                view.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(view, 0, 0);
            } else {
                view = focusView;
            }
            view.requestFocus();
        }
        hideSoftInput(activity, view);
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(Context context, @NonNull final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static long millis;

    /**
     * Hide the soft input.
     *
     * @param activity The activity.
     */
    public static void hideSoftInputByToggle(final Activity activity) {
        long nowMillis = System.currentTimeMillis();
        long delta = nowMillis - millis;
        if (Math.abs(delta) > 500 && isSoftInputVisible(activity)) {
            toggleSoftInput(activity);
        }
        millis = nowMillis;
    }

    /**
     * Toggle the soft input display or not.
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(0, 0);
    }

    private static int sDecorViewDelta = 0;

    /**
     * Return whether soft input is visible.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(@NonNull final Activity activity) {
        return getDecorViewInvisibleHeight(activity.getWindow()) > 0;
    }

    private static int getDecorViewInvisibleHeight(@NonNull final Window window) {
        final View decorView = window.getDecorView();
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: "
                + (decorView.getBottom() - outRect.bottom));
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight() + getStatusBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    /**
     * Return the width and height of screen, in pixel.
     *
     * @return the width and height of screen, in pixel
     */
    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return size;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }

        size[0] = point.x;
        size[1] = point.y;
        return size;
    }

    /**
     * Return the action bar's height.
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, Resources.getSystem().getDisplayMetrics()
            );
        }
        return 0;
    }

    public static boolean supportTransparentStatusBar() {
        return OSUtils.isMiui()
                || OSUtils.isFlyme()
                || (OSUtils.isOppo() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
