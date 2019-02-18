package com.mcgrady.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mcgrady.demo.utils.BarUtils;
import com.mcgrady.xtitlebar.TitleBar;
import com.mcgrady.xtitlebar.TitleBarClickAction;
import com.mcgrady.xtitlebar.interf.OnTitleBarCustomViewClickListener;
import com.mcgrady.xtitlebar.interf.OnTitleBarListener;

public class MainActivity extends AppCompatActivity {

    private TitleBar titleBar, titleBar2, titleBar6;
    private TextView tvTB6Title, tvTB6SubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BarUtils.setStatusBarAlpha(this, 0);

        titleBar = findViewById(R.id.title_bar);
        titleBar.setOnTitleBarClickListener(new OnTitleBarListener() {
            @Override
            public void onClick(View view, TitleBarClickAction action) {
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


        titleBar2 = findViewById(R.id.title_bar_2);
        titleBar2.setOnTitleBarClickListener(new OnTitleBarListener() {
            @Override
            public void onClick(View view, TitleBarClickAction action) {
                switch (action) {
                    case ACTION_TITLE_VIEW:
                        showToast("title view");
                        break;
                    case ACTION_LEFT_VIEW:
                        showToast("left view");
                        break;
                    case ACTION_RIGHT_VIEW:
                        showToast("right view");
                        break;
                    default:
                        break;
                }
            }
        });

        titleBar6 = findViewById(R.id.title_bar_6);
        tvTB6Title = titleBar6.findTitleChildViewById(R.id.tv_title);
        tvTB6SubTitle = titleBar6.findTitleChildViewById(R.id.tv_sub_title);

        titleBar6.setOnLeftViewsClick(new OnTitleBarCustomViewClickListener() {
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.iv_back:
                        showToast("back");
                        break;
                    case R.id.iv_more:
                        showToast("more");
                        break;
                    default:
                        break;
                }
            }
        }, R.id.iv_back, R.id.iv_more);

        titleBar6.setOnRightViewsClick(new OnTitleBarCustomViewClickListener() {
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                switch (viewId) {
                    case R.id.iv_search:
                        showToast("search");
                        break;
                    case R.id.iv_msg:
                    case R.id.tv_msg_num:
                        showToast("message");
                        break;
                    default:
                        break;
                }
            }
        }, R.id.iv_search, R.id.iv_msg, R.id.tv_msg_num);

    }

    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
