package com.enychat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enychat.R;
import com.enychat.constent.Constent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.toolbar_settings)Toolbar toolbar;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.ll_account_settings) LinearLayout llAccountSettings;
    @BindView(R.id.ll_chats_setting) LinearLayout llChatsSettings;
    @BindView(R.id.ll_notification_settings) LinearLayout llNotificationsSettings;
    @BindView(R.id.ll_data_storage_usage_setting) LinearLayout llDataStorageUsageSettings;
    @BindView(R.id.ll_invite_friend_setting) LinearLayout llInviteFriendsSettings;
    @BindView(R.id.ll_help_setting) LinearLayout llHelpSettings;
    @BindView(R.id.ll_profile_photo) LinearLayout llProfilePhoto;
    @BindView(R.id.iv_profile_image) ImageView ivProfileImage;
    @BindView(R.id.tv_profile_name) TextView tvProfileName;
    @BindView(R.id.tv_about_us) TextView tvAboutUs;
    String Name,AboutUs,MobileNo,Pics,MemberId;
    RequestQueue requestQueue;
    ProgressDialog progDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        tvTitle.setText(R.string.setting);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(Settings.this);
        getMemberProfile();
        llAccountSettings.setOnClickListener(this);
        llChatsSettings.setOnClickListener(this);
        llNotificationsSettings.setOnClickListener(this);
        llDataStorageUsageSettings.setOnClickListener(this);
        llInviteFriendsSettings.setOnClickListener(this);
        llHelpSettings.setOnClickListener(this);
        ivBtnBack.setOnClickListener(this);
        llProfilePhoto.setOnClickListener(this);
    }
    public void getMemberProfile() {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constent.GetMemberData+MemberId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Name = jsonObject1.getString("Name");
                        AboutUs = jsonObject1.getString("AboutUs");
                        MobileNo = jsonObject1.getString("Mobile");
                        Pics = jsonObject1.getString("Pics");
                        SharedPreferences settings = getSharedPreferences("mypref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("Mobile", MobileNo);
                        editor.apply();
                        Picasso.get()
                                .load("http://enychat.biz"+Pics)
                                .placeholder(R.drawable.ic_launcher_logo)
                                .into(ivProfileImage);
                        tvProfileName.setText(Name);
                        tvAboutUs.setText(AboutUs);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if (view==ivBtnBack){
            finish();

        }else if(view==llAccountSettings){
            Intent intent=new Intent(getApplicationContext(),AccountSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llChatsSettings){
            Intent intent=new Intent(getApplicationContext(),ChatsSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llNotificationsSettings){
            Intent intent=new Intent(getApplicationContext(),NotificationsSettings.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llDataStorageUsageSettings){
            Toast.makeText(getApplicationContext(),"I will soon....",Toast.LENGTH_SHORT).show();

        }else if(view==llInviteFriendsSettings){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("dialog_text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"https:enychat.in");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }else if(view==llHelpSettings){
            Intent intent=new Intent(getApplicationContext(),Help.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==llProfilePhoto){
            Intent intent=new Intent(getApplicationContext(),Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void showpDialog() {
        progDailog = ProgressDialog.show(Settings.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }
}
