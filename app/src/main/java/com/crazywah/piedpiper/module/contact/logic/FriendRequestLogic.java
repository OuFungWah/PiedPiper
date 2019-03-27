package com.crazywah.piedpiper.module.contact.logic;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.CustomNotificationBean;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.contact.util.RequestTimeComparator;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class FriendRequestLogic extends BaseLogic {

    public static final int MSG_GET_USERS_SUCC = 0;
    public static final int MSG_GET_USERS_FAIL = 1;
    public static final int MSG_HANDLE_SUCC = 2;
    public static final int MSG_HANDLE_FAIL = 3;

    private List<User> users;
    private Gson parser = new Gson();

    public FriendRequestLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void loadRequestList() {
        RequestManager.getInstance().getAllFriendRequests(new PiedCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> object) {
                users = object;
                Collections.sort(users, new RequestTimeComparator());
                notifyUi(MSG_GET_USERS_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_USERS_FAIL);
                return false;
            }
        });
    }

    public void handleRequest(User user, int result, CustomNotificationBean bean) {
        RequestManager.getInstance().handleFriendRequest(user.getAccountId(), result, parser.toJson(bean), new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_HANDLE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_HANDLE_FAIL);
                return false;
            }
        });
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
