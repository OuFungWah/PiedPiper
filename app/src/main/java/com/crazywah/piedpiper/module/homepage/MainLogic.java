package com.crazywah.piedpiper.module.homepage;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.module.login.LoginConst;
import com.crazywah.piedpiper.util.SPUtil;
import com.google.gson.Gson;

import java.util.List;

public class MainLogic extends BaseLogic {

    private static final String TAG = "MainLogic";

    private UserDBService userDBService;

    public static final int MSG_GET_MY_INFO_SUCC = 0;
    public static final int MSG_GET_MY_INFO_FAIL = 1;
    public static final int MSG_GET_FRIENDS_SUCC = 2;
    public static final int MSG_GET_FRIENDS_FAIL = 3;
    public static final int MSG_RECENT_CONTACT_CHANGE = 4;

    public MainLogic(Context context, Handler handler) {
        super(context, handler);
        userDBService = UserDBService.newInstance();
    }

    public void updateMyInfo() {
        RequestManager.getInstance().getUserInfo(new PiedCallback<User>() {
            @Override
            public void onSuccess(User object) {
                PiedPiperApplication.setLoginUser(object);
                userDBService.addOrUpdateUser(PiedPiperApplication.getLoginUser());
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

    public void loadFriends() {
        RequestManager.getInstance().getFriends(new PiedCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> object) {
                userDBService.addUsers(object);
                try {
                    Log.d(TAG, "onSuccess: size = " + userDBService.selectAll(new User()).size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyUi(MSG_GET_FRIENDS_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_FRIENDS_FAIL);
                return false;
            }
        });
    }

}
