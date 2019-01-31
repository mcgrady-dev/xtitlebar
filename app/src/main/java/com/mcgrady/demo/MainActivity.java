package com.mcgrady.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mcgrady.xtitlebar.TitleBar;
import com.mcgrady.xtitlebar.TitleBarViewClickAction;
import com.mcgrady.xtitlebar.interf.OnTitleBarListener;

public class MainActivity extends AppCompatActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleBar = findViewById(R.id.title_bar);
        findViewById(R.id.btn_set_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleBar.setTitle("标题标题");
            }
        });

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
