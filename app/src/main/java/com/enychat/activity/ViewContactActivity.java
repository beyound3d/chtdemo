package com.enychat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ViewContactActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ViewContactActivity.class.getSimpleName();
    Toolbar toolbar;
    @BindView(R.id.tv_custome_notifications) TextView tvCustomeNotifications;
    @BindView(R.id.tv_media_visibility) TextView tvMediaVisibility;
    @BindView(R.id.iv_message_btn) ImageView ivMessageBtn;
    @BindView(R.id.iv_phone_call_btn) ImageView ivPhoneCallBtn;
    @BindView(R.id.iv_video_call_btn) ImageView ivVideoCallBtn;
    @BindView(R.id.tv_block) TextView tvBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        ButterKnife.bind(this);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout =  findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary1));
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, R.color.colorPrimary2));

        tvCustomeNotifications.setOnClickListener(this);
        tvMediaVisibility.setOnClickListener(this);
        ivMessageBtn.setOnClickListener(this);
        ivPhoneCallBtn.setOnClickListener(this);
        ivVideoCallBtn.setOnClickListener(this);
        tvBlock.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                Toast.makeText(getApplicationContext(),"I will soon..",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==tvCustomeNotifications){
            Intent intent=new Intent(getApplicationContext(),NotificationsSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvMediaVisibility){

            DialogMediaVisibility();

        }else if (view==ivMessageBtn){
            Intent intent=new Intent(getApplicationContext(),Chat.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==ivPhoneCallBtn){
            Toast.makeText(getApplicationContext(),"I will soon...",Toast.LENGTH_SHORT).show();

        }else if(view==ivVideoCallBtn){
            Toast.makeText(getApplicationContext(),"I will soon...",Toast.LENGTH_SHORT).show();

        }else if(view==tvBlock){
            Toast.makeText(getApplicationContext(),"I will soon...",Toast.LENGTH_SHORT).show();

        }
    }

    public void DialogMediaVisibility() {
        final Dialog dialog = new Dialog(ViewContactActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_media_visibility);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        final RadioButton rbDefault = dialog.findViewById(R.id.rb_default_yes);
        final RadioButton rbYes = dialog.findViewById(R.id.rb_yes);
        final RadioButton rbNo = dialog.findViewById(R.id.rb_no);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvOk = dialog.findViewById(R.id.tv_ok);
        rbDefault.setChecked(true);
        rbDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbDefault.setChecked(true);
                rbYes.setChecked(false);
                rbNo.setChecked(false);
            }
        });
        rbYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbDefault.setChecked(false);
                rbYes.setChecked(true);
                rbNo.setChecked(false);
            }
        });
        rbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbDefault.setChecked(false);
                rbYes.setChecked(false);
                rbNo.setChecked(true);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

