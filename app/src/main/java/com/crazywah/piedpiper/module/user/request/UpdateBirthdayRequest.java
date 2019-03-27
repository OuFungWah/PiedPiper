package com.crazywah.piedpiper.module.user.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;
import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateBirthdayRequest extends RequestBase<Void> {

    private Date date;

    public UpdateBirthdayRequest(Date date, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_UPDATE_BIRTHDAY, listener, errorListener);
        this.date = date;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> map = new HashMap<>();
        map.put("time", date.getTime() + "");
        return map;
    }
}
