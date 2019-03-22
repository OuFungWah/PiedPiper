package com.crazywah.piedpiper.module.chat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.module.chat.bean.RecentContactViewHolder;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

public class RecentContactAdapter extends RecyclerView.Adapter<RecentContactViewHolder> {

    private List<RecentContact> recentContacts;

    public RecentContactAdapter(List<RecentContact> recentContacts) {
        this.recentContacts = recentContacts;
    }

    @NonNull
    @Override
    public RecentContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_recent_contact,parent,false);
        return new RecentContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentContactViewHolder holder, int position) {
        holder.updateView(recentContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return recentContacts.size();
    }
}
