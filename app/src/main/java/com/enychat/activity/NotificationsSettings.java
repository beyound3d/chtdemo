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

public class NotificationsSettings extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_notification_message) TextView tvNotificationMessage;
    @BindView(R.id.tv_vibrate_message) TextView tvVibrateMessage;
    @BindView(R.id.tv_pop_notifications_message) TextView tvPopNotificationsMessage;
    @BindView(R.id.tv_light_message) TextView tvLightMessage;
    @BindView(R.id.tv_notification_tone_group) TextView tvNotificationToneGroup;
    @BindView(R.id.tv_vibrate_group) TextView tvVibrateGroup;
    @BindView(R.id.tv_pop_notifications_group) TextView tvpopNotificationsGroup;
    @BindView(R.id.tv_light_group) TextView tvLightGroup;
    @BindView(R.id.tv_ringtone_call) TextView tvRingtoneCall;
    @BindView(R.id.tv_vibrate_call) TextView tvVibrateCall;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_settings);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.notifications);
        ivBtnBack.setOnClickListener(this);
        tvNotificationMessage.setOnClickListener(this);
        tvVibrateMessage.setOnClickListener(this);
        tvPopNotificationsMessage.setOnClickListener(this);
        tvLightMessage.setOnClickListener(this);

        tvNotificationToneGroup.setOnClickListener(this);
        tvVibrateGroup.setOnClickListener(this);
        tvpopNotificationsGroup.setOnClickListener(this);
        tvLightGroup.setOnClickListener(this);
        tvRingtoneCall.setOnClickListener(this);
        tvVibrateCall.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view==ivBtnBack) {
            finish();

       }else if(view==tvNotificationMessage){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvVibrateMessage){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvPopNotificationsMessage){
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvLightMessage) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvNotificationToneGroup) {
        Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvVibrateGroup) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvpopNotificationsGroup) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvLightGroup) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvRingtoneCall) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();

        }else if(view==tvVibrateCall) {
            Toast.makeText(getApplicationContext(), "I will Soon...", Toast.LENGTH_SHORT).show();
        }

    }
}
