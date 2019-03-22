package com.crazywah.piedpiper.common;

public class PiedEvent {

    public enum EventType {
        MSG_NOTIFY_REGISTRANT_UPDATE,
        MSG_AFTER_SELECT_DATE
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
