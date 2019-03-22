package com.crazywah.piedpiper.module.chat.logic;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.util.MessageUtil;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.ArrayList;
import java.util.List;

public class ChatFragmentLogic extends BaseLogic {

    public static final int LOAD_RECENT_CONTACTS_SUCC = 0;
    public static final int LOAD_RECENT_CONTACTS_FAIL = 1;

    private List<RecentContact> recentContacts = new ArrayList<>();

    public ChatFragmentLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void loadRecentContacts() {
        MessageUtil.getRecentContact(new RequestCallbackWrapper<List<RecentContact>>() {
            @Override
            public void onResult(int code, List<RecentContact> result, Throwable exception) {
                if(exception == null){
                    setRecentContacts(result);
                    notifyUi(LOAD_RECENT_CONTACTS_SUCC);
                }else{
                    notifyUi(LOAD_RECENT_CONTACTS_FAIL);
                }
            }
        });
    }

    public List<RecentContact> getRecentContacts() {
        return recentContacts;
    }

    public void setRecentContacts(List<RecentContact> recentContacts) {
        if (!this.recentContacts.isEmpty()) {
            this.recentContacts.clear();
        }
        this.recentContacts.addAll(recentContacts);
    }
}
