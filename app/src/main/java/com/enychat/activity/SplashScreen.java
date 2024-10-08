package com.enychat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.enychat.R;



public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    String MemberId;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences settings = getSharedPreferences("mypref", MODE_PRIVATE);
        edit = settings.edit();
        MemberId=settings.getString("MemberID","");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MemberId.equals("")){
                    Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashScreen.this,DashBoard.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                    }
                }
        }, SPLASH_TIME_OUT);
    }

}