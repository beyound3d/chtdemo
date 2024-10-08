package com.enychat.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusPrivacy extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.rb_contacts)RadioButton rbContacts;
    @BindView(R.id.rb_my_contact_except)RadioButton rbMyContactExcept;
    @BindView(R.id.rb_only_share_with)RadioButton rbOnlyShareWith;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_privacy);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.status_privacy);
        rbContacts.setChecked(true);


        ivBtnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==ivBtnBack){
            finish();
        }
    }
    public void onRadioButtonClicked(View v) {
        boolean  checked = ((RadioButton) v).isChecked();
        switch(v.getId()){
            case R.id.rb_contacts:
                if(checked)
                    Toast.makeText(getApplicationContext(),"Contacts Successfully Done.......",Toast.LENGTH_LONG).show();
                   break;
            case R.id.rb_my_contact_except:
                if(checked) {
                    Intent intent=new Intent(getApplicationContext(),HideStatusFrom.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                   break;
            case R.id.rb_only_share_with:
                if(checked){
                    Intent intent=new Intent(getApplicationContext(),ShareStatusWith.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                   break;
        }
    }
}
