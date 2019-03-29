package com.crazywah.piedpiper.common;

public interface ConstValue {

    int ACCOUNT_MAX = 20;
    int ACCOUNT_MIN = 8;
    int PASSWORD_MAX = 20;
    int PASSWORD_MIN = 6;
    int NICKNAME_MAX = 20;
    int NICKNAME_MIN = 1;

    String[] USER_INFO_METHOD = new String[]{
            "getNickname",
            "getPassword",
            "getGenderString",
            "getBirthday",
            "getMobile",
            "getAddress",
            "getEmail",
            "getSignature"
    };

    String HEADER_K_TOKEN = "token";
    String HEADER_K_CONTENT_TYPE = "Content-Type";
    String HEADER_V_CONTENT_TYPE_JSON = "application/json";

    int LOGIN_INFO = 0;
    int FRIEND_REQUEST = 1;

    String[] SP_NAMES = new String[]{
        "LOGIN_INFO",
        "FRIEND_REQUEST"
    };

}
