package com.crazywah.piedpiper.module.discovery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.crazywah.piedpiper.base.BaseAdapter;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Like;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.discovery.widget.CommentItemViewHolder;
import com.crazywah.piedpiper.module.discovery.widget.LikeItemViewHolder;

import java.util.List;

public class MomentDetailItemAdapter extends BaseAdapter {

    private static final String TAG = "MomentDetailItemAdapter";

    public enum TYPE {
        LIKE,
        COMMENT
    }

    private List contents;
    private TYPE type;

    public MomentDetailItemAdapter(List contents, TYPE type) {
        Log.d(TAG, "MomentDetailItemAdapter: contents = " + contents.size());
        this.contents = contents;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.COMMENT.ordinal()) {
            return new CommentItemViewHolder(parent);
        } else if (viewType == TYPE.LIKE.ordinal()) {
            return new LikeItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentItemViewHolder) {
            ((CommentItemViewHolder) holder).setView((Comment) contents.get(position), checkIsSameNext(position));
        } else if (holder instanceof LikeItemViewHolder) {
            ((LikeItemViewHolder) holder).setView((Like) contents.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type.ordinal();
    }
}
