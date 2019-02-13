package com.mcgrady.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcgrady.demo.utils.BarUtils;
import com.mcgrady.xtitlebar.TitleBar;
import com.mcgrady.xtitlebar.TitleBarViewClickAction;
import com.mcgrady.xtitlebar.interf.OnTitleBarListener;

public class MainActivity extends AppCompatActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarUtils.setStatusBarAlpha(this, 0);

        titleBar = findViewById(R.id.title_bar);

        titleBar.setTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onClick(View view, TitleBarViewClickAction action) {
                switch (action) {
                    case ACTION_LEFT_VIEW:
                        break;
                    case ACTION_RIGHT_VIEW:
                        break;
                    case ACTION_TITLE_VIEW:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
