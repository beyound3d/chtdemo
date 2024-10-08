package com.enychat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.enychat.R;
import com.enychat.activity.Chat;
import com.enychat.activity.SelectContact;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChats extends Fragment implements View.OnClickListener{
    @BindView(R.id.ll_chat_start)
    LinearLayout llChatStart;
    @BindView(R.id.fab_chats) FloatingActionButton FabChats;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.fragment_chats,container,false);
        ButterKnife.bind(this, view);
        llChatStart.setOnClickListener(this);
        FabChats.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view==llChatStart){
            Intent intent=new Intent(getContext(),Chat.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(view==FabChats){
            Intent intent=new Intent(getContext(),SelectContact.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }
}
