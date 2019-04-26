package com.crazywah.piedpiper.common;

public class PiedEvent {

    public enum EventType {
        MSG_NOTIFY_REGISTRANT_UPDATE,
        MSG_AFTER_SELECT_DATE,
        MSG_RECEIVE_FRIEND_REQUEST,
        MSG_READ_FRIEND_REQUEST,
        MSG_RECEIVE_FRIEND_HANDLE_REQUEST,
        MSG_UPDATE_FRIEND_LIST,
        MSG_UPDATE_MESSAGE_STATUS,
        MSG_UPDATE_MESSAGE_READ,
        MSG_UPDATE_UNREAD_COUNT,
        MSG_REQUEST_PHOTO,
        MSG_REMOVE_POSTING_PHOTO,
        //朋友圈相关
        MSG_POST_MOMENT_SUCC,
        MSG_PROGRESSING_DELETE,
        MSG_DELETE_MOMENT_SUCC,
        MSG_DELETE_MOMENT_FAIL,
        MSG_CLICK_COMMENT,
        MSG_CLICK_LIKE,
        MSG_COMMENT_SUCC,
        MSG_DELETE_COMMENT_SUCC,
        MSG_LIKE_SUCC,
        MSG_DISLIKE_SUCC
    }

    private EventType type;
    private Object params;

    public PiedEvent(EventType type) {
        this.type = type;
    }

    public PiedEvent(EventType type, Object params) {
        this.type = type;
        this.params = params;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
