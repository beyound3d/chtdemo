package com.enychat.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Help extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.tv_faq) TextView tvFaq;
    @BindView(R.id.tv_contact_us) TextView tvContactus;
    @BindView(R.id.tv_terms_and_privacy_policy) TextView tvTermsPrivacyPolicy;
    @BindView(R.id.tv_app_info) TextView tvAppInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        Toolbar toolbar=findViewById(R.id.toolbar_select_contact);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.help);
        ivBtnBack.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        tvContactus.setOnClickListener(this);
        tvTermsPrivacyPolicy.setOnClickListener(this);
        tvAppInfo.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        if (view == ivBtnBack) {
            finish();
        }else if(view==tvFaq){
            Uri uri = Uri.parse("https://enychat.in/FAQ.aspx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(view==tvContactus){
            Intent intent=new Intent(getApplicationContext(),HelpContactus.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvTermsPrivacyPolicy){

        }else if(view==tvAppInfo){

    }
    }
}

