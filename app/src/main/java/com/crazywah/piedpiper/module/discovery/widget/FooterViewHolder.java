package com.crazywah.piedpiper.module.discovery.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crazywah.piedpiper.R;

public class FooterViewHolder extends RecyclerView.ViewHolder {

    public enum STATE {
        NORMAL,
        LOADING,
        NO_MORE
    }

    private TextView tipsTv;
    private ProgressBar progressBar;

    public FooterViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_footer, parent, false));
        tipsTv = itemView.findViewById(R.id.view_footer_tv);
        progressBar = itemView.findViewById(R.id.view_footer_pb);
    }

    public void setView(STATE state) {
        switch (state) {
            case NORMAL:
                tipsTv.setText("加载更多");
                progressBar.setVisibility(View.GONE);
                break;
            case LOADING:
                tipsTv.setText("加载中...");
                progressBar.setVisibility(View.VISIBLE);
                break;
            case NO_MORE:
                tipsTv.setText("已经到底了");
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

}
