package com.crazywah.piedpiper.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.crazywah.piedpiper.bean.MessageBean;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.util.NotificationUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

public class BaseReceiver extends BroadcastReceiver {

    private static final String TAG = "BaseReceiver";

    private Gson parser = new Gson();

    @Override
    public void onReceive(Context context, Intent intent) {

        PiedToast.showShort("收到消息");

        if (intent.getAction() != null) {
            if (intent.getAction().equals(context.getPackageName() + NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION)) {
                // 从intent中取出自定义通知
                CustomNotification notification = (CustomNotification) intent.getSerializableExtra(NimIntent.EXTRA_BROADCAST_MSG);
                Log.d(TAG, "onReceive: " + notification.getContent());
                MessageBean obj = parser.fromJson(notification.getContent(), MessageBean.class);
                PiedToast.showShort(obj.getFromNickName() + ":" + obj.getMessage());
                NotificationUtil.showNotification(obj);
//                Log.d(TAG, "onReceive: ");
//                if (obj != null && obj.getId() == 2) {
//
//                    Log.d(TAG, "onReceive - receive custom notification: " + notification.getContent() + " from :" + notification.getSessionId() + "/" + notification.getSessionType());
//                    // Toast
//                    String content = obj.getContent();
//                    String tip = String.format("自定义消息[%s]：%s", notification.getFromAccount(), content);
//                    PiedToast.showShort(tip);
//                }
            } else if (intent.getAction().equals(context.getPackageName() + NimIntent.ACTION_RECEIVE_AVCHAT_CALL_NOTIFICATION)) {
                PiedToast.showShort("收到 ACTION_RECEIVE_AVCHAT_CALL_NOTIFICATION 信息");
            } else if (intent.getAction().equals(context.getPackageName() + NimIntent.ACTION_RECEIVE_MSG)) {
                PiedToast.showShort("收到 ACTION_RECEIVE_MSG 信息");
            } else if (intent.getAction().equals(context.getPackageName() + NimIntent.ACTION_RECEIVE_RTS_NOTIFICATION)) {
                PiedToast.showShort("收到 ACTION_RECEIVE_RTS_NOTIFICATION 信息");
            } else if (intent.getAction().equals(context.getPackageName() + NimIntent.EXTRA_BROADCAST_MSG)) {
                PiedToast.showShort("收到 EXTRA_BROADCAST_MSG 信息");
            } else if (intent.getAction().equals(context.getPackageName() + NimIntent.EXTRA_NOTIFY_CONTENT)) {
                PiedToast.showShort("收到 EXTRA_NOTIFY_CONTENT 信息");
            }
        }
    }

}
