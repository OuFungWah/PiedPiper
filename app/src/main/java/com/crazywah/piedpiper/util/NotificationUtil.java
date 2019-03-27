package com.crazywah.piedpiper.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseSchemer;
import com.crazywah.piedpiper.base.SchemerActivity;
import com.crazywah.piedpiper.bean.CustomNotificationBean;
import com.crazywah.piedpiper.bean.MessageBean;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class NotificationUtil {

    private static int ID = 0;
    private static final String CHANNEL_NAME = "PiedPiper";
    private static NotificationManager manager;
    private static Gson parser = new Gson();

    public static void init() {
        manager = getManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager().createNotificationChannel(new NotificationChannel(CHANNEL_NAME, "PiedPiper", NotificationManager.IMPORTANCE_HIGH));
        }
    }

    public static void showNotification(MessageBean messageBean) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PiedPiperApplication.getInstance());
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(messageBean.getFromNickName())
                .setContentText(messageBean.getMessage())
                .setAutoCancel(true)
                .setChannelId(CHANNEL_NAME);
        Intent intent = new Intent(PiedPiperApplication.getInstance(), SchemerActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("piedpiper://crazywah.com/chat_room"));
        Bundle params = new Bundle();
        params.putString("key_id", messageBean.getFrom());
        intent.putExtras(params);
        PendingIntent pendingIntent = PendingIntent.getActivity(PiedPiperApplication.getInstance(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        getManager().notify("PiedPiper", ID++, builder.build());
    }

    public static void showNotification(IMMessage message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PiedPiperApplication.getInstance());
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(message.getFromNick())
                .setContentText(message.getContent())
                .setAutoCancel(true)
                .setChannelId(CHANNEL_NAME);
        Bundle params = new Bundle();
        params.putString("key_id", message.getFromAccount());
        params.putSerializable("latest_message", message);
        showNotification(builder,BaseSchemer.SCHEME + BaseSchemer.HOST + BaseSchemer.CHATROOM,params);
    }

    public static void showFriendRequestNotification(String name, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PiedPiperApplication.getInstance());
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(name + "发来好友请求")
                .setContentText(content)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_NAME);
        showNotification(builder, BaseSchemer.SCHEME + BaseSchemer.HOST + BaseSchemer.FRIEND_REQUEST);
    }

    public static void showHandleFriendRequestNotification(String name, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PiedPiperApplication.getInstance());
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle("好友处理消息")
                .setContentText(name+" : "+content)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_NAME);
        showNotification(builder, BaseSchemer.SCHEME + BaseSchemer.HOST + BaseSchemer.LAUNCH_APP);
    }

    private static void showNotification(NotificationCompat.Builder builder, String url) {
        showNotification(builder, url, null);
    }

    private static void showNotification(NotificationCompat.Builder builder, String url, Bundle params) {
        Intent intent = new Intent(PiedPiperApplication.getInstance(), SchemerActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (params != null) {
            intent.putExtras(params);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(PiedPiperApplication.getInstance(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        getManager().notify("PiedPiper", ID++, builder.build());
    }

    public static void cannelAll() {
        manager.cancelAll();
    }

    public static NotificationManager getManager() {
        return (NotificationManager) PiedPiperApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
