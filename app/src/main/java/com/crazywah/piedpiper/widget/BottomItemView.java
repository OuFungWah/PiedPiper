package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.DensityUtils;

public class BottomItemView extends RelativeLayout {

    private int checkedImgRes = R.drawable.logo_grey;
    private int unCheckedImgRes = R.drawable.logo_grey;

    private ImageView iconImg;
    private UnReadView unReadView;
    private boolean isChecked = false;

    public BottomItemView(Context context) {
        super(context);
        init(context);
    }

    public BottomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        iconImg = new ImageView(context);
        LayoutParams iconImgParams = new LayoutParams(DensityUtils.dp2px(25), DensityUtils.dp2px(25));
        iconImgParams.addRule(CENTER_IN_PARENT);
        iconImg.setLayoutParams(iconImgParams);
        iconImg.setImageDrawable(getResources().getDrawable(checkedImgRes));
        addView(iconImg);
        unReadView = new UnReadView(context);
        LayoutParams unReadParams = new LayoutParams(DensityUtils.dp2px(22),DensityUtils.dp2px(22));
        unReadParams.addRule(ALIGN_PARENT_RIGHT);
        unReadView.setLayoutParams(unReadParams);
        addView(unReadView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getLayoutParams().width = DensityUtils.dp2px(47);
        getLayoutParams().height = DensityUtils.dp2px(47);

        if (getLayoutParams() instanceof LayoutParams) {
            LayoutParams parenParams = (LayoutParams) getLayoutParams();
            parenParams.addRule(CENTER_IN_PARENT);
            parenParams.setMargins(DensityUtils.dp2px(15), 0, DensityUtils.dp2px(15), 0);
        } else if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) getLayoutParams()).setMargins(DensityUtils.dp2px(15), 0, DensityUtils.dp2px(15), 0);
        }
    }

    public void setRes(int checkedRes, int unCheckedRes) {
        checkedImgRes = checkedRes;
        unCheckedImgRes = unCheckedRes;
        check(false);
    }

    public void check() {
        isChecked = !isChecked;
        check(isChecked);
    }

    public void check(boolean checked) {
        if (checked) {
            iconImg.setImageDrawable(getResources().getDrawable(checkedImgRes));
        } else {
            iconImg.setImageDrawable(getResources().getDrawable(unCheckedImgRes));
        }
    }

    public void showRedUnRead(String text) {
        unReadView.show(text, Color.WHITE, Color.WHITE, Color.parseColor("#d63c41"), DensityUtils.dp2px(11), DensityUtils.dp2px(8));
    }

    public void showGreenUnRead(String text) {
        unReadView.show(text, Color.parseColor("#79CD59"), Color.WHITE, Color.parseColor("#E2F7D8"), DensityUtils.dp2px(11), DensityUtils.dp2px(8));
    }

    public void hideUnRead() {
        unReadView.hide();
    }

    public boolean isChecked() {
        return isChecked;
    }
}
