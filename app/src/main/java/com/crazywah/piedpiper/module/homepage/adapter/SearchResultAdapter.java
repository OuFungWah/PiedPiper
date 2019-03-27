package com.crazywah.piedpiper.module.homepage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.base.BaseAdapter;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.widget.NormalEntranceViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends BaseAdapter {

    private List<User> users = new ArrayList<>();

    public SearchResultAdapter(List<User> users) {
        setUsers(users);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NormalEntranceViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalEntranceViewHolder) {
            ((NormalEntranceViewHolder) holder).setView(users.get(position), checkIsSameNext(position));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        if (users != null && !users.isEmpty()) {
            if (!this.users.isEmpty()) {
                this.users.clear();
            }
            this.users = users;
        }
    }
}
