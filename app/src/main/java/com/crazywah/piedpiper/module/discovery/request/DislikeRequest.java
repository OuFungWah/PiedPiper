package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class DislikeRequest extends RequestBase<Void> {

    private int momentId;

    public DislikeRequest(int momentId, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_DISLIKE_MOMENT, listener, errorListener);
        this.momentId = momentId;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("objId", momentId+"");
        return params;
    }
}
