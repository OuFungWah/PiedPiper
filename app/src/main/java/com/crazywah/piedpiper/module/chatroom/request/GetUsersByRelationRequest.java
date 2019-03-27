package com.crazywah.piedpiper.module.chatroom.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUsersByRelationRequest extends RequestBase<List<User>> {

    private int relation = 0;

    public GetUsersByRelationRequest(int relation, Response.Listener<List<User>> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_USERS_BY_RELATION, listener, errorListener);
        this.relation = relation;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> map = new HashMap<>();
        map.put("relation", relation + "");
        return map;
    }
}
