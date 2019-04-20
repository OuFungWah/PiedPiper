package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class DeleteMomentRequest extends RequestBase<Integer> {

    private int momentId;

    public DeleteMomentRequest(int momentId, Response.Listener<Integer> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_DELETE_MOMENT, listener, errorListener);
        this.momentId = momentId;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("momentId", momentId + "");
        return params;
    }
}
