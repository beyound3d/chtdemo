package com.enychat.activity;

import android.content.Intent;
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

public class ChangeNumber extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.tv_next)TextView tvNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);
        ButterKnife.bind(this);

        tvTitle.setText(R.string.change_number);
        ivBtnBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==tvNext){
            Intent intent=new Intent(getApplicationContext(),ChangeMobileNumber.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }
}
