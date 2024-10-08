package com.enychat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnyChatAppWeb extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.iv_add) ImageView ivAdd;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.toolbar_new_broadcast) Toolbar toolbar;
    @BindView(R.id.ll_log_out) LinearLayout llLogOut;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enychat_app_web);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        tvTitle.setText(R.string.enychatapp_web);

        ivBtnBack.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        llLogOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==ivAdd){
            Intent intent=new Intent(getApplicationContext(),ScanQRCode.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llLogOut){
            DialogLogOut();
        }
    }
    public void DialogLogOut() {
        final Dialog dialog = new Dialog(EnyChatAppWeb.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_web_logout_privacy_setting);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
       TextView tvLogout=dialog.findViewById(R.id.tv_log_out);
       TextView tvCancel= dialog.findViewById(R.id.tv_cancel);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    }

