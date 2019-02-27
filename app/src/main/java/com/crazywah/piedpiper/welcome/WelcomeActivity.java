package com.crazywah.piedpiper.welcome;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.login.LoginActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Override
    protected void prepareLogic() {

    }
}
