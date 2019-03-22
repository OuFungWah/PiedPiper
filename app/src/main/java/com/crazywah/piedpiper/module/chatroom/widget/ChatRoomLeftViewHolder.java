package com.crazywah.piedpiper.module.chatroom.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.ImageLoader;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class ChatRoomLeftViewHolder extends RecyclerView.ViewHolder {

    private TextView contentTv;
    private ImageView avatarImg;

    public ChatRoomLeftViewHolder(View itemView) {
        super(itemView);
        contentTv = itemView.findViewById(R.id.chatroom_left_content_tv);
        avatarImg = itemView.findViewById(R.id.chatroom_left_avatar_img);
    }

    public void setView(IMMessage message, final User user) {
        contentTv.setText(message.getContent());
        ImageLoader.loadCircle(user.getAvatar(), avatarImg);
        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.launch(itemView.getContext(), user.getAccountId());
            }
        });
    }

}
