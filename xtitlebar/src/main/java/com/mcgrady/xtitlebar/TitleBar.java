package com.mcgrady.xtitlebar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcgrady.xtitlebar.interf.OnTitleBarCustomViewClickListener;
import com.mcgrady.xtitlebar.interf.OnTitleBarListener;
import com.mcgrady.xtitlebar.utils.DrawableUtils;

import java.util.UUID;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * <p>自定义TitleBar</p>
 *
 * @author: mcgrady
 * @date: 2019/1/22
 */

public class TitleBar extends FrameLayout implements View.OnClickListener, Runnable {

    private TextView tvLeftView, tvTitleView, tvRightView;
    private View leftCustomView, titleCustomView, rightCustomView, dividerLineView;
    private LinearLayout contentLayout;
    private OnTitleBarListener listener;

    private int titleGravity;
    private int titleBarHeight;
    private int padding_12;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context, attrs, defStyleAttr, 0);
        formattionViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

        padding_12 = dp2px(context, 12);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr,
                defStyleRes <= 0 ? R.style.DefaultTitleBarRes : defStyleRes);

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

            leftCustomView.setLayoutParams(leftLayoutParams);
        } else {
            tvLeftView = new TextViewDirector.Builder()
                    .with(context)
                    .setId(generateViewId())
                    .setLayoutParams(leftLayoutParams)
                    .setGravity(Gravity.CENTER)
                    .setPadding(padding_12, 0, padding_12, 0)
                    .setTextColor(array.getColor(R.styleable.TitleBar_leftTextColor, 0))
                    .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, 0))
                    .setSingleLine(true)
                    .setEllipsize(TextUtils.TruncateAt.END)
                    .setText(array.getString(R.styleable.TitleBar_leftText))
                    .setOnClickListener(this)
                    .create()
                    .construct();

            if (array.hasValue(R.styleable.TitleBar_leftDrawableRes)) {
                DrawableUtils.setDrawableLeft(context, tvLeftView, array.getResourceId(R.styleable.TitleBar_leftDrawableRes, 0));
                tvLeftView.setCompoundDrawablePadding(array.getDimensionPixelOffset(R.styleable.TitleBar_leftDrawablePadding, 0));
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int[][] stateList = new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated},
                        new int[]{}
                };

                //深蓝
                int normalColor = Color.parseColor("#303F9F");
                //玫瑰红
                int pressedColor = Color.parseColor("#FF4081");
                int[] stateColorList = new int[]{
                        pressedColor,
                        pressedColor,
                        pressedColor,
                        normalColor
                };
                ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

                RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, null);


                tvLeftView.setBackground(rippleDrawable);
            }
        }


        ///////////////////////////////////////////////////////////////////////////
        // Title View
        ///////////////////////////////////////////////////////////////////////////
        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(0, MATCH_PARENT);
        titleLayoutParams.weight = 1;
        titleLayoutParams.leftMargin = array.getDimensionPixelOffset(R.styleable.TitleBar_titleViewLeftMargin, 0);
        titleLayoutParams.rightMargin = array.getDimensionPixelOffset(R.styleable.TitleBar_titleViewRightMargin, 0);
        if (array.hasValue(R.styleable.TitleBar_layout_title_custom)) {
            titleCustomView = LayoutInflater.from(context).inflate(
                    array.getResourceId(R.styleable.TitleBar_layout_title_custom, 0), this, false);

            if (View.NO_ID == titleCustomView.getId()) {
                titleCustomView.setId(generateViewId());
            }

            titleCustomView.setLayoutParams(titleLayoutParams);
        } else {

            tvTitleView = new TextViewDirector.Builder()
                    .with(context)
                    .setId(generateViewId())
                    .setLayoutParams(titleLayoutParams)
                    .setTextColor(array.getColor(R.styleable.TitleBar_titleColor, 0))
                    .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_titleSize, 0))
                    .setSingleLine(true)
                    .setEllipsize(TextUtils.TruncateAt.END)
                    .setText(array.getString(R.styleable.TitleBar_title))
                    .setOnClickListener(this)
                    .create()
                    .construct();

            titleGravity = array.getInteger(R.styleable.TitleBar_titleGravity, 0);
            switch (titleGravity) {
                case 0:
                    tvTitleView.setGravity(Gravity.CENTER);
                    break;
                case 1:
                    tvTitleView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    break;
                case 2:
                    tvTitleView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    break;
                default:
                    tvTitleView.setGravity(Gravity.CENTER);
                    break;
            }

            boolean titleMarquee = array.getBoolean(R.styleable.TitleBar_titleMarquee, false);
            if (titleMarquee) {
                tvTitleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tvTitleView.setMarqueeRepeatLimit(-1);
                tvTitleView.requestFocus();
                tvTitleView.setSelected(true);
            }
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

            rightCustomView.setLayoutParams(rightLayoutParams);
        } else {
            if (array.hasValue(R.styleable.TitleBar_rightDrawableRes)) {
                tvRightView = new TextViewDirector.Builder()
                        .with(context)
                        .setId(generateViewId())
                        .setLayoutParams(rightLayoutParams)
                        .setGravity(Gravity.START | Gravity.CENTER_VERTICAL)
                        .setPadding(dp2px(context, 12), 0, dp2px(context, 12), 0)
                        .setSingleLine(true)
                        .setOnClickListener(this)
                        .create()
                        .construct();

                DrawableUtils.setDrawableLeft(context, tvRightView, array.getResourceId(R.styleable.TitleBar_rightDrawableRes, 0));
            } else {
                tvRightView = new TextViewDirector.Builder()
                        .with(context)
                        .setId(generateViewId())
                        .setLayoutParams(rightLayoutParams)
                        .setGravity(Gravity.CENTER)
                        .setTextColor(array.getColor(R.styleable.TitleBar_rightTextColor, 0))
                        .setTextSize(array.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, 0))
                        .setPadding(padding_12, 0, padding_12, 0)
                        .setSingleLine(true)
                        .setEllipsize(TextUtils.TruncateAt.END)
                        .setText(array.getString(R.styleable.TitleBar_rightText))
                        .setOnClickListener(this)
                        .create()
                        .construct();
            }
        }


        ///////////////////////////////////////////////////////////////////////////
        // Line View
        ///////////////////////////////////////////////////////////////////////////
        dividerLineView = new View(context);
        dividerLineView.setId(generateViewId());
        LayoutParams lineLayoutParams = new LayoutParams(MATCH_PARENT, 1);
        lineLayoutParams.gravity = Gravity.BOTTOM;
        dividerLineView.setLayoutParams(lineLayoutParams);
        setDividerLineVisible(array.getBoolean(R.styleable.TitleBar_dividerLineVisibility, false));
        setDividerLineSize(array.getDimensionPixelSize(R.styleable.TitleBar_dividerLineSize, 1));
        setDividerLineColor(array.getDrawable(R.styleable.TitleBar_dividerLineColor));


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
        contentLayout = new LinearLayout(context);
        contentLayout.setId(generateViewId());
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        if (leftCustomView != null) {
            contentLayout.addView(leftCustomView);
        } else if (tvLeftView != null) {
            contentLayout.addView(tvLeftView);
        }

        if (titleCustomView != null) {
            contentLayout.addView(titleCustomView);
        } else if (tvTitleView != null) {
            contentLayout.addView(tvTitleView);
        }

        if (rightCustomView != null) {
            contentLayout.addView(rightCustomView);
        } else if (tvRightView != null) {
            contentLayout.addView(tvRightView);
        }

        addView(contentLayout, 0);
        addView(dividerLineView, 1);

        // 初始化边距
        post(this);
    }

    @Override
    protected void onAttachedToWindow() {
        if (tvLeftView != null) {
            tvLeftView.setOnClickListener(this);
        }
        if (tvTitleView != null) {
            tvTitleView.setOnClickListener(this);
        }
        if (tvRightView != null) {
            tvRightView.setOnClickListener(this);
        }
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (tvLeftView != null) {
            tvLeftView.setOnClickListener(null);
        }
        if (tvTitleView != null) {
            tvTitleView.setOnClickListener(null);
        }
        if (tvRightView != null) {
            tvRightView.setOnClickListener(null);
        }
        super.onDetachedFromWindow();
    }

    /**
     * 注意：不支持自定义view点击事件
     * @param listener
     */
    public void setOnTitleBarClickListener(OnTitleBarListener listener) {
        this.listener = listener;
    }

    /**
     * 屏蔽点击事件
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        return;
    }

    @Override
    public void onClick(View view) {
        if (listener == null) {
            return;
        }

        if (view.equals(tvLeftView)) {
            listener.onClick(view, TitleBarClickAction.ACTION_LEFT_VIEW);
        } else if (view.equals(tvRightView)) {
            listener.onClick(view, TitleBarClickAction.ACTION_RIGHT_VIEW);
        } else if (view.equals(tvTitleView)) {
            listener.onClick(view, TitleBarClickAction.ACTION_TITLE_VIEW);
        }
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

            final Drawable background = getBackground();
            // 如果当前背景是一张图片的话
            if (background instanceof BitmapDrawable) {
                contentLayout.getLayoutParams().height = MeasureSpec.getSize(heightMeasureSpec);
                // 算出标题栏的宽度和图片的宽度之比例
                final double ratio = (double) MeasureSpec.getSize(widthMeasureSpec) / (double) background.getIntrinsicWidth();
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (ratio * background.getIntrinsicHeight()), MeasureSpec.EXACTLY);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void run() {
        // 更新中间标题的内边距，避免向左或者向右偏移
        int leftViewWidth = leftCustomView == null ? tvLeftView == null ? 0 : tvLeftView.getWidth() : leftCustomView.getWidth();
        int rightViewWidth = rightCustomView == null ? tvRightView == null ? 0 : tvRightView.getWidth() : rightCustomView.getWidth();
        if (leftViewWidth != rightViewWidth) {
            if (leftViewWidth > rightViewWidth) {
                if (titleCustomView != null) {
                    titleCustomView.setPadding(0, 0, leftViewWidth - rightViewWidth, 0);
                } else if (tvTitleView != null) {
                    tvTitleView.setPadding(0, 0, titleGravity == 0 ? leftViewWidth - rightViewWidth : 0, 0);
                }
            } else {
                if (titleCustomView != null) {
                    titleCustomView.setPadding(rightViewWidth - leftViewWidth, 0, 0, 0);
                } else if (tvTitleView != null) {
                    tvTitleView.setPadding(titleGravity == 0 ? rightViewWidth - leftViewWidth : 0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * 设置标题
     * @param text
     */
    public void setTitle(CharSequence text) {
        if (tvTitleView != null) {
            tvTitleView.setText(text);
            post(this);
        }
    }

    /**
     * 设置标题颜色
     * @param color
     */
    public void setTitleColor(@ColorInt int color) {
        if (tvTitleView != null) {
            tvTitleView.setTextColor(color);
        }
    }

    /**
     * 设置标题字体大小
     * @param size
     */
    public void setTitleSize(int size) {
        if (tvTitleView != null) {
            tvTitleView.setTextSize(size);
            post(this);
        }
    }

    /**
     * 设置标题对齐方式
     * @param gravity
     */
    public void setTitleGravity(int gravity) {
        if (tvTitleView != null) {
            tvTitleView.setGravity(gravity);
            post(this);
        }
    }

    /**
     * 设置左边文字
     * @param text
     */
    public void setLeftText(String text) {
        if (tvLeftView != null) {
            tvLeftView.setText(text);
            post(this);
        }
    }

    /**
     * 设置左边文字颜色
     * @param color
     */
    public void setLeftTextColor(@ColorInt int color) {
        if (tvLeftView != null) {
            tvLeftView.setTextColor(color);
        }
    }

    /**
     * 设置左边文字大小
     * @param size
     */
    public void setLeftTextSize(int size) {
        if (tvLeftView != null) {
            tvLeftView.setTextSize(size);
            post(this);
        }
    }

    /**
     * 设置左边图标
     * @param resId
     */
    public void setLeftDrawable(int resId) {
        if (tvLeftView != null) {
            DrawableUtils.setDrawableLeft(getContext(), tvLeftView, resId);
            post(this);
        }
    }

    /**
     * 设置右边图标
     * @param resId
     */
    public void setRightDrawable(int resId) {
        if (tvRightView != null) {
            DrawableUtils.setDrawableLeft(getContext(), tvRightView, resId);
            post(this);
        }
    }

    public View getTitleCustomView() {
        return titleCustomView;
    }

    public View getLeftCustomView() {
        return leftCustomView;
    }

    public View getRightCustomView() {
        return rightCustomView;
    }

    public <T extends View> T findLeftChildViewById(@IdRes int id) {
        if (leftCustomView == null) {
            return null;
        }

        return leftCustomView.findViewById(id);
    }

    public <T extends View> T findRightChildViewById(@IdRes int id) {
        if (rightCustomView == null) {
            return null;
        }

        return rightCustomView.findViewById(id);
    }

    public <T extends View> T findTitleChildViewById(@IdRes int id) {
        if (titleCustomView == null) {
            return null;
        }

        return titleCustomView.findViewById(id);
    }


    /**
     * 设置左自定义View点击事件
     * @param listener
     * @param ids
     */
    public void setOnLeftViewsClick(OnTitleBarCustomViewClickListener listener, int... ids) {
        if (leftCustomView != null) {
            for (int id : ids) {
                View viewById = leftCustomView.findViewById(id);
                if (viewById != null) {
                    viewById.setOnClickListener(view -> {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    });
                }
            }
        }
    }

    /**
     * 设置Tile自定义View点击事件
     * @param listener
     * @param ids
     */
    public void setOnTitleViewsClick(OnTitleBarCustomViewClickListener listener, int... ids) {
        if (titleCustomView != null) {
            for (int id : ids) {
                View viewById = titleCustomView.findViewById(id);
                if (viewById != null) {
                    viewById.setOnClickListener(view -> {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    });
                }
            }
        }
    }

    /**
     * 设置右自定义View点击事件
     * @param listener
     * @param ids
     */
    public void setOnRightViewsClick(OnTitleBarCustomViewClickListener listener, int... ids) {
        if (rightCustomView != null) {
            for (int id : ids) {
                View viewById = rightCustomView.findViewById(id);
                if (viewById != null) {
                    viewById.setOnClickListener(view -> {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    });
                }
            }
        }
    }

    /**
     * 设置分割线是否显示
     * @param visible
     */
    public void setDividerLineVisible(boolean visible) {
        dividerLineView.setVisibility(visible ? VISIBLE : GONE);
    }


    /**
     * 设置分割线颜色
     * @param color
     */
    public void setDividerLineColor(int color) {
        setDividerLineColor(new ColorDrawable(color));
    }

    /**
     * 设置分割线颜色
     * @param drawable
     */
    public void setDividerLineColor(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dividerLineView.setBackground(drawable);
        } else {
            dividerLineView.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置分割线大小
     * @param size
     */
    public void setDividerLineSize(int size) {
        ViewGroup.LayoutParams layoutParams = dividerLineView.getLayoutParams();
        layoutParams.height = size;
        dividerLineView.setLayoutParams(layoutParams);
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
