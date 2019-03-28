package com.crazywah.piedpiper.module.chatroom.logic;

import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.util.MessageUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomLogic extends BaseLogic {

    private static final String TAG = "ChatRoomLogic";

    public static final int MSG_GET_USER_INFO_SUCC = 0;
    public static final int MSG_GET_USER_INFO_FAIL = 1;
    public static final int MSG_GET_HISTORY_SUCC = 2;
    public static final int MSG_GET_HISTORY_FAIL = 3;

    private User target;
    private Gson parser = new Gson();

    private List<IMMessage> messageList = new ArrayList<>();

    public ChatRoomLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void getTargetInfo(String targetId) {
        User dbSave = UserDBService.newInstance().selectUser(targetId);
        if (dbSave != null) {
            target = dbSave;
            notifyUi(MSG_GET_USER_INFO_SUCC);
        } else {
            RequestManager.getInstance().getUserInfo(targetId, new PiedCallback<User>() {
                @Override
                public void onSuccess(User object) {
                    target = object;
                    notifyUi(MSG_GET_USER_INFO_SUCC);
                }

                @Override
                public boolean onFail(String message) {
                    PiedToast.showShort(message);
                    notifyUi(MSG_GET_USER_INFO_FAIL);
                    return false;
                }
            });
        }
    }

    public IMMessage doSend(String targetId, String content) {
        IMMessage message = MessageUtil.sendMessage(targetId, content, new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                PiedToast.showShort("发送成功");
            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        });
        messageList.add(0, message);
        notifyUi(MSG_GET_HISTORY_SUCC);
        return message;
    }

    public void addToFirst(List<IMMessage> messages) {
        messageList.addAll(0, messages);
        notifyUi(MSG_GET_HISTORY_SUCC);
    }

    public void loadMessage(IMMessage message) {
        MessageUtil.getService().pullMessageHistory(message, 50, true).setCallback(new RequestCallback<List<IMMessage>>() {
            @Override
            public void onSuccess(List<IMMessage> param) {
                Log.d(TAG, "ChatLogic onSuccess: " + param.size());
                setMessageList(param);
                notifyUi(MSG_GET_HISTORY_SUCC);
            }

            @Override
            public void onFailed(int code) {
                notifyUi(MSG_GET_HISTORY_FAIL);
            }

            @Override
            public void onException(Throwable exception) {
                notifyUi(MSG_GET_HISTORY_FAIL);
            }
        });
    }

    public List<IMMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<IMMessage> list) {
        if (!messageList.isEmpty()) {
            messageList.clear();
        }
        messageList.addAll(list);
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }
}
