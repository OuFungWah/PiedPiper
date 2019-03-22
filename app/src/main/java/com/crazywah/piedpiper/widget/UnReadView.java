package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.util.DensityUtils;

public class UnReadView extends RelativeLayout {

    private TextView textView;
    private View bgView;

    public UnReadView(Context context) {
        super(context);
        init(context);
    }

    public UnReadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UnReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UnReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        textView = new TextView(context);
        bgView = new View(context);
        addView(bgView);
        addView(textView);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        int radius = (getWidth() < getHeight() ? getWidth() / 2 : getHeight() / 2);
        setForeground(Color.RED, radius*3/4);
        setBackground(Color.WHITE, radius);
    }

    public void setForeground(int color, int radius) {
        setBg(textView, color, radius);
    }

    public void setBackground(int color, int radius) {
        setBg(bgView, color, radius);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void show(String text){
        textView.setText(text);
        setVisibility(VISIBLE);
    }

    public void show(String text, int tColor, int bColor, int fColor, int bRadius, int fRadius) {
        textView.setText(text);
        textView.setTextColor(tColor);
        setForeground(fColor, fRadius);
        setBackground(bColor, bRadius);
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void updateText(String text) {
        textView.setText(text);
    }

    private void setBg(View view, int color, int radius) {
        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(DensityUtils.dp2px(radius));
        bg.setColor(color);
        bg.setSize(DensityUtils.dp2px(2 * radius), DensityUtils.dp2px(2 * radius));
        view.setBackgroundDrawable(bg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = 2 * radius;
        layoutParams.height = 2 * radius;
        layoutParams.addRule(CENTER_IN_PARENT);
    }

}
