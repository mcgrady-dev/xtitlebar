package com.mcgrady.xtitlebar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by mcgrady on 2020/9/29.
 */
final class ViewCore {

    static View newView(Context context) {
        View view = new View(context);
        view.setId(generateViewId());
        return view;
    }
    
    static TextView newTextView(Context context) {
        TextView leftView = new TextView(context);
        leftView.setId(generateViewId());
        leftView.setClickable(true);
        leftView.setSingleLine(true);
        return leftView;
    }

    static ImageButton newImageButton(Context context) {
        ImageButton imageButton = new ImageButton(context);
        imageButton.setId(generateViewId());
        imageButton.setClickable(true);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        return imageButton;
    }

    static ImageView newImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setId(generateViewId());

        return imageView;
    }

    static RelativeLayout newRelativeLayout(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setId(generateViewId());
        return relativeLayout;
    }

    static LinearLayout newMainCenterViews(Context context) {
        LinearLayout linearMainCenter = new LinearLayout(context);
        linearMainCenter.setId(generateViewId());
        linearMainCenter.setGravity(Gravity.CENTER);
        linearMainCenter.setOrientation(LinearLayout.VERTICAL);
        return linearMainCenter;
    }

    static void setBackground(View view, Drawable background) {
        if (background == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * 计算View Id
     *
     * @return
     */
    static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }
}
