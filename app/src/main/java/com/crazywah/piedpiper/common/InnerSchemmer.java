package com.crazywah.piedpiper.common;

import android.content.Context;
import android.os.Bundle;

import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.base.BaseSchemer;
import com.crazywah.piedpiper.module.chatroom.activity.ChatRoomActivity;
import com.crazywah.piedpiper.module.contact.activity.FriendRequestActivity;
import com.crazywah.piedpiper.module.homepage.activity.MainActivity;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class InnerSchemmer extends BaseSchemer {

    private InnerSchemmer() {

    }

    @Override
    public void launchApp(Context context) {
        MainActivity.launch(context);
    }

    @Override
    public void launchChatRoom(Context context) {
        BaseActivity baseActivity = (BaseActivity) context;
        Bundle params = baseActivity.getIntent().getExtras();
        if (params != null && params.containsKey(ChatRoomActivity.KEY_ID) && params.containsKey(ChatRoomActivity.KEY_LATEST_MESSAGE)) {
            ChatRoomActivity.launch(context, params.getString("key_id"));
        }
    }

    @Override
    public void launchFriendRequest(Context context) {
        FriendRequestActivity.launch(context);
    }

    public static InnerSchemmer getInstance() {
        return Holder.instance;
    }

    private static final class Holder {
        public static InnerSchemmer instance = new InnerSchemmer();
    }

}
