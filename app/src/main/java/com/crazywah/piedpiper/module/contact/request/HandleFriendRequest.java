package com.crazywah.piedpiper.module.contact.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class HandleFriendRequest extends RequestBase<Void> {

    private String toId;
    private int result;
    private String attach;

    public HandleFriendRequest(String toId, int result, String attach, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_HANDLE_REQUEST, listener, errorListener);
        this.toId = toId;
        this.result = result;
        this.attach = attach;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("to", toId);
        params.put("result", result + "");
        params.put("attach", attach);
        return params;
    }
}
