package com.crazywah.piedpiper.module.chatroom.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.ImageLoader;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class ChatRoomRightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView contentTv;
    private ImageView avatarImg;

    public ChatRoomRightViewHolder(View itemView) {
        super(itemView);
        contentTv = itemView.findViewById(R.id.chatroom_right_content_tv);
        avatarImg = itemView.findViewById(R.id.chatroom_right_avatar_img);
    }

    public void setView(IMMessage message) {
        contentTv.setText(message.getContent());
        ImageLoader.loadCircle(PiedPiperApplication.getLoginUser().getAvatar(), avatarImg);
        avatarImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatroom_right_avatar_img:
                UserInfoActivity.launch(itemView.getContext(), PiedPiperApplication.getLoginUser().getAccountId());
                break;
            default:
                break;
        }
    }
}
