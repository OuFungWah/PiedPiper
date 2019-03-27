package com.crazywah.piedpiper.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.crazywah.piedpiper.bean.CustomNotificationBean;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.util.NotificationUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

import org.greenrobot.eventbus.EventBus;

public class CustomMessageService extends Service implements Observer<CustomNotification> {

    private Gson parser = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(this, true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(this, false);
    }

    @Override
    public void onEvent(CustomNotification customNotification) {
        CustomNotificationBean bean = parser.fromJson(customNotification.getContent(), CustomNotificationBean.class);
        PiedEvent event;
        switch (bean.getType()) {
            case TYPE_FRIEND_REQUEST:
                event = new PiedEvent(PiedEvent.EventType.MSG_RECEIVE_FRIEND_REQUEST);
                EventBus.getDefault().post(event);
                NotificationUtil.showFriendRequestNotification(bean.getFromName(), bean.getContent());
                break;
            case TYPE_HANDLE_FRIEND_REQUEST:
                event = new PiedEvent(PiedEvent.EventType.MSG_RECEIVE_FRIEND_HANDLE_REQUEST);
                EventBus.getDefault().post(event);
                NotificationUtil.showHandleFriendRequestNotification(bean.getFromName(), bean.getContent());
                break;
            default:
                break;
        }
//        PiedToast.showShort("收到自定义消息");
    }
}
