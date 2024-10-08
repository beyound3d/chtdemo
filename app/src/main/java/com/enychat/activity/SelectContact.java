package com.enychat.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enychat.Adapter.UserListAdapter;
import com.enychat.R;
import com.enychat.constent.Constent;
import com.enychat.model.UserObjectModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectContact extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;
    @BindView(R.id.et_search_contact) EditText etSearchContact;
    String MemberId, mMemberId, mName, mNumber, mAboutUs, mPic, Name, Number;
    RequestQueue requestQueue;
    ProgressDialog progDailog;
    private RecyclerView mUserList;;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<UserObjectModel> userList;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        Toolbar toolbar=findViewById(R.id.toolbar_select_contact);
        requestQueue = Volley.newRequestQueue(SelectContact.this);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        tvTitle.setText(R.string.select_contact);
        ivBtnBack.setOnClickListener(this);
        userList=new ArrayList<>();
        initializeReRecyclerView();
        GetMemberList();
    }

    private void initializeReRecyclerView() {
        mUserList=findViewById(R.id.list_enychat_contacts);
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);
        mUserListLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        mUserList.setLayoutManager(mUserListLayoutManager);
        mUserListAdapter=new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.action_select_contact, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.action_invite_a_friend) {
                Toast.makeText(getApplicationContext(), "invite_a_friend", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_contacts) {
                Toast.makeText(getApplicationContext(), "contacts)", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_refresh) {
                Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (id == R.id.action_help) {
                Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public void onClick(View view) {
        if(view==ivBtnBack) {
            finish();
        }
    }

    public void GetMemberList() {
        showpDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constent.GetMemberList+MemberId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        mMemberId = jsonObject1.getString("MemberId");
                        mName = jsonObject1.getString("Name");
                        mAboutUs = jsonObject1.getString("AboutUs");
                        mNumber = jsonObject1.getString("Mobile");
                        mPic = jsonObject1.getString("Pics");
                        UserObjectModel mConatct=new  UserObjectModel(mMemberId,mName,mAboutUs,mNumber,mPic);
                        userList.add(mConatct);
                    }
                    mUserListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(stringRequest);
    }

    public void getContactList() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            Name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            Number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
           // UserObjectModel mConatct=new  UserObjectModel(Name,Number);
           // userList.add(mConatct);
            mUserListAdapter.notifyDataSetChanged();
        }
    }

    private void showpDialog() {
        progDailog = ProgressDialog.show(SelectContact.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }
}

