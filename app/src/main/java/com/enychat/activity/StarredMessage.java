package com.enychat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarredMessage extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.toolbar_starred_message)Toolbar toolbar;
    @BindView(R.id.tv_title)TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred_message);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        tvTitle.setText(R.string.starred_message);
        ivBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();
        }
    }
}
