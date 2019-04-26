package com.crazywah.piedpiper.module.discovery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.module.discovery.widget.FooterViewHolder;
import com.crazywah.piedpiper.module.discovery.widget.FooterViewHolder.STATE;
import com.crazywah.piedpiper.module.discovery.widget.MomentItemViewHolder;


import java.util.List;

public class DiscoveryMomentAdapter extends RecyclerView.Adapter {

    public enum TYPE {
        CONTENT,
        FOOTER
    }

    public STATE state = STATE.NORMAL;

    private static final String TAG = "DiscoveryMomentAdapter";

    private List<Moment> objectList;

    public DiscoveryMomentAdapter(List<Moment> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.CONTENT.ordinal()) {
            return new MomentItemViewHolder(parent);
        } else {
            return new FooterViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MomentItemViewHolder) {
            ((MomentItemViewHolder) holder).setView(objectList.get(position));
        } else {
            ((FooterViewHolder) holder).setView(state);
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < objectList.size()) {
            return TYPE.CONTENT.ordinal();
        } else {
            return TYPE.FOOTER.ordinal();
        }
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
        notifyItemChanged(getItemCount() - 1, "");
    }


}
