package com.crazywah.piedpiper.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.netease.nimlib.sdk.NimIntent;

public class BaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        switch (intent.getAction()) {
            case NimIntent.ACTION_RECEIVE_AVCHAT_CALL_NOTIFICATION:
                break;
            case NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION:
                break;
            case NimIntent.ACTION_RECEIVE_MSG:
                break;
            case NimIntent.ACTION_RECEIVE_RTS_NOTIFICATION:
                break;
            case NimIntent.EXTRA_BROADCAST_MSG:
                break;
            case NimIntent.EXTRA_NOTIFY_CONTENT:
                break;
            default:
                break;
        }
    }
}
