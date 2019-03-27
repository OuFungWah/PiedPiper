package com.crazywah.piedpiper.bean;

public class CustomNotificationBean {

    private CustomMessageType type;
    private String fromName;
    private String content;

    public CustomMessageType getType() {
        return type;
    }

    public void setType(CustomMessageType type) {
        this.type = type;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
