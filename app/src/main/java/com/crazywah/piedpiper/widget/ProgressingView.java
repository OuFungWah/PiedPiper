package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.crazywah.piedpiper.R;

public class ProgressingView extends RelativeLayout implements View.OnClickListener {

    private View rootView;

    public ProgressingView(Context context) {
        super(context);
        init(context);
    }

    public ProgressingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_progressing, null, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(rootView);
        setOnClickListener(this);
    }

    public void show() {
        this.setVisibility(VISIBLE);
    }

    public void dismiss() {
        this.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {

    }
}
