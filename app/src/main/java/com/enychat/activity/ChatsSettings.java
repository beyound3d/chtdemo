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

public class ChatsSettings extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.ll_app_language) LinearLayout llAppLanguage;
    @BindView(R.id.tv_wallpaper) TextView tvWallpaper;
    @BindView(R.id.tv_chat_backup) TextView tvChatBackup;
    @BindView(R.id.tv_chat_history) TextView tvChatHistory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_settings);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.chats);
        ivBtnBack.setOnClickListener(this);
        llAppLanguage.setOnClickListener(this);
        tvWallpaper.setOnClickListener(this);
        tvChatBackup.setOnClickListener(this);
        tvChatHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==llAppLanguage){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvWallpaper){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvChatBackup){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvChatHistory) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();
        }



    }
}
