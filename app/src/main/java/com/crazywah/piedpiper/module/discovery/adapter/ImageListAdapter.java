package com.crazywah.piedpiper.module.discovery.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.crazywah.piedpiper.module.discovery.bean.ItemAddPicBean;
import com.crazywah.piedpiper.module.discovery.widget.AddImageViewHolder;
import com.crazywah.piedpiper.module.discovery.widget.ImageItemViewHolder;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum TYPE {
        PIC,
        ADD
    }

    private List<Object> bitmapList;
    private List<Uri> sourceList;

    public ImageListAdapter(List<Object> bitmapList, List<Uri> sourceList) {
        this.bitmapList = bitmapList;
        this.sourceList = sourceList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE.PIC.ordinal()) {
            return new ImageItemViewHolder(parent);
        } else {
            return new AddImageViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE.PIC.ordinal()) {
            ((ImageItemViewHolder) holder).setView((Bitmap) bitmapList.get(position), sourceList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (bitmapList.get(position) instanceof Bitmap) {
            return TYPE.PIC.ordinal();
        } else if (bitmapList.get(position) instanceof ItemAddPicBean) {
            return TYPE.ADD.ordinal();
        }
        return super.getItemViewType(position);
    }

    public List<Uri> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Uri> sourceList) {
        this.sourceList = sourceList;
    }
}
