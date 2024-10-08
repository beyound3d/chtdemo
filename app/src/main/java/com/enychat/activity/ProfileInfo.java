package com.enychat.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileInfo extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_profile_name)EditText tvProfileName;
    @BindView(R.id.tv_btn_submit)TextView tvBtnSubmit;
    @BindView(R.id.iv_profile_image) ImageView ivProfileImage;
    String ProfileName,ProfileImage;
    String MemberId;
    ProgressDialog progDailog;
    RequestQueue requestQueue;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap bmp;
    Uri imageUri;
    public static final int GALLEY_REQUEST_CODE_CUSTOMER = 10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ButterKnife.bind(this);
        tvBtnSubmit.setOnClickListener(this);
        ivProfileImage.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        if(view==tvBtnSubmit) {
            ProfileName=tvProfileName.getText().toString();
            if (!ProfileName.equals("")){
                SaveProfile();
            }else {
                Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
            }
        }else if(view==ivProfileImage){
            OpenDialouge();
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
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ProfileInfo.this);
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
                if (takePictureIntent.resolveActivity(ProfileInfo.this.getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        myAlertDialog.show();
    }

    public void SaveProfile() {
        showpDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constent.Signup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    Intent i=new Intent(getApplicationContext(),DashBoard.class);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("MemberId",MemberId);
                    params.put("Name",ProfileName);
                    params.put("Pics",ProfileImage);
                    return params;
                }};
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 60 * 1000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
    }

    private void showpDialog() {
        progDailog = ProgressDialog.show(ProfileInfo.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }

}

