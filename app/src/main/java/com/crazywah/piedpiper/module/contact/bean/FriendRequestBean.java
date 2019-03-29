package com.crazywah.piedpiper.module.contact.bean;

import java.util.List;

public class FriendRequestBean {

    private List<FriendRequest> list;

    public List<FriendRequest> getList() {
        return list;
    }

    public void setList(List<FriendRequest> list) {
        this.list = list;
    }

    public static class FriendRequest {

        private String id;
        private long time;

        public FriendRequest() {

        }

        public FriendRequest(String id, long time) {
            this.id = id;
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }


    }

}
