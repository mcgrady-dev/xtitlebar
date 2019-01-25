package com.mcgrady.xtitlebar;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>构建TextView</p>
 *
 * @author: mcgrady
 * @date: 2019/1/25
 */

public class TextViewDirector {

    private Context context;
    private int id;
    private CharSequence text;
    private int gravity;
    private boolean singleLine;
    private TextUtils.TruncateAt ellipsize;
    private int color;
    private float size;
    private LinearLayout.LayoutParams layoutParams;
    private View.OnClickListener listener;

    private TextViewDirector() {
    }

    public TextViewDirector(Builder builder) {
        context = builder.context;
        id = builder.id;
        gravity = builder.gravity;
        singleLine = builder.singleLine;
        ellipsize = builder.ellipsize;
        color = builder.color;
        size = builder.size;
        layoutParams = builder.layoutParams;
        text = builder.text;
        listener = builder.listener;
    }

    public TextView construct() {
        if (context == null) {
            return null;
        }

        TextView textView;
        textView = new TextView(context);
        textView.setId(id);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(gravity);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textView.setTextColor(color);
        textView.setSingleLine(singleLine);
        textView.setEllipsize(ellipsize);
        textView.setText(text);

        return textView;
    }

    /**
     * TextView建造者
     */
    public static final class Builder {
        private Context context;
        private int id;
        private int gravity;
        private boolean singleLine;
        private TextUtils.TruncateAt ellipsize;
        private int color;
        private float size;
        private LinearLayout.LayoutParams layoutParams;
        private CharSequence text;
        private View.OnClickListener listener;

        public Builder() {
        }

        public Builder with(Context val) {
            context = val;
            return this;
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setGravity(int val) {
            gravity = val;
            return this;
        }

        public Builder setSingleLine(boolean val) {
            singleLine = val;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt val) {
            ellipsize = val;
            return this;
        }

        public Builder setTextColor(int val) {
            color = val;
            return this;
        }

        public Builder setTextSize(float val) {
            size = val;
            return this;
        }

        public Builder setLayoutParams(LinearLayout.LayoutParams val) {
            layoutParams = val;
            return this;
        }

        public Builder setText(CharSequence val) {
            text = val;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener val) {
            listener = val;
            return this;
        }

        public TextViewDirector create() {
            return new TextViewDirector(this);
        }
    }


}
