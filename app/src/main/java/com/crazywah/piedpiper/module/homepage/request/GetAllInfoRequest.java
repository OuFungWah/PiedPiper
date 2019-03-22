package com.crazywah.piedpiper.module.homepage.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.Map;

public class GetAllInfoRequest extends RequestBase<User> {
    public GetAllInfoRequest(Response.Listener<User> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_INFO_BY_TOKEN, listener, errorListener);
    }

    @Override
    public Map<String, String> getParam() {
        return null;
    }
}
