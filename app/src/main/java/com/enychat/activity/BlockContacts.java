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

public class BlockContacts extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.iv_add_block) ImageView ivAddBlock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_contacts);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.blocked_contacts);

        ivBtnBack.setOnClickListener(this);
        ivAddBlock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==ivAddBlock){
            Intent intent=new Intent(getApplicationContext(),SelectContactBlock.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
}
