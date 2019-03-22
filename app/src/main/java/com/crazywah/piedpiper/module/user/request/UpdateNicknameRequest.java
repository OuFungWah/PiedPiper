package com.crazywah.piedpiper.module.user.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class UpdateNicknameRequest extends RequestBase<Void> {

    private String value;

    public UpdateNicknameRequest(String value, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_UPDATE_NICKNAME, listener, errorListener);
        this.value = value;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("nickname", value);
        return params;
    }
}
