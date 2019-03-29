package com.crazywah.piedpiper.module.chatroom;

import com.crazywah.piedpiper.common.PiedEvent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.greenrobot.eventbus.EventBus;

public class ChatRoomMessageStatusObserver implements Observer<IMMessage> {
    @Override
    public void onEvent(IMMessage message) {
        if (message.getStatus() != MsgStatusEnum.read || message.getStatus() != MsgStatusEnum.unread) {
            PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_UPDATE_MESSAGE_STATUS);
            event.setParams(message);
            EventBus.getDefault().post(event);
        }
    }
}
