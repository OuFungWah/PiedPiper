package com.crazywah.piedpiper.module.discovery.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.discovery.logic.PostMomentLogic;

import org.greenrobot.eventbus.EventBus;

public class AddImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public AddImageViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_add_pic, parent, false));
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_REQUEST_PHOTO));
    }
}
