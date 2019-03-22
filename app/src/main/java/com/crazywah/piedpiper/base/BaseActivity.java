package com.crazywah.piedpiper.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.crazywah.piedpiper.common.PiedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {

    public static final int REQUEST_CODE_PERMISSION_STORAGE = 0x201;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 0x202;
    public static final int REQUEST_CODE_PERMISSION_MESSAGE = 0x203;
    public static final int REQUEST_CODE_PERMISSION_CALL = 0x204;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0x205;
    public static final int REQUEST_CODE_SELECT_PHOTO = 0x206;

    protected List<BaseResultDependence> resultDependences;

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return onHandle(msg);
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(getLayoutId());
        prepareLogic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (resultDependences != null) {
            for (BaseResultDependence dependence : resultDependences) {
                dependence.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultDependences != null) {
            for (BaseResultDependence dependence : resultDependences) {
                dependence.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void addDependence(BaseResultDependence dependence) {
        if (resultDependences == null) {
            resultDependences = new ArrayList<>();
        }
        resultDependences.add(dependence);
    }

    protected void removeDependence(BaseResultDependence dependence) {
        if (resultDependences != null) {
            resultDependences.remove(dependence);
        }
    }

    protected abstract int getLayoutId();

    protected abstract boolean onHandle(Message msg);

    protected abstract void prepareLogic();

    @Subscribe
    public void onEvent(PiedEvent event) {/* Do something */}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
