package com.crazywah.piedpiper.module.discovery.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.discovery.adapter.MomentDetailItemAdapter;

import java.util.List;

public class MomentDetailViewHolder extends RecyclerView.ViewHolder {

    private static final int HORI_SIZE = 8;

    private ImageView iconImg;
    private RecyclerView listRv;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    public MomentDetailViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_like_list, parent, false));
        iconImg = itemView.findViewById(R.id.item_icon_img);
        listRv = itemView.findViewById(R.id.item_list_rv);
        gridLayoutManager = new GridLayoutManager(itemView.getContext(), HORI_SIZE, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    public void setLikeView(List list) {
        iconImg.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_like));
        if (list != null && !list.isEmpty()) {
            itemView.setVisibility(View.VISIBLE);
            listRv.setLayoutManager(gridLayoutManager);
            listRv.setAdapter(new MomentDetailItemAdapter(list, MomentDetailItemAdapter.TYPE.LIKE));
        } else {
            itemView.setVisibility(View.GONE);
        }
    }

    public void setCommentView(List list) {
        iconImg.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_comment));
        if (list != null && !list.isEmpty()) {
            itemView.setVisibility(View.VISIBLE);
            listRv.setLayoutManager(linearLayoutManager);
            listRv.setAdapter(new MomentDetailItemAdapter(list, MomentDetailItemAdapter.TYPE.COMMENT));
        } else {
            itemView.setVisibility(View.GONE);
        }
    }

}
