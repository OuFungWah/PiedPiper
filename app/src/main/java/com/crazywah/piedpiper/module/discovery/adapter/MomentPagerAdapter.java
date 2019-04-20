package com.crazywah.piedpiper.module.discovery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crazywah.photo_viewer.PhotoViewerActivity;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MomentPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private Context context;

    public MomentPagerAdapter(Context context) {
        this.context = context;
        combineImg();
    }

    public MomentPagerAdapter(Context context, List<String> urls) {
        this.context = context;
        setUrls(urls);
        combineImg();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 回收图片
        //移除页面
        imageViewList.get(position).setImageDrawable(null);
        imageViewList.get(position).setImageBitmap(null);
        container.removeView(imageViewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // 图片压缩
        // 加载图片
        // 加载页面
        imageViewList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewerActivity.launch((String) v.getTag(R.id.viewholder_image_tag), context);
            }
        });
        ImageLoader.loadCompress(75, (String) imageViewList.get(position).getTag(R.id.viewholder_image_tag), imageViewList.get(position));
        container.addView(imageViewList.get(position));
        return imageViewList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void notifyDataSetChanged() {
        combineImg();
        super.notifyDataSetChanged();
    }

    private void combineImg() {
        if (!imageViewList.isEmpty()) {
            imageViewList.clear();
        }
        for (String url : urls) {
            ImageView imageView = new ImageView(context);
            imageView.setTag(R.id.viewholder_image_tag, url);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageViewList.add(imageView);
        }
    }

    public void setUrls(List<String> urls) {
        if (this.urls != null) {
            if (!this.urls.isEmpty()) {
                this.urls.clear();
            }
            this.urls.addAll(urls);
        }
    }

}
