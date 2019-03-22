package com.crazywah.piedpiper.module.homepage;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.login.LoginConst;
import com.crazywah.piedpiper.util.SPUtil;
import com.google.gson.Gson;

public class MainLogic extends BaseLogic {

    public static final int MSG_GET_MY_INFO_SUCC = 0;
    public static final int MSG_GET_MY_INFO_FAIL = 1;
    public static final int MSG_RECENT_CONTACT_CHANGE = 2;

    public MainLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void updateMyInfo() {
        RequestManager.getInstance().getUserInfo(new PiedCallback<User>() {
            @Override
            public void onSuccess(User object) {
                PiedPiperApplication.setLoginUser(object);
                SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).save(LoginConst.SP_KEY_USER, new Gson().toJson(PiedPiperApplication.getLoginUser()));
                notifyUi(MSG_GET_MY_INFO_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_MY_INFO_FAIL);
                return false;
            }
        });
    }

}
