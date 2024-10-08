package com.enychat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwoStepsVerfication extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.tv_enable)TextView tvEnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_step_verification);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.two_steps_verification);
        ivBtnBack.setOnClickListener(this);
        tvEnable.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==tvEnable){
            Toast.makeText(getApplicationContext(),"I will Soon......",Toast.LENGTH_LONG).show();
        }

    }
}
