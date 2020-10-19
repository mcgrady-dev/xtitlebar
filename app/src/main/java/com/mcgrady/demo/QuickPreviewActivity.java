package com.mcgrady.demo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.mcgrady.xtitlebar.TitleBar;

public class QuickPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_preview);

        ImmersionBar.with(this)
                .titleBar(R.id.titlebar)
                .statusBarDarkFont(true)
                .init();

        ((TitleBar) findViewById(R.id.titlebar)).setListener(new TitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
        ((TitleBar) findViewById(R.id.titlebar_3)).showCenterProgress();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        KeyboardConflictCompat.assistWindow(getWindow());
    }
}
