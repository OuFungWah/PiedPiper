package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.crazywah.piedpiper.util.DensityUtils;

public class CircleIndexLayout extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "CircleIndexLayout";

    private int radiu = DensityUtils.dp2px(4);

    private LinearLayout container;
    private View curCircleV;
    private ViewPager viewPager;

    public CircleIndexLayout(Context context) {
        super(context);
        init(context);
    }

    public CircleIndexLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleIndexLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleIndexLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        buildContainer(context);
        for (int i = 0; i < 9; i++) {
            addCircle(context);
        }

        curCircleV = buildCircle(context, radiu, Color.WHITE, true);
        curCircleV.setPivotX(container.getChildAt(0).getPivotX());
        curCircleV.setPivotY(container.getChildAt(0).getPivotY());
        addView(curCircleV);
    }

    private void buildContainer(Context context) {
        container = new LinearLayout(context);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setGravity(Gravity.CENTER);
        container.setPadding(0, radiu, 0, radiu);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radiu * 2);
        drawable.setColor(Color.parseColor("#753C3F41"));
        container.setBackgroundDrawable(drawable);
        addView(container);
    }

    private void addCircle(Context context) {
        View view = buildCircle(context, radiu, Color.parseColor("#75757575"), false);
        view.setOnClickListener(this);
        container.addView(view);
    }

    public View buildCircle(Context context, int radiu, int argb, boolean isSelected) {
        View view = new View(context);
        ViewGroup.LayoutParams params;
        if (!isSelected) {
            params = new LinearLayout.LayoutParams(radiu * 2, radiu * 2);
            ((LinearLayout.LayoutParams) params).setMargins(radiu, 0, radiu, 0);
        } else {
            params = new RelativeLayout.LayoutParams(radiu * 2, radiu * 2);
            ((RelativeLayout.LayoutParams) params).addRule(CENTER_VERTICAL, TRUE);
            ((RelativeLayout.LayoutParams) params).setMargins(radiu, radiu, radiu, radiu);
        }
        view.setLayoutParams(params);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(radiu);
        gradientDrawable.setColor(argb);
        view.setBackgroundDrawable(gradientDrawable);
        return view;
    }

    public void setRadiu(int dp) {
        radiu = DensityUtils.dp2px(dp);
    }

    public void associateViewPager(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(new CircleOnScrollListener());
        if (container == null) {
            buildContainer(getContext());
        }
        if (viewPager.getAdapter() != null) {
            int offset = viewPager.getAdapter().getCount() - container.getChildCount();
            if (offset > 0) {
                for (int i = 0; i < offset; i++) {
                    addCircle(getContext());
                }
            } else if (offset < 0) {
                for (int i = offset; i < 0; i++) {
                    container.removeViewAt(container.getChildCount() - 1);
                }
            }
        }
        curCircleV.setX(container.getChildAt(0).getX());
        select(0);
    }

    @Override
    public void onClick(View v) {
        select(v);
    }

    public void select(View view) {
        select(container.indexOfChild(view));
    }

    public void select(int position) {
        if (position >= 0 && position < container.getChildCount() && viewPager == null) {
            curCircleV.setX(container.getChildAt(position).getX());
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }
    }

    private class CircleOnScrollListener implements ViewPager.OnPageChangeListener {

        private float lastV = -1;

        @Override
        public void onPageScrolled(int i, float v, int i1) {
//            Log.d(TAG, "onPageScrolled: i = " + i + "; v = " + v + "; i1 = " + i1);
            if (lastV >= 0) {
                float curX = curCircleV.getX();
                if (lastV > v) {
                    //右滑
                    curX = container.getChildAt(i - 1).getX() + (v * radiu * 4);
                } else if (lastV < v) {
                    //左滑
                    curX = container.getChildAt(i).getX() + (v * radiu * 4);
                }
                curCircleV.setX(curX);
            } else {
                lastV = v;
            }
        }

        @Override
        public void onPageSelected(int i) {
            select(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
