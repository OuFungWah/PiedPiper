package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.DensityUtils;

public class PiedToastView extends RelativeLayout {

    private View rootView;
    private TextView contentTv;

    public PiedToastView(Context context) {
        super(context);
        init();
    }

    public PiedToastView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PiedToastView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PiedToastView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.toast_normal,this,false);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)rootView.getLayoutParams();
        layoutParams.addRule(CENTER_IN_PARENT);
        rootView.setLayoutParams(layoutParams);
        addView(rootView);
        contentTv = findViewById(R.id.toast_text);
    }

    public void setText(String content){
        contentTv.setText(content);
    }

    public void setTextColor(String color){
        contentTv.setTextColor(Color.parseColor(color));
    }

    public void setBackground(String color){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(DensityUtils.dp2px(5));
        gd.setColor(Color.parseColor(color));
        setBackgroundDrawable(gd);
    }

    public void setBackground(int color){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(DensityUtils.dp2px(5));
        gd.setColor(color);
        setBackgroundDrawable(gd);
    }

}
