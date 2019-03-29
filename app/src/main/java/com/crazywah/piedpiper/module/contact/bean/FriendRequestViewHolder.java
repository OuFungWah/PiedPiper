package com.crazywah.piedpiper.module.contact.bean;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.CustomMessageType;
import com.crazywah.piedpiper.bean.CustomNotificationBean;
import com.crazywah.piedpiper.bean.FriendRequest;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.contact.logic.FriendRequestLogic;
import com.crazywah.piedpiper.util.DensityUtils;
import com.crazywah.piedpiper.util.ImageLoader;
import com.google.gson.Gson;

public class FriendRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private FriendRequestLogic logic;

    private User user;

    private ImageView avatarImg;
    private TextView nameTv;
    private View btnContainer;
    private View acceptBtn;
    private View refuseBtn;
    private TextView statusTv;
    private View divider;
    private TextView requestMessageTv;

    private boolean isOpened = false;

    public FriendRequestViewHolder(ViewGroup parent, FriendRequestLogic logic) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_friend_request, parent, false));
        avatarImg = itemView.findViewById(R.id.friend_request_avatar_img);
        nameTv = itemView.findViewById(R.id.friend_request_name_tv);
        btnContainer = itemView.findViewById(R.id.friend_request_btn_container);
        acceptBtn = itemView.findViewById(R.id.friend_request_accept_rl);
        refuseBtn = itemView.findViewById(R.id.friend_request_refuse_rl);
        statusTv = itemView.findViewById(R.id.friend_request_status_tv);
        divider = itemView.findViewById(R.id.friend_request_divider);
        requestMessageTv = itemView.findViewById(R.id.friend_request_message_tv);
        this.logic = logic;
    }

    public void setView(final User user, boolean isSame) {
        this.user = user;
        ImageLoader.loadUserAvatar(user, avatarImg);
        nameTv.setText(user.getName());
        switch (user.getRequestStatus()) {
            case FriendRequest.REFUSED:
                btnContainer.setVisibility(View.GONE);
                statusTv.setVisibility(View.VISIBLE);
                statusTv.setText("已拒绝");
                setStatusBg("#b71c1c");
                break;
            case FriendRequest.REQUESTING:
                btnContainer.setVisibility(View.VISIBLE);
                statusTv.setVisibility(View.GONE);
                break;
            case FriendRequest.ACCEPTED:
                btnContainer.setVisibility(View.GONE);
                statusTv.setVisibility(View.VISIBLE);
                statusTv.setText("已接受");
                setStatusBg("#4caf50");
                break;
            default:
                btnContainer.setVisibility(View.GONE);
                statusTv.setVisibility(View.GONE);
                break;
        }
        requestMessageTv.setText(new Gson().fromJson(user.getRequestMessage(), CustomNotificationBean.class).getContent());
        requestMessageTv.setVisibility(isOpened ? View.VISIBLE : View.GONE);
        acceptBtn.setOnClickListener(this);
        refuseBtn.setOnClickListener(this);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpened = !isOpened;
                requestMessageTv.setVisibility(isOpened && !TextUtils.isEmpty(user.getRequestMessage()) ? View.VISIBLE : View.GONE);
            }
        });
        divider.setVisibility(isSame ? View.VISIBLE : View.GONE);
    }

    private void setStatusBg(String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(DensityUtils.dp2px(0.5f), Color.parseColor(color));
        drawable.setCornerRadius(DensityUtils.dp2px(2));
        statusTv.setTextColor(Color.parseColor(color));
        statusTv.setBackgroundDrawable(drawable);
    }

    @Override
    public void onClick(View v) {
        CustomNotificationBean bean = new CustomNotificationBean();
        bean.setFromName(PiedPiperApplication.getLoginUser().getName());
        bean.setType(CustomMessageType.TYPE_HANDLE_FRIEND_REQUEST);
        switch (v.getId()) {
            case R.id.friend_request_accept_rl:
                bean.setContent("我接受你的好友请求");
                if (user != null) {
                    logic.handleRequest(user, 1, bean);
                }
                break;
            case R.id.friend_request_refuse_rl:
                bean.setContent("我拒绝你的好友请求");
                if (user != null) {
                    logic.handleRequest(user, 0, bean);
                }
                break;
            default:
                break;
        }
    }
}
