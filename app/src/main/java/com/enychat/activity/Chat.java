package com.enychat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enychat.Adapter.ChatMessageAdapter;
import com.enychat.R;
import com.enychat.constent.Constent;
import com.enychat.model.ChatTextModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
public class Chat extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_profile_name) TextView tvProfileName;
    @BindView(R.id.iv_btn_back) ImageView ivBtnBack;
    @BindView(R.id.iv_profile_image) ImageView ivProfileImage;
    @BindView(R.id.iv_video_call) ImageView ivVideoCall;
    @BindView(R.id.iv_audio_call) ImageView ivAudioCall;
    @BindView(R.id.iv_smily_face) ImageView ivSmilyFace;
    @BindView(R.id.et_text_message) EmojiconEditText etTextMessage;
    @BindView(R.id.iv_attched_file) ImageView ivAttachedFie;
    @BindView(R.id.iv_camera_photo) ImageView ivCameraPhoto;
    @BindView(R.id.iv_voice_record) ImageView ivVoiceRecord;
    @BindView(R.id.iv_send_message)  ImageView ivSendMessage;
    @BindView(R.id.rl_root) RelativeLayout rlRootView;
    private static final int RESULT_FROM_IMAGE_PICKER = 1;
    private Bitmap mPhotoBitmap,mPreviewBitmap;
    private boolean typingStarted = false;
    ProgressDialog progDailog;
    EmojIconActions emojAction;
    String MemberId,mMemberIdTo,TextMessage,msgPostID,msgType;
    RequestQueue requestQueue;
    String mMemberId,mAboutUs,mMobileNo,mPic, mName;
    private RecyclerView mChatList;;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;
    ArrayList<ChatTextModel> chatList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        SharedPreferences settings = getApplication().getSharedPreferences("mypref", MODE_PRIVATE);
        MemberId = settings.getString("MemberID", "");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Intent i = getIntent();
        requestQueue = Volley.newRequestQueue(Chat.this);
        mMemberId = i.getStringExtra("mMemberId");
        mName = i.getStringExtra("mName");
        mAboutUs = i.getStringExtra("mAboutUs");
        mMobileNo = i.getStringExtra("mNumber");
        mPic = i.getStringExtra("mPic");
        tvProfileName.setText(mName);
        chatList=new ArrayList<>();
        ChatMessageList();
        initializeReRecyclerView();

        Picasso.get()
                .load("http://enychat.biz"+mPic)
                .placeholder(R.mipmap.logo_white)
                .into(ivProfileImage);
        emojAction = new EmojIconActions(this, rlRootView, etTextMessage, ivSmilyFace);
        emojAction.ShowEmojIcon();
        emojAction.setIconsIds(R.drawable.ic_action_keyboard,R.drawable.smiley);
        emojAction.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
            }
            @Override
            public void onKeyboardClose() {
            }
        });

        tvProfileName.setOnClickListener(this);
        ivBtnBack.setOnClickListener(this);
        ivProfileImage.setOnClickListener(this);
        ivVideoCall.setOnClickListener(this);
        ivAudioCall.setOnClickListener(this);
        ivAttachedFie.setOnClickListener(this);
        ivCameraPhoto.setOnClickListener(this);
        ivVoiceRecord.setOnClickListener(this);
        ivSendMessage.setOnClickListener(this);

        etTextMessage.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void afterTextChanged(Editable s) {

                boolean hasImage = hasImageSpan(etTextMessage);

                if(!TextUtils.isEmpty(s.toString()) || hasImage) {

                    typingStarted = true;
                    ivSendMessage.setEnabled(true);
                    ivCameraPhoto.setVisibility(View.GONE);
                    ivVoiceRecord.setVisibility(View.GONE);
                    ivSendMessage.setVisibility(View.VISIBLE);

                } else if(TextUtils.isEmpty(s.toString()) && hasImage == false) {

                    if(mPreviewBitmap != null) {
                        mPreviewBitmap.recycle();
                        mPreviewBitmap = null;
                    }
                    if(mPhotoBitmap != null) {
                        mPhotoBitmap.recycle();
                        mPhotoBitmap = null;
                    }
                    ivSendMessage.setEnabled(false);
                    if(typingStarted) {
                        typingStarted = false;
                        ivCameraPhoto.setVisibility(View.VISIBLE);
                        ivVoiceRecord.setVisibility(View.VISIBLE);
                        ivSendMessage.setVisibility(View.GONE);
                        }
                    }
                }
        });
    }
    private void initializeReRecyclerView() {
        mChatList=findViewById(R.id.list_contacts);
        mChatList.setNestedScrollingEnabled(false);
        mChatList.setHasFixedSize(false);
        mChatListLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        mChatList.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter=new ChatMessageAdapter(this,chatList,MemberId);
        mChatList.setAdapter(mChatListAdapter);
    }

    public boolean hasImageSpan(EditText editText) {
        Editable text  = editText.getEditableText();
        ImageSpan[] spans = text.getSpans(0, text.length(), ImageSpan.class);
        return !(spans.length == 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_view_contact) {
           Intent intent=new Intent(getApplicationContext(),ViewContactActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
           return true;
        }
        if (id == R.id.action_media) {
            Intent intent=new Intent(getApplicationContext(),MediaChat.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_mute_notifications) {
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_block) {
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_clear_chat) {
            Toast.makeText(getApplicationContext(),"I will Soon...",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view==tvProfileName){
            Intent intent=new Intent(getApplicationContext(),ViewContactActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==ivProfileImage){
            Toast.makeText(getApplicationContext(),"I will Soon....",Toast.LENGTH_SHORT).show();

        }else if(view==ivBtnBack){
            finish();

        }else if(view==ivVideoCall){
            Toast.makeText(getApplicationContext(),"I will Soon....",Toast.LENGTH_SHORT).show();

        }else if(view==ivAudioCall){
            Toast.makeText(getApplicationContext(),"I will Soon....",Toast.LENGTH_SHORT).show();

        }else if(view==ivAttachedFie){
            Toast.makeText(getApplicationContext(),"I will Soon....",Toast.LENGTH_SHORT).show();

        }else if(view==ivCameraPhoto){
            launchPhotoPicker();
        }else if(view==ivVoiceRecord){
            Toast.makeText(getApplicationContext(),"I will Soon....",Toast.LENGTH_SHORT).show();

        }else if(view==ivSendMessage){
            SendMessage();
            etTextMessage.getText().clear();
        }
    }

    private void launchPhotoPicker() {
        etTextMessage.setText("");
        ivSendMessage.setEnabled(false);
        if(mPreviewBitmap != null) {
            mPreviewBitmap.recycle();
            mPreviewBitmap = null;
        }
        if(mPhotoBitmap != null) {
            mPhotoBitmap.recycle();
            mPhotoBitmap = null;
        }
        Intent intent =  new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_FROM_IMAGE_PICKER);
    }

    public void SendMessage() {
        final String Text=etTextMessage.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constent.PostInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Status=jsonObject.getString("status");
                    String Message=jsonObject.getString("Message");
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String MemberIdFrom = jsonObject1.getString("MemberIdFrom");
                        String MemberIdTo = jsonObject1.getString("MemberIdTo");
                        String PostID = jsonObject1.getString("PostID");
                        String Type = jsonObject1.getString("Type");
                        if (Status.equals("Succeed")) {
                            ChatMessageList();
                            } else {
                            Toast.makeText(getApplication(), Message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MemberIdFrom", MemberId);
                params.put("MemberIdTo", mMemberId);
                params.put("Type", "0");
                params.put("Text",Text);
                params.put("Media","");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void ChatMessageList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Constent.GetAllPostByMeberID+MemberId+"&MemberIDTo="+mMemberId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        MemberId = jsonObject1.getString("MemberIdFrom");
                        mMemberIdTo = jsonObject1.getString("MemberIdTo");
                        TextMessage = jsonObject1.getString("Text");
                        msgPostID = jsonObject1.getString("PostID");
                        msgType=jsonObject1.getString("Type");
                        ChatTextModel mConatct=new  ChatTextModel(MemberId,mMemberIdTo,TextMessage,msgPostID,msgType);
                        chatList.add(mConatct);
                    }
                    mChatListAdapter.notifyDataSetChanged();
                    mChatList.scrollToPosition(mChatListAdapter.getItemCount() - 1);
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

    private void showpDialog() {
        progDailog = ProgressDialog.show(Chat.this, null,"Please wait.. ", true);
        progDailog.setCancelable(false);
    }

    private void hidepDialog() {
        if (progDailog.isShowing())
            progDailog.dismiss();
    }
}
