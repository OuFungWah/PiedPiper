package com.crazywah.piedpiper.module.chatroom.logic;

import android.content.Context;
import android.net.Network;
import android.os.Environment;
import android.os.Handler;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.util.MessageUtil;
import com.crazywah.piedpiper.util.NetworkUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomLogic extends BaseLogic {

    private static final String TAG = "ChatRoomLogic";

    public static final int MSG_GET_USER_INFO_SUCC = 0;
    public static final int MSG_GET_USER_INFO_FAIL = 1;
    public static final int MSG_FIRST_LOAD_MESSAGE_SUCC = 6;
    public static final int MSG_FIRST_LOAD_MESSAGE_FAIL = 7;
    public static final int MSG_LOAD_MORE_MESSAGE_SUCC = 8;
    public static final int MSG_LOAD_MORE_MESSAGE_FAIL = 9;
    public static final int MSG_NEW_MESSAGE_SUCC = 10;
    public static final int MSG_NEW_MESSAGE_FAIL = 11;

    private String targetId;
    private User target;
    private Gson parser = new Gson();

    private boolean isFirstLoad = true;

    public static final int PAGE_LIMIT = 20;

    private List<IMMessage> messageList = new ArrayList<>();

    public ChatRoomLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void getTargetInfo(String targetId) {
        this.targetId = targetId;
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

            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        });
        messageList.add(0, message);
        notifyUi(MSG_NEW_MESSAGE_SUCC);
        return message;
    }

    public void addToFirst(List<IMMessage> messages) {
        messageList.addAll(0, messages);
        notifyUi(MSG_NEW_MESSAGE_SUCC);
        sendRead(messages);
    }

    public void loadMessage(IMMessage message) {
        if (NetworkUtil.isNetworkAvailable()) {
            loadServerMessage(message);
        } else {
            loadLocalMessage(message);
        }
    }

    public void loadServerMessage(IMMessage message) {
        MessageUtil.getService().pullMessageHistory(message, PAGE_LIMIT, true).setCallback(new RequestCallback<List<IMMessage>>() {
            @Override
            public void onSuccess(List<IMMessage> param) {
                if (param == null || param.isEmpty()) {
                    PiedToast.showShort("到顶喽 (^ o ^)/~");
                } else {
                    sendRead(param);
                    addToOlder(param);
                }
                notifyLoadMessage(true);
            }

            @Override
            public void onFailed(int code) {
                notifyLoadMessage(false);
            }

            @Override
            public void onException(Throwable exception) {
                notifyLoadMessage(false);
            }
        });
    }

    public void loadLocalMessage(IMMessage message) {
        MessageUtil.getService().queryMessageListEx(message, QueryDirectionEnum.QUERY_OLD, PAGE_LIMIT, true).setCallback(new RequestCallback<List<IMMessage>>() {
            @Override
            public void onSuccess(List<IMMessage> param) {
                if (param == null || param.isEmpty()) {
                    PiedToast.showShort("到顶喽 (^ o ^)/~");
                } else {
                    sendRead(param);
                    addToOlder(param);
                }
                notifyLoadMessage(true);
            }

            @Override
            public void onFailed(int code) {
                notifyLoadMessage(false);
            }

            @Override
            public void onException(Throwable exception) {
                notifyLoadMessage(false);
            }
        });
    }

    private void sendRead(List<IMMessage> param) {
        for (IMMessage object : param) {
            if (!object.getFromAccount().equals(PiedPiperApplication.getLoginUser().getAccountId())) {
                MessageUtil.sendRemoteRead(object);
                break;
            }
        }
    }

    private void notifyLoadMessage(boolean isSucc) {
        if (isFirstLoad) {
            notifyUi(isSucc ? MSG_FIRST_LOAD_MESSAGE_SUCC : MSG_FIRST_LOAD_MESSAGE_FAIL);
            isFirstLoad = !isSucc;
        } else {
            notifyUi(isSucc ? MSG_LOAD_MORE_MESSAGE_SUCC : MSG_LOAD_MORE_MESSAGE_FAIL);
        }
    }

    public List<IMMessage> getMessageList() {
        return messageList;
    }

    public void addToOlder(List<IMMessage> list) {
        messageList.addAll(list);
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }
}
