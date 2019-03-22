package com.crazywah.piedpiper.module.chatroom.request;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class PicUpLoadRequest extends RequestBase {

    private String picBase64;

    public PicUpLoadRequest(String picBase64, Response.Listener listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_UPLOAD_PIC, listener, errorListener);
        this.picBase64 = picBase64;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> map = new HashMap<>();
        map.put("base64",picBase64);
        return map;
    }
}
