package com.crazywah.piedpiper.chatroom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.chatroom.widget.ChatRoomLeftViewHolder;
import com.crazywah.piedpiper.chatroom.widget.ChatRoomRightViewHolder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ChatRoomAdapter";

    enum TYPE {
        RIGHT,
        LEFT
    }

    private List<IMMessage> messageList;

    public ChatRoomAdapter(List<IMMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE.RIGHT.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_chatroom_right, parent, false);
            return new ChatRoomRightViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_chatroom_left, parent, false);
            return new ChatRoomLeftViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChatRoomRightViewHolder){
            ((ChatRoomRightViewHolder)holder).setView(messageList.get(position));
        }else if(holder instanceof ChatRoomLeftViewHolder){
            ((ChatRoomLeftViewHolder)holder).setView(messageList.get(position));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getFromAccount().equals(PiedPiperApplication.getLoginUser().getAccountId())) {
            return TYPE.RIGHT.ordinal();
        } else {
            return TYPE.LEFT.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
