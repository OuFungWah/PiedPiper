package com.crazywah.piedpiper.module.login;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;


import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.common.ResponseStateCode;
import com.crazywah.piedpiper.bean.User;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class LoginLogic extends BaseLogic {

    public static final int ERROR_NO_ACCOUNT = 0;
    public static final int ERROR_NO_PASSWORD = 1;
    public static final int ERROR_ACCOUNT_LEN = 2;
    public static final int ERROR_PASSWORD_LEN = 3;
    public static final int LOGIN_SUCC = 4;
    public static final int LOGIN_FAIL = 5;

    public LoginLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void doLogin(final String account, final String password) {
        if (TextUtils.isEmpty(account)) {
            notifyUi(ERROR_NO_ACCOUNT);
        } else if (account.length() < 8) {
            notifyUi(ERROR_ACCOUNT_LEN);
        } else if (TextUtils.isEmpty(password)) {
            notifyUi(ERROR_NO_PASSWORD);
        } else if (password.length() < 6) {
            notifyUi(ERROR_PASSWORD_LEN);
        } else {
            RequestManager.getInstance().login(account, password, new PiedCallback<User>() {
                @Override
                public void onSuccess(final User object) {
                    if (object != null) {
                        PiedPiperApplication.setLoginUser(object);
                        loginWithToken(account, object.getToken());
                    } else {
                        notifyUi(LOGIN_FAIL, "返回数据为空");
                    }
                }

                @Override
                public boolean onFail(String message) {
                    notifyUi(LOGIN_FAIL, message);
                    return false;
                }
            });
        }
    }

    public void loginWithToken(String accountId, String token) {
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        notifyUi(LOGIN_SUCC);
                    }

                    @Override
                    public void onFailed(int code) {
                        notifyUi(LOGIN_FAIL, ResponseStateCode.getMessageByCode(code));
                    }

                    @Override
                    public void onException(Throwable exception) {
                        notifyUi(LOGIN_FAIL, exception.getMessage());
                    }
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                };
        AccountManager.getInstance().doLogin(accountId, token, callback);
    }

}
