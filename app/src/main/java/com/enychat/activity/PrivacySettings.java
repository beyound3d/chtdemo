package com.enychat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacySettings extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.check_read_reciept) CheckBox checkBox;
    @BindView(R.id.ll_last_seen) LinearLayout llLastSeen;
    @BindView(R.id.ll_profile_photo)LinearLayout llProfilePhoto;
    @BindView(R.id.ll_about)LinearLayout llAbout;
    @BindView(R.id.ll_status)LinearLayout llStatus;
    @BindView(R.id.ll_live_location)LinearLayout llLiveLocation;
    @BindView(R.id.ll_blocked_contact)LinearLayout llBlockedContact;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.privacy);
        llLastSeen.setOnClickListener(this);
        llProfilePhoto.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llStatus.setOnClickListener(this);
        llLiveLocation.setOnClickListener(this);
        llBlockedContact.setOnClickListener(this);
        ivBtnBack.setOnClickListener(this);

    }
    /*public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_last_seen_everyone:
                if(checked)
                    rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(false);
                break;
            case R.id.rb_last_seen_my_contatcs:
                if(checked)
                   rbLastSeenEveryone.setChecked(false);
                rbLastSeenNobody.setChecked(false);
                break;
            case R.id.rb_last_seen_nobody:
                if(checked)
                   rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(false);
                break;
        }
    }*/
    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==llLastSeen){
            DialogLastSeen();

        }else if(view==llProfilePhoto){
            DialogProfilePhoto();

        }else if(view==llAbout){
            DialogAbout();

        }else if(view==llStatus){
            Intent intent=new Intent(getApplicationContext(),StatusPrivacy.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llLiveLocation){
             Intent intent=new Intent(getApplicationContext(),LiveLocation.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llBlockedContact){
            Intent intent=new Intent(getApplicationContext(),BlockContacts.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    public void DialogLastSeen() {
       final Dialog dialog = new Dialog(PrivacySettings.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_last_seen);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final RadioButton rbLastSeenEveryone = dialog.findViewById(R.id.rb_last_seen_everyone);
        final RadioButton rbLastSeenMyContatcs = dialog.findViewById(R.id.rb_last_seen_my_contatcs);
        final RadioButton rbLastSeenNobody = dialog.findViewById(R.id.rb_last_seen_nobody);
         TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        rbLastSeenEveryone.setChecked(true);
        rbLastSeenEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(true);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenMyContatcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(true);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenNobody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(true);
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

    public void DialogProfilePhoto() {
      final Dialog dialog = new Dialog(PrivacySettings.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_profile_photo_privacy_setting);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final RadioButton rbLastSeenEveryone = dialog.findViewById(R.id.rb_last_seen_everyone);
        final RadioButton rbLastSeenMyContatcs= dialog.findViewById(R.id.rb_last_seen_my_contatcs);
        final RadioButton rbLastSeenNobody= dialog.findViewById(R.id.rb_last_seen_nobody);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        rbLastSeenEveryone.setChecked(true);
        rbLastSeenEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(true);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenMyContatcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(true);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenNobody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(true);
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

    public void DialogAbout() {
       final Dialog dialog = new Dialog(PrivacySettings.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_about_privacy_setting);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final RadioButton rbLastSeenEveryone = dialog.findViewById(R.id.rb_last_seen_everyone);
        final RadioButton rbLastSeenMyContatcs = dialog.findViewById(R.id.rb_last_seen_my_contatcs);
        final RadioButton rbLastSeenNobody = dialog.findViewById(R.id.rb_last_seen_nobody);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        rbLastSeenEveryone.setChecked(true);
        rbLastSeenEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(true);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenMyContatcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(true);
                rbLastSeenNobody.setChecked(false);
            }
        });
        rbLastSeenNobody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbLastSeenEveryone.setChecked(false);
                rbLastSeenMyContatcs.setChecked(false);
                rbLastSeenNobody.setChecked(true);
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
