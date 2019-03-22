package com.crazywah.piedpiper.module.contact.bean;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazywah.piedpiper.R;

public class ContactDividerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public ContactDividerViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_contact_divider, parent, false));
        textView = itemView.findViewById(R.id.contact_divider_title_tv);
    }

    public void setView(String title) {
        if (TextUtils.isEmpty(title)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
    }

}
