package com.crazywah.piedpiper.login.request;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;
import com.crazywah.piedpiper.common.User;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends RequestBase<User> {

    private String accountId;
    private String password;

    public LoginRequest(String accountId, String password, Response.Listener listener, @Nullable Response.ErrorListener errorListener) {
        super(RequestUrl.URL_LOGIN, listener, errorListener);
        this.accountId = accountId;
        this.password = password;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String,String> params = new HashMap<>();
        params.put("accid",accountId);
        params.put("password",password);
        return params;
    }
}
