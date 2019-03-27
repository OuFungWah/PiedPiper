package com.crazywah.piedpiper.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.bean.NormalEntrance;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.DensityUtils;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.widget.BackgroundView;
import com.crazywah.piedpiper.widget.UnReadView;

public class NormalEntranceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView iconImg;
    private TextView titleTv;
    private TextView contentTv;
    private UnReadView unReadView;
    private BackgroundView backgroundView;
    private View entranceImg;
    private View subView;
    private View divider;

    private String userId;

    public NormalEntranceViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_contact, parent, false));
        iconImg = itemView.findViewById(R.id.contact_head_icon);
        titleTv = itemView.findViewById(R.id.contact_head_title_tv);
        contentTv = itemView.findViewById(R.id.contact_head_content_tv);
        unReadView = itemView.findViewById(R.id.contact_head_unread);
        divider = itemView.findViewById(R.id.contact_head_divider);
        subView = itemView.findViewById(R.id.contact_head_sub_ll);
        backgroundView = itemView.findViewById(R.id.contact_head_icon_bg);
        entranceImg = itemView.findViewById(R.id.contact_head_entrance_img);
    }

    public void setView(User user) {
        setView(user, true);
    }

    public void setView(User user, boolean showDivider) {
        userId = user.getAccountId();
        ImageLoader.loadUserAvatar(user, iconImg);
        titleTv.setText(user.getName());
        subView.setVisibility(View.GONE);
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
        backgroundView.setVisibility(View.GONE);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userId)) {
                    UserInfoActivity.launch(itemView.getContext(), userId);
                } else {
                    PiedToast.showShort("缺少用户ID");
                }
            }
        });
    }

    public void setView(NormalEntrance entrance) {
        setView(entrance, true);
    }

    public void setView(final NormalEntrance entrance, boolean showDivider) {
        setView(entrance, showDivider, true);
    }

    public void setView(final NormalEntrance entrance, boolean showDivider, boolean canClick) {
        iconImg.setImageBitmap(entrance.getIconBm());

        backgroundView.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(entrance.getBackgroundColor())) {
            backgroundView.setColor(entrance.getBackgroundColor());
        } else {
            backgroundView.setColor(NormalEntrance.getColor());
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iconImg.getLayoutParams();
        int margin = 8;
        params.setMargins(DensityUtils.dp2px(margin), DensityUtils.dp2px(margin), DensityUtils.dp2px(margin), DensityUtils.dp2px(margin));
        iconImg.setLayoutParams(params);

        if (!TextUtils.isEmpty(entrance.getTitle())) {
            titleTv.setText(entrance.getTitle());
        }
        if (!TextUtils.isEmpty(entrance.getContent())) {
            contentTv.setText(entrance.getContent());
        } else {
            contentTv.setVisibility(View.GONE);
        }
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
        if (canClick) {
            entranceImg.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entrance.getCallBack().onEnter(entrance);
                }
            });
        } else {
            entranceImg.setVisibility(View.GONE);
        }
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(entrance.getContent());
                PiedToast.showShort("已复制到粘贴板");
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
