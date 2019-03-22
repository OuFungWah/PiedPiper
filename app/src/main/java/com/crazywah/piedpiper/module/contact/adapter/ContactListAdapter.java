package com.crazywah.piedpiper.module.contact.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.contact.bean.ContactDividerViewHolder;
import com.crazywah.piedpiper.bean.NormalEntrance;
import com.crazywah.piedpiper.widget.NormalEntranceViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum TYPE {
        TYPE_ENTRANCE,
        TYPE_USER,
        TYPE_DIVIDER
    }

    private List<Object> list = new ArrayList<>();

    public ContactListAdapter(List<? extends Object> list) {
        setList(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.TYPE_ENTRANCE.ordinal() || viewType == TYPE.TYPE_USER.ordinal()) {
            return new NormalEntranceViewHolder(parent);
        } else if (viewType == TYPE.TYPE_DIVIDER.ordinal()) {
            return new ContactDividerViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (list.get(position) instanceof NormalEntrance) {
            ((NormalEntranceViewHolder) holder).setView(((NormalEntrance) list.get(position)), checkShouldShowDivider(position));
        } else if (list.get(position) instanceof User) {
            ((NormalEntranceViewHolder) holder).setView(((User) list.get(position)), checkShouldShowDivider(position));
        } else if (list.get(position) instanceof String) {
            ((ContactDividerViewHolder) holder).setView(((String) list.get(position)));
        }
    }

    private boolean checkShouldShowDivider(int position) {
        if (position + 1 >= list.size()) {
            return false;
        } else {
            return getItemViewType(position) == getItemViewType(position + 1);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof User) {
            return TYPE.TYPE_USER.ordinal();
        } else if (list.get(position) instanceof NormalEntrance) {
            return TYPE.TYPE_ENTRANCE.ordinal();
        } else if (list.get(position) instanceof String) {
            return TYPE.TYPE_DIVIDER.ordinal();
        }
        return super.getItemViewType(position);
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<? extends Object> list) {
        if (list != null && !list.isEmpty()) {
            if (!this.list.isEmpty()) {
                this.list.clear();
            }
            this.list.addAll(list);
        }
    }
}
