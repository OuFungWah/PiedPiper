package com.crazywah.piedpiper.bean;

import java.util.Date;

public class Like {
    public static final int TYPE_MOMENT = 1;
    public static final int TYPE_COMMENT = 2;

    private String avatar;
    private int likeId;
    private String fromId;
    private int objId;
    private int objType;
    private Date likeTime;

    public String getAvatar() {
        return avatar;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }

    public int getObjType() {
        return objType;
    }

    public void setObjType(int objType) {
        this.objType = objType;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}
