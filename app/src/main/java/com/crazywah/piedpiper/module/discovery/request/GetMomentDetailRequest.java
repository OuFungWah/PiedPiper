package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;
import com.crazywah.piedpiper.module.discovery.bean.MomentDetail;

import java.util.HashMap;
import java.util.Map;

public class GetMomentDetailRequest extends RequestBase<MomentDetail> {

    private int momentId;

    public GetMomentDetailRequest(int momentId, Response.Listener<MomentDetail> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_GET_MOMENT_DETAIL, listener, errorListener);
        this.momentId = momentId;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String,String> params = new HashMap<>();
        params.put("momentId",""+momentId);
        return params;
    }
}
