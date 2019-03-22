package com.crazywah.piedpiper.module.user.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class UpdateGenderRequest extends RequestBase<Void> {

    private String value;

    public UpdateGenderRequest(String value, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_UPDATE_GENDER, listener, errorListener);
        this.value = value;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("gender", value);
        return params;
    }
}
