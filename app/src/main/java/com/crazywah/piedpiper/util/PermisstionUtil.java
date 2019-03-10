package com.crazywah.piedpiper.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;

public class PermisstionUtil {

    public static final int TYPE_WRITE_STORAGE = 0;
    public static final int TYPE_READ_STORAGE = 1;
    public static final int TYPE_CAMERA = 2;
    public static final int TYPE_MESSAGE = 3;
    public static final int TYPE_CALL = 4;

    public static boolean check(int type) {
        int check = ContextCompat.checkSelfPermission(PiedPiperApplication.getInstance(), getPermission(type));
        if (check == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPermission(int type) {
        String permission = "";
        switch (type) {
            case TYPE_WRITE_STORAGE:
                permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                break;
            case TYPE_READ_STORAGE:
                permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                break;
            case TYPE_CAMERA:
                permission = Manifest.permission.CAMERA;
                break;
            case TYPE_MESSAGE:
                permission = Manifest.permission.SEND_SMS;
                break;
            case TYPE_CALL:
                permission = Manifest.permission.CALL_PHONE;
                break;
            default:
                break;
        }
        return permission;
    }

    public static void requestAllPermission(BaseActivity activity, int code) {
        String[] permissions = new String[]{
                getPermission(TYPE_WRITE_STORAGE),
                getPermission(TYPE_CAMERA),
                getPermission(TYPE_CAMERA),
                getPermission(TYPE_MESSAGE),
                getPermission(TYPE_CALL)
        };
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

}
