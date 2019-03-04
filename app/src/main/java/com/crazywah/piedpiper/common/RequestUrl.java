package com.crazywah.piedpiper.common;

public class RequestUrl {

    public static final boolean DEV = true;

    public static final String HOST = "http://www.crazywah.com:9527";
    public static final String DEV_HOST = "http://192.168.0.105:9527";

    public static final String URL_LOGIN = (DEV?DEV_HOST:HOST)+"/account/login";
    public static final String URL_REGISTER = (DEV?DEV_HOST:HOST)+"/account/register";
    public static final String URL_REFRESH_TOKEN = (DEV?DEV_HOST:HOST)+"/account/refreshToken";
    public static final String URL_REFRESH_PASSWORD = (DEV?DEV_HOST:HOST)+"/account/refreshPassword";
    public static final String URL_REFRESH_ADDTIONAL_INFO = (DEV?DEV_HOST:HOST)+"/account/refreshAdditionInfo";
    public static final String URL_REFRESH_SIGNATURE = (DEV?DEV_HOST:HOST)+"/account/refreshSignature";
    public static final String URL_ADD_FRIEND = (DEV?DEV_HOST:HOST)+"/friend/addFriend";
    public static final String URL_HANDLE_REQUEST = (DEV?DEV_HOST:HOST)+"/friend/handleRequest";
    public static final String URL_POST_MOMENT = (DEV?DEV_HOST:HOST)+"/moment/postMoment";
    public static final String URL_GET_MOMENTS = (DEV?DEV_HOST:HOST)+"/moment/getMomentsByToken";
    public static final String URL_DELETE_MOMENT = (DEV?DEV_HOST:HOST)+"/moment/deleteMoment";
    public static final String URL_ADD_COMMENT = (DEV?DEV_HOST:HOST)+"/moment/addComment";
    public static final String URL_GET_COMMENTS = (DEV?DEV_HOST:HOST)+"/moment/getCommentByMomentId";

}
