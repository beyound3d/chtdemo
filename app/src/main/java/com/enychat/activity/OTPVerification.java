package com.enychat.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enychat.constent.Constent;
import com.enychat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OTPVerification extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.et_otp_code)
    EditText etOTPCode;
    @BindView(R.id.tv_btn_verify)
    TextView tvBtnVerify;
    @BindView(R.id.tv_btn_resend_otp)
    TextView tvBtnResendOtp;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String MemberId, OTP, MobileNo;
    ProgressDialog progDailog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        checkAndRequestPermissions();
        ButterKnife.bind(this);
        Intent intent = getIntent();
        MobileNo = intent.getStringExtra("MobileNo");
        requestQueue = Volley.newRequestQueue(OTPVerification.this);
        tvBtnVerify.setOnClickListener(this);
        tvBtnResendOtp.setOnClickListener(this);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                String result = message.replaceAll("[-+.^:,]", "");
                String str = result.replaceAll("[^\\d.]", "");
                etOTPCode.setText(str);
            }
        }
    };

    private void checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        if (view == tvBtnVerify) {
            OTP = etOTPCode.getText().toString().trim();
            Pattern patternOTP = Pattern.compile("[0-9]{6}");
            Matcher matcherOTP = patternOTP.matcher(OTP);
            if (TextUtils.isEmpty(OTP)) {
                Context context = getApplicationContext();
                CharSequence text = "Please Enter the 6 Digit OTP.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (!matcherOTP.matches()) {
                Context context = getApplicationContext();
                CharSequence text = "Please Enter the 6 Digit OTP.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                VerifyMessage(MemberId, OTP);
            }

        } else if (view == tvBtnResendOtp) {
                GetOTP(MobileNo);
            }
    }

    public void VerifyMessage(String memberId, String otpcode) {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constent.VerifyOTP+ memberId + "&OTPCode=" + otpcode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String Message = jsonObject.getString("Message");
                    String MemberID = jsonObject.getString("MemberID");
                    if (status.equals("Succeed")) {
                        Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplication(), Message, Toast.LENGTH_SHORT).show();
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

    public void GetOTP(String mobileNo) {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constent.GetOTP+mobileNo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Status = jsonObject.getString("status");
                    String Message = jsonObject.getString("Message");
                    MemberId=jsonObject.getString("MemberID");
                    if (Status.equals("Succeed")) {
                        Toast.makeText(getApplication(), Status, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), Message, Toast.LENGTH_SHORT).show();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 60 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void showpDialog() {
        progDailog = ProgressDialog.show(OTPVerification.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }
}


