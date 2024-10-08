package com.enychat.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestAccountInfo extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.tv_learn_more)TextView tvLearnMore;
    @BindView(R.id.ll_request_report) LinearLayout llRequestReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_account_info);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.request_account_info);
        ivBtnBack.setOnClickListener(this);
        llRequestReport.setOnClickListener(this);
        tvLearnMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==llRequestReport){
            Toast.makeText(getApplicationContext(),"I will Soon......",Toast.LENGTH_LONG).show();

        }else if(view==tvLearnMore){
            Toast.makeText(getApplicationContext(),"I will Soon......",Toast.LENGTH_LONG).show();
        }

    }
}
