package com.crazywah.piedpiper.module.user.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class AddFriendRequest extends RequestBase<Void> {

    private String fromId;
    private String toId;
    private String requestMessage;

    public AddFriendRequest(String fromId, String toId, String requestMessage, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_ADD_FRIEND, listener, errorListener);
        this.fromId = fromId;
        this.toId = toId;
        this.requestMessage = requestMessage;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("fromId", fromId);
        params.put("toId", toId);
        params.put("requestMessage", requestMessage);
        return params;
    }
}
