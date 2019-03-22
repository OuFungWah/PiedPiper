package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BackgroundView extends View {

    private static String lastColor;
    private GradientDrawable drawable = new GradientDrawable();

    public BackgroundView(Context context) {
        super(context);
        init(context);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setColor("#F5F5F5");
    }

    public void setColor(String color){
        drawable.setColor(Color.parseColor(color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = (getWidth() > getHeight() ? getHeight() : getWidth()) / 2;
        drawable.setCornerRadius(radius);
        setBackgroundDrawable(drawable);
        super.onDraw(canvas);
    }
}
