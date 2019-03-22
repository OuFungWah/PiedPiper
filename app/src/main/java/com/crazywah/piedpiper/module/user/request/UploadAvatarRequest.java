package com.crazywah.piedpiper.module.user.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class UploadAvatarRequest extends RequestBase<String> {

    private String base64;

    public UploadAvatarRequest(String base64, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_UPDATE_AVATAR, listener, errorListener);
        this.base64 = base64;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("base64",base64);
        return params;
    }
}
