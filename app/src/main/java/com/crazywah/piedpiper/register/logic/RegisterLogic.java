package com.crazywah.piedpiper.register.logic;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.bean.User;

public class RegisterLogic extends BaseLogic {

    public static final int REGISTER_FAIL = 0;
    public static final int REGISTER_SUCC = 1;

    public RegisterLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void register(String accountId, String nickname, String password){
        RequestManager.getInstance().register(accountId, nickname, password, new PiedCallback<User>() {
            @Override
            public void onSuccess(User object) {
                notifyUi(REGISTER_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(REGISTER_FAIL,"账户已注册");
                return false;
            }
        });
    }

}
