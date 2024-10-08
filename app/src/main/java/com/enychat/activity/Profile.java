package com.enychat.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.iv_profile_image) ImageView ivProfileImage;
    @BindView(R.id.iv_btn_camera) ImageView ivBtnCamera;
    @BindView(R.id.tv_profile_name) EditText tvProfileName;
    @BindView(R.id.tv_number) TextView tvMobileNo;
    @BindView(R.id.tv_btn_update) TextView tvUpdate;
    @BindView(R.id.tv_aboutus) TextView tvAboutUs;
    @BindView(R.id.tv_select_aboutus) TextView tvSelectAboutus;
    String Pics,MemberId,Name,AboutUs,MobileNo,ProfileName,Str,AboutUsUpdate,ProfileImage;
    RequestQueue requestQueue;
    ProgressDialog progDailog;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bmp;
    Uri imageUri;
    public static final int GALLEY_REQUEST_CODE_CUSTOMER = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        MobileNo = settings.getString("Mobile", "");
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(Profile.this);
        tvTitle.setText(R.string.profile);
        tvAboutUs.setText(Str);
        ivBtnBack.setOnClickListener(this);
        ivBtnCamera.setOnClickListener(this);
        tvMobileNo.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        tvSelectAboutus.setOnClickListener(this);
        getMemberProfile();
    }

    @Override
    public void onClick(View view) {
        if (view == ivBtnBack) {
            Intent intent=new Intent(getApplicationContext(),DashBoard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if (view == ivBtnCamera) {
            OpenDialouge();

        }else if(view==tvMobileNo){
            Intent intent=new Intent(getApplicationContext(),ChangeNumber.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==tvUpdate){
            ProfileName=tvProfileName.getText().toString();
            AboutUsUpdate=tvAboutUs.getText().toString();
            if (ProfileName.equals("")){
                Toast.makeText(getApplicationContext(),"Please enter the Profile name",Toast.LENGTH_SHORT).show();
            }else {
                UpdateProfile();
            }

        }else if(view==tvSelectAboutus){
            DialogProfileAboutUs();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            ivProfileImage.setImageBitmap(bmp);
            ProfileImage= Base64.encodeToString(byteArray,Base64.DEFAULT);

        }else if (resultCode == RESULT_OK && requestCode == GALLEY_REQUEST_CODE_CUSTOMER)
        {
            if (data.getData() != null) {
                try {
                    imageUri = data.getData();
                    InputStream imageStream = getApplication().getContentResolver().openInputStream(imageUri);
                    bmp = BitmapFactory.decodeStream(imageStream);
                    bmp = getResizedBitmap(bmp, 400);
                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG,100, baos);
                    byte [] b2=baos.toByteArray();
                    ivProfileImage.setImageBitmap(bmp);
                    ProfileImage= Base64.encodeToString(b2,Base64.DEFAULT);
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            } else {
                Toast.makeText(getApplicationContext(), "plaese select another image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void OpenDialouge(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(Profile.this);
        myAlertDialog.setTitle("Uploade Your Pictures");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(openGallery, "Open Gallery"), GALLEY_REQUEST_CODE_CUSTOMER);

            }
        });
        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(Profile.this.getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        myAlertDialog.show();
    }

    public void UpdateProfile() {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constent.updateProfile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Status = jsonObject.getString("status");
                    String Message = jsonObject.getString("Message");
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MemberId = jsonObject.getString("MemberId");
                        Name = jsonObject.getString("Name");

                        if (Status.equals("Succeed")) {
                            Toast.makeText(getApplication(), Message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), Message, Toast.LENGTH_SHORT).show();
                        }
                        tvProfileName.setText(Name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MemberId", MemberId);
                params.put("Name", ProfileName);
                params.put("AboutUs", AboutUsUpdate);
                params.put("Pics", ProfileImage);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void getMemberProfile() {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constent.GetMemberData+MemberId, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
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
                        tvMobileNo.setText("+91 " + MobileNo);
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

    private void showpDialog() {
        progDailog = ProgressDialog.show(Profile.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }

    public void DialogProfileAboutUs() {
        final Dialog dialog = new Dialog(Profile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_aboutus);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        ImageView ivBtnBack = dialog.findViewById(R.id.iv_btn_back);
        final TextView Available=dialog.findViewById(R.id.tv_available);
        final TextView Busy=dialog.findViewById(R.id.tv_busy);
        final TextView AtSchool=dialog.findViewById(R.id.tv_at_school);
        final TextView AtTheMovies=dialog.findViewById(R.id.tv_at_the_movies);
        final TextView AtWork=dialog.findViewById(R.id.tv_at_work);
        final TextView BatteryAboutToDie=dialog.findViewById(R.id.tv_battery_about_to_die);
        final TextView CanNotEnychatOnly=dialog.findViewById(R.id.tv_can_not_enychat_only);
        final TextView InAMeeting=dialog.findViewById(R.id.tv_in_a_meeting);
        final TextView AtTheGym=dialog.findViewById(R.id.tv_at_the_gym);
        final TextView Sleeping=dialog.findViewById(R.id.tv_sleeping);
        final TextView UrgentCallsOnly=dialog.findViewById(R.id.tv_urgent_calls_only);

        Available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=Available.getText().toString();
            tvAboutUs.setText(Str);
            dialog.dismiss();}});

        Busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=Busy.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}});

        AtSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=AtSchool.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}});

        AtTheMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=AtTheMovies.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}});

        AtWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=AtWork.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}});

        BatteryAboutToDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=BatteryAboutToDie.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}
        });

        CanNotEnychatOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=CanNotEnychatOnly.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss();}

        });

        InAMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=InAMeeting.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss(); }});

        AtTheGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=AtTheGym.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss(); }});

        Sleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=Sleeping.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss(); }});

        UrgentCallsOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Str=UrgentCallsOnly.getText().toString();
                tvAboutUs.setText(Str);
                dialog.dismiss(); }});

        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}