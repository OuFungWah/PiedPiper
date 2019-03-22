package com.crazywah.piedpiper.module.user.logic;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;

import java.util.Date;

public class ModifyInfoLogic extends BaseLogic {

    public static final int INFO_TYPE_NICKNAME = 0x001;
    public static final int INFO_TYPE_PASSWORD = 0x002;
    public static final int INFO_TYPE_GENDER = 0x003;
    public static final int INFO_TYPE_BIRTHDAY = 0x004;
    public static final int INFO_TYPE_MOBILE = 0x005;
    public static final int INFO_TYPE_ADDRESS = 0x006;
    public static final int INFO_TYPE_EMAIL = 0x007;
    public static final int INFO_TYPE_SIGNATURE = 0x008;

    public static final int MSG_UPDATE_SUCC = 0x001;
    public static final int MSG_UPDATE_FAIL = 0x002;

    public ModifyInfoLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void updatePassowrd(String newPassword) {
        RequestManager.getInstance().updatePassword(newPassword, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateNickname(String newNickname) {
        RequestManager.getInstance().updateNickname(newNickname, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateGender(String newGender) {
        RequestManager.getInstance().updateGender(newGender, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateBirthday(Date newBirthday) {
        RequestManager.getInstance().updateBirthday(newBirthday, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateMobile(String newMobile) {
        RequestManager.getInstance().updateMobile(newMobile, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateAddress(String newAddress) {
        RequestManager.getInstance().updateAddress(newAddress, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateEmail(String newEmail) {
        RequestManager.getInstance().updateEmail(newEmail, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

    public void updateSignature(String newSignature) {
        RequestManager.getInstance().updateSignature(newSignature, new PiedCallback<Void>() {
            @Override
            public void onSuccess(Void object) {
                notifyUi(MSG_UPDATE_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPDATE_FAIL);
                return false;
            }
        });
    }

}
