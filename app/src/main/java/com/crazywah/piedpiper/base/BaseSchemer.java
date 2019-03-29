package com.crazywah.piedpiper.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.crazywah.piedpiper.common.PiedToast;

public abstract class BaseSchemer {

    public static final String SCHEME = "piedpiper://";
    public static final String HOST = "crazywah.com";

    public static final String LAUNCH_APP = "/launch";
    public static final String CHATROOM = "/chat_room";
    public static final String FRIEND_REQUEST = "/friend_request";

    public void launch(Context context) {
        Intent intent = ((BaseActivity) context).getIntent();
        Uri uri = intent.getData();
//        PiedToast.showShort(uri.getPath());
        if (uri.getPath().equals(LAUNCH_APP)) {
            launchApp(context);
        } else if (uri.getPath().equals(CHATROOM)) {
            launchChatRoom(context);
        } else if (uri.getPath().equals(FRIEND_REQUEST)) {
            launchFriendRequest(context);
        }
    }

    public abstract void launchApp(Context context);

    public abstract void launchChatRoom(Context context);

    public abstract void launchFriendRequest(Context context);

}
