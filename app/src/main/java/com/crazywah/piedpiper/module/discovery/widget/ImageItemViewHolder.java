package com.crazywah.piedpiper.module.discovery.widget;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crazywah.photo_viewer.PhotoViewerActivity;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.util.DensityUtils;

import org.greenrobot.eventbus.EventBus;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private View deleteView;

    public ImageItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_image, parent, false));
        imageView = itemView.findViewById(R.id.item_image_img);
        deleteView = itemView.findViewById(R.id.item_image_delete_rl);
    }

    public void setView(final Bitmap bitmap, final Uri source) {
        if (bitmap != null) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(itemView.getResources(), bitmap);
            roundedBitmapDrawable.setCornerRadius(DensityUtils.dp2px(10));
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_REMOVE_POSTING_PHOTO);
                event.setParams(getAdapterPosition());
                EventBus.getDefault().post(event);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source != null) {
                    PhotoViewerActivity.launch(source, itemView.getContext());
                } else {
                    PiedToast.showShort("查看图片异常");
                }
            }
        });
    }

}
