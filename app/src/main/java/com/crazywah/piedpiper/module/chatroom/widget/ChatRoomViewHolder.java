package com.crazywah.piedpiper.module.chatroom.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.module.chatroom.adapter.ChatRoomAdapter;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.DateUtil;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.MessageUtil;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserService;

import java.util.Date;

public class ChatRoomViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ChatRoomViewHolder";

    private View leftView;
    private View rightView;
    private TextView dateTv;
    private TextView leftContentTv;
    private TextView rightContentTv;
    private ImageView leftAvatarImg;
    private ImageView rightAvatarImg;
    private ProgressBar sendStatusPB;
    private ImageView sendStatusImg;

    private boolean isOpened = false;
    private boolean isAlwayOpened = false;

    public ChatRoomViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_chatroom, parent, false));
        leftView = itemView.findViewById(R.id.chatroom_item_left_rl);
        leftAvatarImg = itemView.findViewById(R.id.chatroom_item_left_avatar_img);
        leftContentTv = itemView.findViewById(R.id.chatroom_item_left_content_tv);
        rightView = itemView.findViewById(R.id.chatroom_item_right_rl);
        rightAvatarImg = itemView.findViewById(R.id.chatroom_item_right_avatar_img);
        rightContentTv = itemView.findViewById(R.id.chatroom_item_right_content_tv);
        dateTv = itemView.findViewById(R.id.chatroom_item_date_tv);
        sendStatusPB = itemView.findViewById(R.id.chatroom_item_send_flag_pb);
        sendStatusImg = itemView.findViewById(R.id.chatroom_item_send_flag_img);
    }

    public void setView(IMMessage message, User user) {
        setView(message, user, false);
    }

    public void setView(IMMessage message, User user, final boolean isAlwayOpened) {
        this.isAlwayOpened = isAlwayOpened;
        if (message.getFromAccount().equals(PiedPiperApplication.getLoginUser().getAccountId())) {
            rightView.setVisibility(View.VISIBLE);
            leftView.setVisibility(View.GONE);
            setContent(message.getContent(), PiedPiperApplication.getLoginUser(), rightAvatarImg, rightContentTv);
            setMessageStatus(message);
        } else {
            rightView.setVisibility(View.GONE);
            leftView.setVisibility(View.VISIBLE);
            setContent(message.getContent(), user, leftAvatarImg, leftContentTv);
        }
        setDateTv(message);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAlwayOpened) {
                    isOpened = !isOpened;
                    dateTv.setVisibility(isOpened ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    private void setDateTv(IMMessage message) {
        Date date = new Date(message.getTime());
        if (DateUtil.isToday(date)) {
            dateTv.setText(DateUtil.formatHMS(date));
        } else if (DateUtil.isYesteday(date)) {
            dateTv.setText("昨天 " + DateUtil.formatHMS(date));
        } else if (DateUtil.isInNDay(3, date)) {
            dateTv.setText(DateUtil.toWeekString(date) + "  " + DateUtil.formatHMS(date));
        } else if (DateUtil.isThisYear(date)) {
            dateTv.setText(DateUtil.formatMD(date) + "  " + DateUtil.formatHMS(date));
        } else {
            dateTv.setText(DateUtil.formatYMD(date) + "  " + DateUtil.formatHMS(date));
        }
        dateTv.setVisibility(isAlwayOpened ? View.VISIBLE : View.GONE);
    }

    private void setMessageStatus(IMMessage message) {
        //初始化信息状态
        sendStatusImg.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ic_send_succ));
        if (message.isRemoteRead()) {
//            Log.d(TAG, "setMessageStatus: message:\"" + message.getContent() + "\" isRemoteRead = " + message.isRemoteRead());
            sendStatusImg.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ic_read));
            sendStatusPB.setVisibility(View.GONE);
            sendStatusImg.setVisibility(View.VISIBLE);
        } else {
            switch (message.getStatus()) {
//            case read:
////                sendStatusPB.setVisibility(View.GONE);
////                sendStatusImg.setVisibility(View.VISIBLE);
////                sendStatusImg.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ic_read));
////                break;
//            case unread:
                case success:
//                    Log.d(TAG, "setMessageStatus: message:\"" + message.getContent() + "\" getStatus = " + message.getStatus());
                    sendStatusImg.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ic_send_succ));
                    sendStatusPB.setVisibility(View.GONE);
                    sendStatusImg.setVisibility(View.VISIBLE);
                    break;
                case sending:
                    sendStatusPB.setVisibility(View.VISIBLE);
                    sendStatusImg.setVisibility(View.GONE);
                    break;
                case fail:
                    sendStatusImg.setImageDrawable(itemView.getContext().getResources().getDrawable(R.drawable.ic_send_fail));
                    sendStatusPB.setVisibility(View.GONE);
                    sendStatusImg.setVisibility(View.VISIBLE);
                    break;
                default:
                    sendStatusPB.setVisibility(View.GONE);
                    sendStatusImg.setVisibility(View.GONE);
                    break;
            }
        }

    }

    private void setContent(String content, final User user, ImageView avatarImg, TextView contentTv) {
        ImageLoader.loadUserAvatar(user, avatarImg);
        contentTv.setText(content);
        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.launch(itemView.getContext(), user.getAccountId());
            }
        });
    }

}
