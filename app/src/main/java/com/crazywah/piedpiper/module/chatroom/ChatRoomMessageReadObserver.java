package com.crazywah.piedpiper.module.chatroom;

import com.crazywah.piedpiper.common.PiedEvent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.model.MessageReceipt;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChatRoomMessageReadObserver implements Observer<List<MessageReceipt>> {
    @Override
    public void onEvent(List<MessageReceipt> messageReceipts) {
        for (MessageReceipt receipt : messageReceipts) {
            PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_UPDATE_MESSAGE_READ);
            event.setParams(receipt.getSessionId());
            EventBus.getDefault().post(event);
        }
    }
}
