package com.crazywah.piedpiper.module.contact.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.List;
import java.util.Map;

public class FriendListRequest extends RequestBase<List<User>> {

    public FriendListRequest( Response.Listener<List<User>> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_ALL_REQUEST, listener, errorListener);
    }

    @Override
    public Map<String, String> getParam() {
        return null;
    }

}
