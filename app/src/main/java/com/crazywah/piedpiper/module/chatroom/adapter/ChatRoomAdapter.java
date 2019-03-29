package com.crazywah.piedpiper.module.chatroom.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.chatroom.widget.ChatRoomLeftViewHolder;
import com.crazywah.piedpiper.module.chatroom.widget.ChatRoomRightViewHolder;
import com.crazywah.piedpiper.module.chatroom.widget.ChatRoomViewHolder;
import com.crazywah.piedpiper.util.DateUtil;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ChatRoomAdapter";

    public enum TYPE {
        RIGHT,
        LEFT
    }

    private List<IMMessage> messageList;
    private String leftAvatar;
    private User user;

    public ChatRoomAdapter(List<IMMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ChatRoomViewHolder) holder).setView(messageList.get(position), user, isDifferentFromLast(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static final long TIME_GAP = 3 * DateUtil.MIN_LONG;

    private boolean isDifferentFromLast(int position) {
        if (position + 1 < getItemCount()) {
            return Math.abs(messageList.get(position).getTime() - messageList.get(position + 1).getTime()) >= TIME_GAP;
        } else {
            return true;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLeftAvatar() {
        return leftAvatar;
    }

    public void setLeftAvatar(String leftAvatar) {
        this.leftAvatar = leftAvatar;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void notifyMessageStatus(IMMessage message) {
        if (messageList != null && !messageList.isEmpty()) {
            for (IMMessage item : messageList) {
                if (item.getUuid().equals(message.getUuid())) {
                    item.setStatus(message.getStatus());
                    notifyItemChanged(messageList.indexOf(item), "");
                }
            }
        }
    }

    public void notifyReadStatus() {
        if (messageList != null && !messageList.isEmpty()) {
            for (IMMessage item : messageList) {
                if (item.getFromAccount().equals(PiedPiperApplication.getLoginUser().getAccountId())) {
                    notifyItemChanged(messageList.indexOf(item), "");
                }
            }
        }
    }
}
