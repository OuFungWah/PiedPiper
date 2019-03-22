package com.crazywah.piedpiper.module.chat.bean;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.chatroom.activity.ChatRoomActivity;
import com.crazywah.piedpiper.util.DensityUtils;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.MessageUtil;
import com.crazywah.piedpiper.widget.UnReadView;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecentContactViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "RecentContactViewHolder";

    private ImageView avatarImg;
    private TextView nameTv;
    private TextView contentTv;
    private UnReadView unReadView;
    private TextView timeTv;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public RecentContactViewHolder(View itemView) {
        super(itemView);
        avatarImg = itemView.findViewById(R.id.chat_recent_contact_avatar_img);
        nameTv = itemView.findViewById(R.id.chat_recent_contact_name_tv);
        contentTv = itemView.findViewById(R.id.chat_recent_contact_latest_message_tv);
        unReadView = itemView.findViewById(R.id.chat_recent_contact_unread);
        timeTv = itemView.findViewById(R.id.chat_recent_contact_time_tv);
    }

    public void updateView(final RecentContact recentContact) {
        nameTv.setText(recentContact.getFromNick());
        contentTv.setText(recentContact.getContent());
        String timeStr;
        Date today = new Date(System.currentTimeMillis());
        Date recentday = new Date(recentContact.getTime());
        if (today.getDay() == recentday.getDay() && today.getMonth() == recentday.getMonth() && today.getYear() == recentday.getYear()) {
            timeStr = timeFormat.format(recentday);
        } else {
            timeStr = dateFormat.format(recentday);
        }
        timeTv.setText(timeStr);
        Log.d(TAG, "updateView: getUnreadCount = " + recentContact.getUnreadCount());
        if (recentContact.getUnreadCount() > 0) {
            unReadView.show("" + recentContact.getUnreadCount(), Color.WHITE, Color.WHITE, Color.parseColor("#d63c41"), DensityUtils.dp2px(14), DensityUtils.dp2px(10));
        } else {
            unReadView.hide();
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMMessage imMessage = MessageBuilder.createEmptyMessage(recentContact.getContactId(), SessionTypeEnum.P2P, recentContact.getTime());
                ChatRoomActivity.launch(itemView.getContext(), recentContact.getContactId(), imMessage);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MessageUtil.getService().deleteRecentContact(recentContact);
                return true;
            }
        });
    }
}
