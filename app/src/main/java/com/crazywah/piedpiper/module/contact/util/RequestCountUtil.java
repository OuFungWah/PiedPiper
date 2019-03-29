package com.crazywah.piedpiper.module.contact.util;

import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.contact.bean.FriendRequestBean;
import com.crazywah.piedpiper.module.contact.common.ContactConstValue;
import com.crazywah.piedpiper.util.SPUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RequestCountUtil {

    public static void addRequest(String id, long time) {
        Gson parser = new Gson();
        if (SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).contains(ContactConstValue.REQUEST_LIST)) {
            String listStr = SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).getString(ContactConstValue.REQUEST_LIST);
            FriendRequestBean bean = parser.fromJson(listStr, FriendRequestBean.class);
            boolean isExisted = false;
            for (FriendRequestBean.FriendRequest object : bean.getList()) {
                if (object.getId().equals(id)) {
                    object.setTime(time);
                    isExisted = true;
                }
            }
            if (!isExisted) {
                bean.getList().add(new FriendRequestBean.FriendRequest(id, time));
            }
            SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).save(ContactConstValue.REQUEST_LIST, parser.toJson(bean));
        } else {
            FriendRequestBean bean = new FriendRequestBean();
            bean.setList(new ArrayList<FriendRequestBean.FriendRequest>());
            bean.getList().add(new FriendRequestBean.FriendRequest(id, time));
            SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).save(ContactConstValue.REQUEST_LIST, parser.toJson(bean));
        }
    }

    public static void updateLastTime() {
        SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).save(ContactConstValue.LAST_OPEN_REQUEST_TIME, System.currentTimeMillis());
        EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_READ_FRIEND_REQUEST));
    }

    public static int getRequestUnRead() {
        List<FriendRequestBean.FriendRequest> list = getRequestList();
        if (list == null || list.isEmpty()) {
            return 0;
        }
        if (SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).contains(ContactConstValue.LAST_OPEN_REQUEST_TIME)) {
            int sum = 0;
            long curr = getLastTime();
            for (FriendRequestBean.FriendRequest request : list) {
                if (request.getTime() >= curr) {
                    sum++;
                }
            }
            return sum;
        } else {
            return list.size();
        }
    }

    public static List<FriendRequestBean.FriendRequest> getRequestList() {
        Gson parser = new Gson();
        if (SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).contains(ContactConstValue.REQUEST_LIST)) {
            String listStr = SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).getString(ContactConstValue.REQUEST_LIST);
            FriendRequestBean bean = parser.fromJson(listStr, FriendRequestBean.class);
            if(bean.getList()!=null && !bean.getList().isEmpty()){
                return bean.getList();
            }else{
                return null;
            }
        } else {
            return null;
        }
    }

    public static long getLastTime() {
        return SPUtil.from(ConstValue.SP_NAMES[ConstValue.FRIEND_REQUEST]).getLong(ContactConstValue.LAST_OPEN_REQUEST_TIME);
    }

}
