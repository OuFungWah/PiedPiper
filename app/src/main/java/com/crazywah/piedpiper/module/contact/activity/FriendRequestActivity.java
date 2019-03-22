package com.crazywah.piedpiper.module.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;

public class FriendRequestActivity extends BaseActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FriendRequestActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Override
    protected void prepareLogic() {

    }

}
