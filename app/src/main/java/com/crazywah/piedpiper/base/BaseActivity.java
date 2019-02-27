package com.crazywah.piedpiper.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

public abstract class BaseActivity extends Activity {

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return onHandle(msg);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        prepareLogic();
    }

    protected abstract int getLayoutId();

    protected abstract boolean onHandle(Message msg);

    protected abstract void prepareLogic();

}
