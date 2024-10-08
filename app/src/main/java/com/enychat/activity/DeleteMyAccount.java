package com.enychat.activity;

import android.content.Intent;
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

public class DeleteMyAccount extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.ll_want_change_number)LinearLayout llWantChangeNumber;
    @BindView(R.id.tv_btn_delete_account) TextView tvBtnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_my_account);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.delete_my_account);
        ivBtnBack.setOnClickListener(this);
        tvBtnDelete.setOnClickListener(this);
        llWantChangeNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==tvBtnDelete){
            Toast.makeText(getApplicationContext(),"I will Soon......",Toast.LENGTH_LONG).show();

        }else if(view==llWantChangeNumber){
            Intent intent=new Intent(getApplicationContext(),ChangeNumber.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
}
