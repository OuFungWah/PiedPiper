package com.crazywah.piedpiper.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.crazywah.piedpiper.base.BaseReceiver;
import com.crazywah.piedpiper.base.CustomMessageService;
import com.crazywah.piedpiper.base.MessageService;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.util.NotificationUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class PiedPiperApplication extends Application {

    private static final String TAG = "PiedPiperApplication";

    private static Context context;
    private static User loginUser;

    @Override
    public void onCreate() {
        super.onCreate();
        PiedToast.init(this);
        loginUser = new User();
        //NIM 初始化
        context = PiedPiperApplication.this;
        NIMClient.init(this, loginInfo(), options());
        NIMClient.toggleNotification(false);
        combineBaseReceiver();
        this.startService(new Intent(this, MessageService.class));
        this.startService(new Intent(this, CustomMessageService.class));
        RequestManager.getInstance().init(this);
        NotificationUtil.init();
    }

    private void combineBaseReceiver() {
        BaseReceiver receiver = new BaseReceiver();
        IntentFilter intentFilter = new IntentFilter(context.getPackageName() + NimIntent.ACTION_RECEIVE_MSG);
//        IntentFilter intentFilter = new IntentFilter(context.getPackageName() + NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION);
//        intentFilter.addAction(context.getPackageName() + NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION);
//        intentFilter.addAction(context.getPackageName() + NimIntent.ACTION_RECEIVE_AVCHAT_CALL_NOTIFICATION);
//        intentFilter.addAction(context.getPackageName() + NimIntent.ACTION_RECEIVE_MSG);
//        intentFilter.addAction(context.getPackageName() + NimIntent.ACTION_RECEIVE_RTS_NOTIFICATION);
//        intentFilter.addAction(context.getPackageName() + NimIntent.EXTRA_BROADCAST_MSG);
//        intentFilter.addAction(context.getPackageName() + NimIntent.EXTRA_NOTIFY_CONTENT);
        this.registerReceiver(receiver, intentFilter, NimIntent.PERMISSION_RECEIVE_MSG, null);
    }

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        PiedPiperApplication.loginUser = loginUser;
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        return null;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
    }

    public static Context getInstance() {
        return context;
    }

}
