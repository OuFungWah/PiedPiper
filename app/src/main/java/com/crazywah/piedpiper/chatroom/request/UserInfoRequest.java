package com.crazywah.piedpiper.chatroom.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class UserInfoRequest extends RequestBase<User> {

    private String targetId;

    public UserInfoRequest(String targetId, Response.Listener<User> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_USER_BY_ID, listener, errorListener);
        this.targetId = targetId;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("accid", targetId);
        return params;
    }

}
