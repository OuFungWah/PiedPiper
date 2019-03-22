package com.crazywah.piedpiper.module.welcome;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.homepage.activity.MainActivity;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.login.LoginActivity;
import com.crazywah.piedpiper.module.login.LoginConst;
import com.crazywah.piedpiper.module.login.LoginLogic;
import com.crazywah.piedpiper.util.PermisstionUtil;
import com.crazywah.piedpiper.util.SPUtil;
import com.google.gson.Gson;

public class WelcomeActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST = 0;

    private LoginLogic logic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PermisstionUtil.check(PermisstionUtil.TYPE_WRITE_STORAGE)) {
            PermisstionUtil.requestAllPermission(this, PERMISSION_REQUEST);
        } else {
            autoLogin();
        }
    }

    public void autoLogin() {
        if (SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).contains(LoginConst.SP_KEY_ISAUTO) && SPUtil.getInstance().getBoolean(LoginConst.SP_KEY_ISAUTO)) {
            PiedPiperApplication.setLoginUser(new Gson().fromJson(SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getString(LoginConst.SP_KEY_USER), User.class));
            logic.loginWithToken(SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getString(LoginConst.SP_KEY_ACCOUNTID), SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getString(LoginConst.SP_KEY_TOKEN));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LoginActivity.launch(WelcomeActivity.this);
                    finish();
                }
            }).start();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case LoginLogic.LOGIN_SUCC:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MainActivity.launch(WelcomeActivity.this);
                        finish();
                    }
                }).start();
                break;
            default:
                PiedToast.showErrorShort("自动登录失败");
                LoginActivity.launch(WelcomeActivity.this);
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        autoLogin();
    }

    @Override
    protected void prepareLogic() {
        logic = new LoginLogic(this, handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logic = null;
    }
}
