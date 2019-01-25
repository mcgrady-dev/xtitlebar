package com.mcgrady.xtitlebar;

import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/25
 */

public class TestTextView {

    private Context context;
    private int id;
    private int gravity;
    private boolean singleLine;
    private TextUtils.TruncateAt ellipsize;
    private int color;
    private float size;
    private LinearLayout.LayoutParams layoutParams;

    private TestTextView(Builder builder) {
        context = builder.context;
        id = builder.id;
        gravity = builder.gravity;
        singleLine = builder.singleLine;
        ellipsize = builder.ellipsize;
        color = builder.color;
        size = builder.size;
        layoutParams = builder.layoutParams;
    }


    public static final class Builder {
        private Context context;
        private int id;
        private int gravity;
        private boolean singleLine;
        private TextUtils.TruncateAt ellipsize;
        private int color;
        private float size;
        private LinearLayout.LayoutParams layoutParams;

        public Builder() {
        }

        public Builder context(Context val) {
            context = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder gravity(int val) {
            gravity = val;
            return this;
        }

        public Builder singleLine(boolean val) {
            singleLine = val;
            return this;
        }

        public Builder ellipsize(TextUtils.TruncateAt val) {
            ellipsize = val;
            return this;
        }

        public Builder color(int val) {
            color = val;
            return this;
        }

        public Builder size(float val) {
            size = val;
            return this;
        }

        public Builder layoutParams(LinearLayout.LayoutParams val) {
            layoutParams = val;
            return this;
        }

        public TextView build() {
            return new TextView(context);
        }
    }
}
