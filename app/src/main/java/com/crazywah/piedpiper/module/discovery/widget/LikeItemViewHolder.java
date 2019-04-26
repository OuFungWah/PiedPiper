package com.crazywah.piedpiper.module.discovery.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.bean.Like;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.ImageLoader;

public class LikeItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView avatarImg;

    public LikeItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_like_avatar, parent, false));
        avatarImg = itemView.findViewById(R.id.like_avatar_img);
    }

    public void setView(final Like like) {
        ImageLoader.loadCircle(like.getAvatar(), avatarImg, R.drawable.users);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.launch(itemView.getContext(), like.getFromId());
            }
        });
    }

}
