package com.mcgrady.xtitlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

public class TitleBar extends FrameLayout implements View.OnClickListener {

    private TextView tvLfetView, tvTitleView, tvRightView;
    private View leftCustomView, titleCustomView, rightCustomView, lineView;

    private int padding_10;
    private int titleBarHeight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.DefaultTitleBarRes);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);
        formattionViews(context);
    }

    /**
     * 初始化Views
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @SuppressLint("ObsoleteSdkInt")
    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        padding_10 = dp2px(context, 10);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);

        titleBarHeight = (int) array.getDimension(R.styleable.TitleBar_titleBarHeight, 0);


        ///////////////////////////////////////////////////////////////////////////
        // Lfet View
        ///////////////////////////////////////////////////////////////////////////
        LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);

        if (array.hasValue(R.styleable.TitleBar_layout_left_custom)) {
            leftCustomView = LayoutInflater.from(context).inflate(
                    array.getResourceId(R.styleable.TitleBar_layout_left_custom, 0), this, false);

            if (View.NO_ID == leftCustomView.getId()) {
                leftCustomView.setId(generateViewId());
            }
        } else {
            tvLfetView = new TextViewDirector.Builder()
                    .with(context)
                    .setId(generateViewId())
                    .setLayoutParams(leftLayoutParams)
                    .setGravity(Gravity.CENTER)
                    .setTextColor(array.getColor(R.styleable.TitleBar_leftTextColor, 0))
                    .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, 0))
                    .setSingleLine(true)
                    .setEllipsize(TextUtils.TruncateAt.END)
                    .setText(array.getString(R.styleable.TitleBar_leftText))
                    .create()
                    .construct();

            if (array.hasValue(R.styleable.TitleBar_leftDrawableRes)) {
                DrawableUtils.setDrawableLeft(context, tvLfetView, array.getResourceId(R.styleable.TitleBar_leftDrawableRes, 0));
            }
        }


        ///////////////////////////////////////////////////////////////////////////
        // Title View
        ///////////////////////////////////////////////////////////////////////////
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(0, MATCH_PARENT);
        titleLayoutParams.weight = 1;
        titleLayoutParams.leftMargin = padding_10;
        titleLayoutParams.rightMargin = padding_10;
        if (array.hasValue(R.styleable.TitleBar_layout_title_custom)) {
            //todo: draw custom view
        } else {
            tvTitleView = new TextViewDirector.Builder()
                    .with(context)
                    .setId(generateViewId())
                    .setLayoutParams(titleLayoutParams)
                    .setGravity(Gravity.CENTER)
                    .setTextColor(array.getColor(R.styleable.TitleBar_titleColor, 0))
                    .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_titleSize, 0))
                    .setSingleLine(true)
                    .setEllipsize(TextUtils.TruncateAt.END)
                    .setText(array.getString(R.styleable.TitleBar_title))
                    .create()
                    .construct();
        }


        ///////////////////////////////////////////////////////////////////////////
        // Right View
        ///////////////////////////////////////////////////////////////////////////
        LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        if (array.hasValue(R.styleable.TitleBar_layout_right_custom)) {
            rightCustomView = LayoutInflater.from(context).inflate(
                    array.getResourceId(R.styleable.TitleBar_layout_right_custom, 0), this, false);

            if (View.NO_ID == rightCustomView.getId()) {
                rightCustomView.setId(generateViewId());
            }
        } else {
            tvRightView = new TextView(context);
            tvRightView.setId(generateViewId());
            tvRightView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvRightView.setSingleLine(true);
            if (array.hasValue(R.styleable.TitleBar_rightDrawableRes)) {
                DrawableUtils.setDrawableLeft(context, tvRightView, array.getResourceId(R.styleable.TitleBar_rightDrawableRes, 0));
            } else {
                tvRightView = new TextViewDirector.Builder()
                        .with(context)
                        .setId(generateViewId())
                        .setLayoutParams(rightLayoutParams)
                        .setGravity(Gravity.CENTER)
                        .setTextColor(array.getColor(R.styleable.TitleBar_rightTextColor, 0))
                        .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, 0))
                        .setSingleLine(true)
                        .setEllipsize(TextUtils.TruncateAt.END)
                        .setText(array.getString(R.styleable.TitleBar_rightText))
                        .create()
                        .construct();
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // Line View
        ///////////////////////////////////////////////////////////////////////////
        lineView = new View(context);
        lineView.setId(generateViewId());
        LayoutParams lineLayoutParams = new LayoutParams(MATCH_PARENT, 1);
        lineLayoutParams.gravity = Gravity.BOTTOM;
        lineView.setLayoutParams(lineLayoutParams);


        ///////////////////////////////////////////////////////////////////////////
        // Background
        ///////////////////////////////////////////////////////////////////////////
        if (array.hasValue(R.styleable.TitleBar_titleBarDrawableRes)) {
            setBackgroundResource(array.getResourceId(R.styleable.TitleBar_titleBarDrawableRes, 0));
        } else {
            setBackgroundColor(array.getColor(R.styleable.TitleBar_titleBarColor, 0));
        }

        array.recycle();
    }

    /**
     * 组建Views
     * @param context
     */
    private void formattionViews(Context context) {
        LinearLayout contentLayout = new LinearLayout(context);
        contentLayout.setId(generateViewId());
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        if (leftCustomView != null) {
            contentLayout.addView(leftCustomView);
        } else if (tvLfetView != null) {
            contentLayout.addView(tvLfetView);
        }

        if (titleCustomView != null) {
            contentLayout.addView(titleCustomView);
        } else if (tvTitleView != null) {
            contentLayout.addView(tvTitleView);
        }

        if (rightCustomView != null) {
            contentLayout.addView(rightCustomView);
        } else if (tvLfetView != null) {
            contentLayout.addView(tvLfetView);
        }

        addView(contentLayout, 0);
        addView(lineView, 1);
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

    @SuppressLint({"ObsoleteSdkInt", "DrawAllocation"})
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置TitleBar默认的宽度
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST
                || MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
        }

        // 设置TitleBar默认的高度
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST
                || MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(titleBarHeight, MeasureSpec.EXACTLY);

//            final Drawable background = getBackground();
//            // 如果当前背景是一张图片的话
//            if (background instanceof BitmapDrawable) {
//                mMainLayout.getLayoutParams().height = MeasureSpec.getSize(heightMeasureSpec);
//                // 算出标题栏的宽度和图片的宽度之比例
//                final double ratio = (double) MeasureSpec.getSize(widthMeasureSpec) / (double) background.getIntrinsicWidth();
//                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (ratio * background.getIntrinsicHeight()), MeasureSpec.EXACTLY);
//            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 设置标题
     * @param text
     */
    public void setTitle(CharSequence text) {
        if (tvTitleView != null) {
            tvTitleView.setText(text);
        }
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

    /**
     * dp转px
     */
    static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
