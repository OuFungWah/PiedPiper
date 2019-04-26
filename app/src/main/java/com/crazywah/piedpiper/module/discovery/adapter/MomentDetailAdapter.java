package com.crazywah.piedpiper.module.discovery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.base.BaseAdapter;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Like;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.module.discovery.bean.MomentDetail;
import com.crazywah.piedpiper.module.discovery.widget.MomentDetailViewHolder;
import com.crazywah.piedpiper.module.discovery.widget.MomentItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MomentDetailAdapter extends BaseAdapter {

    public static final int INDEX_MOMENT = 0;
    public static final int INDEX_LIKE = 1;
    public static final int INDEX_COMMENT = 2;

    private Moment moment;
    private List<Like> likeList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

    public MomentDetailAdapter(MomentDetail momentDetail) {
        setAll(momentDetail);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case INDEX_MOMENT:
                return new MomentItemViewHolder(parent);
            case INDEX_COMMENT:
            case INDEX_LIKE:
                return new MomentDetailViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case INDEX_MOMENT:
                if (moment != null) {
                    ((MomentItemViewHolder) holder).setView(moment, false);
                }
                break;
            case INDEX_LIKE:
                ((MomentDetailViewHolder) holder).setLikeView(likeList);
                break;
            case INDEX_COMMENT:
                ((MomentDetailViewHolder) holder).setCommentView(commentList);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setAll(MomentDetail momentDetail) {
        if (momentDetail != null) {
            setMoment(momentDetail.getMoment());
            setLikeList(momentDetail.getLikeList());
            setCommentList(momentDetail.getCommentList());
        }
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    public void setLikeList(List<Like> likeList) {
        if (likeList != null && !likeList.isEmpty()) {
            if (!this.likeList.isEmpty()) {
                this.likeList.clear();
            }
            this.likeList.addAll(likeList);
        }
    }

    public void setCommentList(List<Comment> commentList) {
        if (commentList != null && !commentList.isEmpty()) {
            if (!this.commentList.isEmpty()) {
                this.commentList.clear();
            }
            this.commentList.addAll(commentList);
        }
    }

    public Moment getMoment() {
        return moment;
    }

    public List<Like> getLikeList() {
        return likeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
