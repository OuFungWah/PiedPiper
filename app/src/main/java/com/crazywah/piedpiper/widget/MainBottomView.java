package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainBottomView extends LinearLayout implements View.OnClickListener {

    public static final int INDEX_CHAT = 0;
    public static final int INDEX_CONTACT = 1;
    public static final int INDEX_DISCOVERY = 2;

    private static final int[] ICON_UNCHECKED_RES = new int[]{
            R.drawable.ic_chat_grey,
            R.drawable.ic_contact_grey_400_48dp,
            R.drawable.ic_explore_grey
    };
    private static final int[] ICON_CHECKED_RES = new int[]{
            R.drawable.ic_chat_black_48dp,
            R.drawable.ic_contact_black_48dp,
            R.drawable.ic_explore_black_48dp
    };

    private List<BottomItemView> itemList = new ArrayList<>();
    private OnItemCheckListener itemCheckListener = null;

    public MainBottomView(Context context) {
        super(context);
        init(context);
    }

    public MainBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        itemList.add(new BottomItemView(context));
        itemList.add(new BottomItemView(context));
        itemList.add(new BottomItemView(context));
        for (int i = 0; i < itemList.size(); i++) {
            BottomItemView itemView = itemList.get(i);
            itemView.setRes(ICON_CHECKED_RES[i], ICON_UNCHECKED_RES[i]);
            itemView.setTag(i);
            itemView.setOnClickListener(this);
            addView(itemView);
        }
        check(0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getLayoutParams().width = LayoutParams.MATCH_PARENT;
        getLayoutParams().height = DensityUtils.dp2px(52);
    }

    public void check(int index) {
        for (int i = 0; i < itemList.size(); i++) {
            BottomItemView itemView = itemList.get(i);
            itemView.check(i == index);
        }
    }

    public void showRedUnRead(int index, String text) {
        itemList.get(index).showRedUnRead(text);
    }

    public void showGreenUnRead(int index, String text) {
        itemList.get(index).showGreenUnRead(text);
    }

    public void hideUnRead(int index){
        itemList.get(index).hideUnRead();
    }

    @Override
    public void onClick(View v) {
        check((int) v.getTag());
        if (itemCheckListener != null) {
            itemCheckListener.onItemCheck((int) v.getTag());
        }
    }

    public OnItemCheckListener getItemCheckListener() {
        return itemCheckListener;
    }

    public void setItemCheckListener(OnItemCheckListener itemCheckListener) {
        this.itemCheckListener = itemCheckListener;
    }

    public interface OnItemCheckListener {
        void onItemCheck(int index);
    }

}
