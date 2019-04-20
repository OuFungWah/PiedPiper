package com.crazywah.piedpiper.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;

public class ImageLoader {

    public static void load(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Glide.with(imageView.getContext()).load(uri).into(imageView);
        }
    }

    public static void loadCompress(int quality, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            RequestOptions mRequestOptions = RequestOptions.encodeQualityOf(quality);
            Glide.with(imageView.getContext()).load(uri).apply(mRequestOptions).into(imageView);
        }
    }

    public static void loadUserAvatar(@NonNull User user, @NonNull ImageView iconImg) {
        ImageLoader.loadCircle(user.getAvatar(), iconImg, user.getGender() == 0 ? R.drawable.boy : user.getGender() == 1 ? R.drawable.girl : R.drawable.users);
    }

    public static void loadCircle(String url, ImageView imageView) {
        loadCircle(url, imageView, R.drawable.logo_grey);
    }

    public static void loadCircle(String url, ImageView imageView, int defRes) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//不做磁盘缓存
                    .skipMemoryCache(false);//不做内存缓存
            Glide.with(imageView.getContext()).load(uri).apply(mRequestOptions).into(imageView);
        } else {
            imageView.setImageDrawable(PiedPiperApplication.getInstance().getResources().getDrawable(defRes));
        }
    }

}
