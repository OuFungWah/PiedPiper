package com.crazywah.piedpiper.chatroom.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class ChatRoomRightViewHolder extends RecyclerView.ViewHolder {

    private TextView contentTv;
    private ImageView avatarImg;

    public ChatRoomRightViewHolder(View itemView) {
        super(itemView);
        contentTv = itemView.findViewById(R.id.chatroom_right_content_tv);
        avatarImg = itemView.findViewById(R.id.chatroom_right_avatar_img);
    }

    public void setView(IMMessage message){
        contentTv.setText(message.getContent());
    }

}
