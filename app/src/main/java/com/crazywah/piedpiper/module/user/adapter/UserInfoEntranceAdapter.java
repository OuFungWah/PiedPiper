package com.crazywah.piedpiper.module.user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.base.BaseAdapter;
import com.crazywah.piedpiper.bean.NormalEntrance;
import com.crazywah.piedpiper.widget.NormalEntranceViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserInfoEntranceAdapter extends BaseAdapter {

    private enum TYPE {
        ENTRANCE,
        OTHER
    }

    private List<Object> objectList = new ArrayList<>();

    public UserInfoEntranceAdapter(List<Object> objectList) {
        setObjectList(objectList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.ENTRANCE.ordinal()) {
            return new NormalEntranceViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalEntranceViewHolder) {
            ((NormalEntranceViewHolder) holder).setView(((NormalEntrance) objectList.get(position)), checkIsSameNext(position), ((NormalEntrance) objectList.get(position)).isCanClick());
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof NormalEntrance) {
            return TYPE.ENTRANCE.ordinal();
        }
        return TYPE.OTHER.ordinal();
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        if (objectList != null && !objectList.isEmpty()) {
            if (!this.objectList.isEmpty()) {
                this.objectList.clear();
            }
            this.objectList.addAll(objectList);
        }
    }
}
