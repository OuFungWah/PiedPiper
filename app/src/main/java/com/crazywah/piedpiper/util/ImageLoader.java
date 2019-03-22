package com.crazywah.piedpiper.util;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void load(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Glide.with(imageView.getContext()).load(uri).into(imageView);
        }
    }

    public static void loadCircle(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//不做磁盘缓存
                    .skipMemoryCache(false);//不做内存缓存
            Glide.with(imageView.getContext()).load(uri).apply(mRequestOptions).into(imageView);
        }
    }

}
