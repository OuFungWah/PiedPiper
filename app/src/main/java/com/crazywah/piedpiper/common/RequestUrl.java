package com.crazywah.piedpiper.common;

public class RequestUrl {

    public static final boolean DEV = false;

    //    public static final String HOST = "http://121.42.205.69:9527";
    public static final String HOST = "http://www.crazywah.com:9527";
    public static final String DEV_HOST = "http://192.168.199.117:9527";

    public static final String URL_LOGIN = (DEV ? DEV_HOST : HOST) + "/account/login";
    public static final String URL_REGISTER = (DEV ? DEV_HOST : HOST) + "/account/register";
    public static final String URL_GET_INFO_BY_TOKEN = (DEV ? DEV_HOST : HOST) + "/account/getMyInfo";
    public static final String URL_REFRESH_TOKEN = (DEV ? DEV_HOST : HOST) + "/account/refreshToken";
    public static final String URL_REFRESH_PASSWORD = (DEV ? DEV_HOST : HOST) + "/account/refreshPassword";
    public static final String URL_REFRESH_ADDTIONAL_INFO = (DEV ? DEV_HOST : HOST) + "/account/refreshAdditionInfo";
    public static final String URL_UPDATE_SIGNATURE = (DEV ? DEV_HOST : HOST) + "/account/refreshSignature";
    public static final String URL_UPDATE_AVATAR = (DEV ? DEV_HOST : HOST) + "/account/updateAvatar";
    public static final String URL_UPDATE_NICKNAME = (DEV ? DEV_HOST : HOST) + "/account/updateNickName";
    public static final String URL_UPDATE_PASSWORD = (DEV ? DEV_HOST : HOST) + "/account/updatePassword";
    public static final String URL_UPDATE_GENDER = (DEV ? DEV_HOST : HOST) + "/account/updateGender";
    public static final String URL_UPDATE_BIRTHDAY = (DEV ? DEV_HOST : HOST) + "/account/updateBirthday";
    public static final String URL_UPDATE_MOBILE = (DEV ? DEV_HOST : HOST) + "/account/updateMobile";
    public static final String URL_UPDATE_ADDRESS = (DEV ? DEV_HOST : HOST) + "/account/updateAddress";
    public static final String URL_UPDATE_EMAIL = (DEV ? DEV_HOST : HOST) + "/account/updateEmail";
    public static final String URL_UPLOAD_PIC = (DEV ? DEV_HOST : HOST) + "/common/uploadPic";
    public static final String URL_GET_USERS_BY_RELATION = (DEV ? DEV_HOST : HOST) + "/user/getUsersByRelation";
    public static final String URL_GET_STRANGERS = (DEV ? DEV_HOST : HOST) + "/user/searchStrangers";
    public static final String URL_GET_USER_BY_ID = (DEV ? DEV_HOST : HOST) + "/user/getSingleUserById";
    public static final String URL_ADD_FRIEND = (DEV ? DEV_HOST : HOST) + "/friend/addFriend";
    public static final String URL_GET_ALL_REQUEST = (DEV ? DEV_HOST : HOST) + "/friend/getAllRequest";
    public static final String URL_HANDLE_REQUEST = (DEV ? DEV_HOST : HOST) + "/friend/handleRequest";
    public static final String URL_POST_MOMENT = (DEV ? DEV_HOST : HOST) + "/moment/postMoment";
    public static final String URL_GET_MOMENTS = (DEV ? DEV_HOST : HOST) + "/moment/getMomentsByToken";
    public static final String URL_GET_ALL_MOMENTS = (DEV ? DEV_HOST : HOST) + "/moment/getAllMoments";
    public static final String URL_DELETE_MOMENT = (DEV ? DEV_HOST : HOST) + "/moment/deleteMoment";
    public static final String URL_ADD_COMMENT = (DEV ? DEV_HOST : HOST) + "/moment/addComment";
    public static final String URL_GET_COMMENTS = (DEV ? DEV_HOST : HOST) + "/moment/getCommentByMomentId";
    public static final String URL_DELETE_COMMENTS = (DEV ? DEV_HOST : HOST) + "/moment/deleteComment";
    public static final String URL_LIKE_MOMENT = (DEV ? DEV_HOST : HOST) + "/moment/likeMoment";
    public static final String URL_DISLIKE_MOMENT = (DEV ? DEV_HOST : HOST) + "/moment/dislikeMoment";

}
