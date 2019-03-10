package com.crazywah.piedpiper.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crazywah.piedpiper.bean.MessageBean;
import com.crazywah.piedpiper.chatroom.activity.ChatRoomActivity;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.util.MessageUtil;
import com.crazywah.piedpiper.util.NotificationUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class MessageService extends Service implements Observer<List<IMMessage>> {

    private static final String TAG = "MessageService";

    private Gson parser = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(this, true);
//        NIMClient.getService(MsgServiceObserve.class).
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(this, true);
    }

    @Override
    public void onEvent(List<IMMessage> imMessages) {
        if (!ChatRoomActivity.isShowing){
            // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
            for (IMMessage message : imMessages) {
                PiedToast.showShort("MessageService 收到信息 来自 " + message.getFromNick());
                NotificationUtil.showNotification(message);
                MessageUtil.getService().pullMessageHistory(message, 50, true).setCallback(new RequestCallback<List<IMMessage>>() {
                    @Override
                    public void onSuccess(List<IMMessage> param) {
                        Log.d(TAG, "onSuccess: history size = " + param.size());
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
            }
        }
    }
}
