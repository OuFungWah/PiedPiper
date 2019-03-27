package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;

public class TitleBarNormalView extends RelativeLayout {

    private View rootView;

    private View backBtn;
    private TextView titleTv;

    public TitleBarNormalView(Context context) {
        super(context);
        init(context);
    }

    public TitleBarNormalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBarNormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBarNormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_normal_titlebar, null, false);
        backBtn = rootView.findViewById(R.id.normal_titlebar_back_ll);
        titleTv = rootView.findViewById(R.id.normal_titlebar_title_tv);
        rootView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setOnBackClickListener(OnClickListener listener) {
        backBtn.setOnClickListener(listener);
    }

}
