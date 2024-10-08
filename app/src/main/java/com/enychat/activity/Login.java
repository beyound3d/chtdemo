package com.enychat.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.enychat.R;
import com.enychat.constent.Constent;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.et_number)EditText etNumber;
    @BindView(R.id.tv_btn_login)TextView tvBtnLogin;
    String MemberId,MobileNo;
    RequestQueue requestQueue;
    ProgressDialog progDailog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(Login.this);
        ButterKnife.bind(this);
        tvBtnLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view==tvBtnLogin) {
            MobileNo=etNumber.getText().toString().trim();
            if(TextUtils.isEmpty(MobileNo)){
                Context context = getApplicationContext();
                CharSequence text = "Please Enter the Mobile Number...";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else {
                GetOTP(MobileNo);
            }
        }
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
                    SharedPreferences settings = getSharedPreferences("mypref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("MemberID", MemberId);
                    editor.apply();
                    if (Status.equals("Succeed")) {
                        Intent intent = new Intent(getApplicationContext(), OTPVerification.class).putExtra("MobileNo",MobileNo);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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
        progDailog = ProgressDialog.show(Login.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }
}

