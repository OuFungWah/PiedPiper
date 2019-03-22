package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.ImageLoader;

public class TitleBarView extends RelativeLayout implements View.OnClickListener {

    public static final int CLICK_AVATAR = 0;
    public static final int CLICK_TITLE = 1;
    public static final int CLICK_ONE = 2;
    public static final int CLICK_TWO = 3;

    private View rootView;
    private ImageView avatarImg;
    private TextView titleTv;
    private ImageView oneImg;
    private ImageView twoImg;
    private RelativeLayout oneRl;
    private RelativeLayout twoRl;

    private OnTitleBarClickListener onTitleBarClickListener;

    public TitleBarView(Context context) {
        super(context);
        init(context);
    }

    public TitleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.titlebar_view, this, false);
        avatarImg = rootView.findViewById(R.id.titlebar_avatar_img);
        titleTv = rootView.findViewById(R.id.titlebar_title_tv);
        oneImg = rootView.findViewById(R.id.titlebar_one_img);
        twoImg = rootView.findViewById(R.id.titlebar_two_img);
        oneRl = rootView.findViewById(R.id.titlebar_one_rl);
        twoRl = rootView.findViewById(R.id.titlebar_two_rl);

        addView(rootView);

        avatarImg.setOnClickListener(this);
        titleTv.setOnClickListener(this);
        oneRl.setOnClickListener(this);
        twoRl.setOnClickListener(this);
    }

    public void setTitle(String text) {
        titleTv.setText(text);
    }

    public void setAvatarImg(int avatarRes) {
        avatarImg.setImageDrawable(getResources().getDrawable(avatarRes));
    }

    public void setAvatarImg(Bitmap bitmap) {
        avatarImg.setImageBitmap(bitmap);
    }

    public void setOneImg(int iconRes) {
        oneImg.setImageDrawable(getResources().getDrawable(iconRes));
        oneRl.setVisibility(VISIBLE);
    }

    public void setOneImg(Bitmap bitmap) {
        oneImg.setImageBitmap(bitmap);
        oneRl.setVisibility(VISIBLE);
    }

    public void setTwoImg(int iconRes) {
        twoImg.setImageDrawable(getResources().getDrawable(iconRes));
        twoRl.setVisibility(VISIBLE);
    }

    public void setAvatarImg(String url) {
        ImageLoader.loadCircle(url, avatarImg);
    }

    @Override
    public void onClick(View v) {
        if (onTitleBarClickListener != null) {
            switch (v.getId()) {
                case R.id.titlebar_avatar_img:
                    onTitleBarClickListener.onTitleClick(CLICK_AVATAR);
                    break;
                case R.id.titlebar_title_tv:
                    onTitleBarClickListener.onTitleClick(CLICK_TITLE);
                    break;
                case R.id.titlebar_one_rl:
                    onTitleBarClickListener.onTitleClick(CLICK_ONE);
                    break;
                case R.id.titlebar_two_rl:
                    onTitleBarClickListener.onTitleClick(CLICK_TWO);
                    break;
                default:
                    break;
            }
        }
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return onTitleBarClickListener;
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    public interface OnTitleBarClickListener {
        void onTitleClick(int itemType);
    }

}
