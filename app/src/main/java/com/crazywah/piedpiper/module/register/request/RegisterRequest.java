package com.crazywah.piedpiper.module.register.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;
import com.crazywah.piedpiper.bean.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends RequestBase<User> {

    private String accountId;
    private String nickname;
    private String password;

    public RegisterRequest(String accountId, String nickname, String password, Response.Listener<User> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_REGISTER, listener, errorListener);
        this.accountId = accountId;
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String,String> params = new HashMap<>();
        params.put("accid",accountId);
        params.put("name",nickname);
        params.put("password",password);
        return params;
    }
}
