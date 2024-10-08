package com.enychat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountSettings extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_privacy) TextView tvPrivacy;
    @BindView(R.id.tv_security) TextView tvSecurity;
    @BindView(R.id.tv_two_steps_verification) TextView tvTwoStepsVerification;
    @BindView(R.id.tv_change_number) TextView tvChangeNumber;
    @BindView(R.id.tv_request_account_info) TextView tvRequestAccountInfo;
    @BindView(R.id.tv_delete_my_account) TextView tvDeleteMyAccount;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.account_settings);
        tvPrivacy.setOnClickListener(this);
        tvSecurity.setOnClickListener(this);
        tvTwoStepsVerification.setOnClickListener(this);
        tvChangeNumber.setOnClickListener(this);
        tvRequestAccountInfo.setOnClickListener(this);
        tvDeleteMyAccount.setOnClickListener(this);
        ivBtnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==ivBtnBack){
            finish();

        }else if(view==tvPrivacy){
            Intent intent=new Intent(getApplicationContext(),PrivacySettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvSecurity){
            Intent intent=new Intent(getApplicationContext(),SecuritySetting.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvTwoStepsVerification){
            Intent intent=new Intent(getApplicationContext(),TwoStepsVerfication.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvChangeNumber){
            Intent intent=new Intent(getApplicationContext(),ChangeNumber.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvRequestAccountInfo){
            Intent intent=new Intent(getApplicationContext(),RequestAccountInfo.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvDeleteMyAccount){
            Intent intent=new Intent(getApplicationContext(),DeleteMyAccount.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }
}
