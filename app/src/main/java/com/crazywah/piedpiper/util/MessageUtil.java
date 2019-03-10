package com.crazywah.piedpiper.util;

import android.support.annotation.Nullable;

import com.netease.nimlib.sdk.InvocationFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class MessageUtil {

    public static IMMessage sendMessage(String targetId, String content, @Nullable RequestCallback<Void> callback) {
        // 创建文本消息
        IMMessage message = MessageBuilder.createTextMessage(
                targetId, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                content // 文本内容
        );
        // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        if (callback != null) {
            getService().sendMessage(message, true).setCallback(callback);
        } else {
            getService().sendMessage(message, true);
        }
        return message;
    }

    public static void getHistory(int limit, IMMessage anchor, RequestCallback<List<IMMessage>> callback) {
        getService().pullMessageHistory(anchor, limit, true).setCallback(callback);
    }

    public static int getTotalUnReadCount(){
        return getService().getTotalUnreadCount();
    }

    public static void getUnRead(){

    }

    public static MsgService getService(){
        return NIMClient.getService(MsgService.class);
    }

}
