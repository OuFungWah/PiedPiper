package com.crazywah.piedpiper.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.chatroom.request.UserInfoRequest;
import com.crazywah.piedpiper.login.request.LoginRequest;
import com.crazywah.piedpiper.register.request.RegisterRequest;

public class RequestManager {

    private RequestQueue queue;

    public void init(Context context) {
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

    public void login(String accountId, String password, final PiedCallback<User> callback) {
        LoginRequest loginRequest = new LoginRequest(accountId, password, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFail("返回数据为空");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(loginRequest);
    }

    public void register(String accountId, String nickname, String password, final PiedCallback<User> callback) {
        RegisterRequest registerRequest = new RegisterRequest(accountId, nickname, password, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(registerRequest);
    }

    public void getUserInfo(String targetId, final PiedCallback<User> callback) {
        UserInfoRequest userInfoRequest = new UserInfoRequest(targetId, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(userInfoRequest);
    }

    public void addRequest(Request request) {
        queue.add(request);
    }

    public static RequestManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static RequestManager instance = new RequestManager();
    }

}
