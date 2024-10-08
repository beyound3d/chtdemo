package com.enychat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enychat.R;
import com.enychat.model.ChatTextModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.UserListViewHolder> {
    private ArrayList<ChatTextModel> chatList;
    private String loggedInUserId;
    private Context context;
    public ChatMessageAdapter(Context context,ArrayList<ChatTextModel> chatList,String loggedInUserId){
        this.chatList=chatList;
        this.loggedInUserId = loggedInUserId;
        this.context = context;
    }
    public void add(ChatTextModel message) {
        this.chatList.add(message);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_out, null, false);
        return new UserListViewHolder(layoutView);
        }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ChatTextModel model = chatList.get(position);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.msgLayout.getLayoutParams();

        if(isMyMessage(model.getMemberId())){
            //show msg in right side
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.msgLayout.setBackground(context.getResources().getDrawable(R.drawable.message_out));
        }else{
            //show msg in left side
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.msgLayout.setBackground(context.getResources().getDrawable(R.drawable.message_in));
        }
        holder.TextMessageOut.setText(chatList.get(position).getTextMessage());
        String Time = new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());
        holder.tvMessageOutTime.setText(Time);

    }
    @Override
    public int getItemCount() {
        return chatList.size(); }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
         TextView TextMessageOut,tvMessageOutTime,TextMessageIn,tvMessageInTime;
         private LinearLayout msgLayout;
         UserListViewHolder(View view){
              super(view);
             TextMessageOut=view.findViewById(R.id.tv_message_out);
             tvMessageOutTime=view.findViewById(R.id.tv_message_out_time);
             TextMessageIn=view.findViewById(R.id.tv_message_in);
             tvMessageInTime=view.findViewById(R.id.tv_message_in_time);
             msgLayout = view.findViewById(R.id.myMsgLayout);
        }
    }


    private boolean isMyMessage(String fromId){
        return fromId.equals(loggedInUserId);
    }
}