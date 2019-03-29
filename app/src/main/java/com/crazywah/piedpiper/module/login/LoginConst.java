package com.crazywah.piedpiper.module.login;

import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.util.SPUtil;

public class LoginConst {

    public static String SP_KEY_ISAUTO = "isAuto";
    public static String SP_KEY_ISREMEMBER = "isRemember";
    public static String SP_KEY_ACCOUNTID = "accountId";
    public static String SP_KEY_PASSWORD = "password";
    public static String SP_KEY_TOKEN = "token";
    public static String SP_KEY_USER = "user";

    public static boolean isAutoLogin() {
        return (!SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).contains(SP_KEY_ISAUTO) && SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getBoolean(SP_KEY_ISAUTO));
    }

    public static boolean isRemember() {
        return (!SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).contains(SP_KEY_ISREMEMBER) && SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getBoolean(SP_KEY_ISREMEMBER));
    }

}
