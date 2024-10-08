package com.enychat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enychat.R;
import com.enychat.activity.Chat;
import com.enychat.model.UserObjectModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    private ArrayList<UserObjectModel> userList;
    Context mContext;
    public UserListAdapter(ArrayList<UserObjectModel> userList){
        this.userList=userList;
    }
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View layoutView=LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_member_contact_list,null,false);
        final UserListViewHolder rcv= new UserListViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mName.setText(userList.get(position).getmName());
        holder.mPhone.setText(userList.get(position).getmNumber());
        String Pics=userList.get(position).getmPic();
        Picasso.get()
                .load("http://enychat.biz"+Pics)
                .placeholder(R.drawable.ic_launcher_logo)
                .into (holder.ivProfileImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Chat.class);
                intent.putExtra("mMemberId",userList.get(position).getmMemberId());
                intent.putExtra("mName",userList.get(position).getmName());
                intent.putExtra("mAboutUs",userList.get(position).getmAboutUs());
                intent.putExtra("mNumber",userList.get(position).getmNumber());
                intent.putExtra("mPic",userList.get(position).getmPic());
                view.getContext().startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return userList.size(); }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
         TextView mName,mPhone;
         ImageView ivProfileImage;
         UserListViewHolder(View view){
              super(view);
            mName=view.findViewById(R.id.tv_name);
            mPhone=view.findViewById(R.id.tv_number);
            ivProfileImage=view.findViewById(R.id.iv_profile_image);
        }
    }
}