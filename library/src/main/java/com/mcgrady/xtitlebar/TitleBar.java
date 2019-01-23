package com.mcgrady.xtitlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcgrady.xtitlebar.utils.DrawableUtils;

import java.util.UUID;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/22
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private TextView tvLfetView, tvTitleView, tvRightView;
    private View leftCustomView, titleCustomView, rightCustomView;

    int padding_12;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.DefaultTitleBarAttr);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr);
    }

    @SuppressLint("ObsoleteSdkInt")
    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
        padding_12 = dp2PxInt(context, 12);
        ViewGroup.LayoutParams globalParams = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setLayoutParams(globalParams);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);

        /**
         * Lfet View
         */
        LayoutParams leftLayoutParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (array.hasValue(R.styleable.TitleBar_layout_left_custom)) {
            leftCustomView = LayoutInflater.from(context).inflate(
                    array.getResourceId(R.styleable.TitleBar_layout_left_custom, 0), this, false);

            if (View.NO_ID == leftCustomView.getId()) {
                leftCustomView.setId(generateViewId());
            }

            addView(leftCustomView, leftLayoutParams);
        } else {
            tvLfetView = new TextView(context);
            tvLfetView.setId(generateViewId());
            tvLfetView.setText(array.getString(R.styleable.TitleBar_leftText));
            tvLfetView.setTextColor(array.getColor(R.styleable.TitleBar_leftTextColor, 0));
            tvLfetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, 0));
            tvLfetView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvLfetView.setSingleLine(true);
            if (array.hasValue(R.styleable.TitleBar_leftDrawableRes)) {
                DrawableUtils.setDrawableLeft(context, tvLfetView, array.getResourceId(R.styleable.TitleBar_leftDrawableRes, 0));
            }
            addView(tvLfetView, leftLayoutParams);
        }

        /**
         * Title View
         */
        LayoutParams titleLayoutParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            titleLayoutParams.setMarginStart(padding_12);
            titleLayoutParams.setMarginEnd(padding_12);
        } else {
            titleLayoutParams.setMargins(padding_12, 0, padding_12, 0);
        }
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (array.hasValue(R.styleable.TitleBar_layout_title_custom)) {

        } else {
            tvTitleView = new TextView(context);
            tvTitleView.setId(generateViewId());
            tvTitleView.setText(array.getString(R.styleable.TitleBar_title));
            tvTitleView.setTextColor(array.getColor(R.styleable.TitleBar_titleColor, 0));
            tvTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBar_titleSize, 0));
            tvTitleView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvTitleView.setSingleLine(true);
            addView(tvTitleView, titleLayoutParams);
        }

        /**
         * Right View
         */
        LayoutParams rightLayoutParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (array.hasValue(R.styleable.TitleBar_layout_right_custom)) {
            rightCustomView = LayoutInflater.from(context).inflate(
                    array.getResourceId(R.styleable.TitleBar_layout_right_custom, 0), this, false);

            if (View.NO_ID == rightCustomView.getId()) {
                rightCustomView.setId(generateViewId());
            }
            addView(rightCustomView, leftLayoutParams);
        } else {
            tvRightView = new TextView(context);
            tvRightView.setId(generateViewId());
            tvRightView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvRightView.setSingleLine(true);
            if (array.hasValue(R.styleable.TitleBar_rightDrawableRes)) {
                DrawableUtils.setDrawableLeft(context, tvRightView, array.getResourceId(R.styleable.TitleBar_rightDrawableRes, 0));
            } else {
                tvRightView.setText(array.getString(R.styleable.TitleBar_rightText));
                tvRightView.setTextColor(array.getColor(R.styleable.TitleBar_rightTextColor, 0));
                tvRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, 0));
            }

            addView(tvRightView, rightLayoutParams);
        }

        array.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        tvLfetView.setOnClickListener(this);
        tvTitleView.setOnClickListener(this);
        tvRightView.setOnClickListener(this);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        tvLfetView.setOnClickListener(null);
        tvTitleView.setOnClickListener(null);
        tvRightView.setOnClickListener(null);
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View view) {

    }

    private int dp2PxInt(Context context, float dp) {
        return (int) (dp2Px(context, dp) + 0.5f);
    }

    private float dp2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * density(context);
    }

    private float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 计算View Id
     *
     * @return
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }
}
