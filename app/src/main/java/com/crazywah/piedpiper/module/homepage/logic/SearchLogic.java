package com.crazywah.piedpiper.module.homepage.logic;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class SearchLogic extends BaseLogic {

    public static final int MSG_SEARCH_SUCC = 0;
    public static final int MSG_SEARCH_FAIL = 1;

    private List<User> users = new ArrayList<>();

    public SearchLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void searchUser(String id){
        RequestManager.getInstance().searchStrangers(id, new PiedCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> object) {
                users = object;
                notifyUi(MSG_SEARCH_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_SEARCH_FAIL);
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
