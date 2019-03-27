package com.crazywah.piedpiper.module.contact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.base.BaseAdapter;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.contact.bean.FriendRequestViewHolder;
import com.crazywah.piedpiper.module.contact.logic.FriendRequestLogic;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestListAdapter extends BaseAdapter {

    private List<User> userList = new ArrayList<>();
    private FriendRequestLogic logic;

    public FriendRequestListAdapter(FriendRequestLogic logic, List<User> userList) {
        setUserList(userList);
        this.logic = logic;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendRequestViewHolder(parent, logic);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendRequestViewHolder) {
            ((FriendRequestViewHolder) holder).setView(userList.get(position), checkIsSameNext(position));
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        if (userList != null && !userList.isEmpty()) {
            this.userList.clear();
            this.userList.addAll(userList);
        }
    }


}
