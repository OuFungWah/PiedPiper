package com.crazywah.piedpiper.module.homepage.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetStrangersRequest extends RequestBase<List<User>> {

    private String accid;

    public GetStrangersRequest(String id, Response.Listener<List<User>> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_STRANGERS, listener, errorListener);
        this.accid = id;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("accid", accid);
        return params;
    }
}
