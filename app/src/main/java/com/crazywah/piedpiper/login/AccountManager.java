package com.crazywah.piedpiper.login;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class AccountManager {

    public void doRegister(){
    }

    public void doLogin(String account, String password,RequestCallback<LoginInfo> callback) {
        LoginInfo loginInfo = new LoginInfo(account, password);
        NIMClient.getService(AuthService.class).login(loginInfo)
                .setCallback(callback);
    }

    public void doLoginOut(){
        NIMClient.getService(AuthService.class).logout();
    }

    public static AccountManager getInstance() {
        return InnerHolder.instance;
    }

    private static class InnerHolder {
        protected static AccountManager instance = new AccountManager();
    }

}
