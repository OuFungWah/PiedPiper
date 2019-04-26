package com.crazywah.piedpiper.module.discovery.bean;

import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Like;
import com.crazywah.piedpiper.bean.Moment;

import java.util.List;

public class MomentDetail {

    private Moment moment;
    private List<Like> likeList;
    private List<Comment> commentList;

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

}
