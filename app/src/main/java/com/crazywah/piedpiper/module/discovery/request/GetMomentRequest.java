package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMomentRequest extends RequestBase<List<Moment>> {

    private int limit;
    private int offset;

    public GetMomentRequest(int limit, int offset, Response.Listener<List<Moment>> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_ALL_MOMENTS, listener, errorListener);
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("limit", limit + "");
        params.put("offset", offset + "");
        return params;
    }
}
