package com.crazywah.piedpiper.module.discovery.widget;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.discovery.activity.MomentDetailActivity;
import com.crazywah.piedpiper.module.discovery.adapter.MomentPagerAdapter;
import com.crazywah.piedpiper.util.DateUtil;
import com.crazywah.piedpiper.util.EmojiUtil;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.widget.CircleIndexLayout;

import org.greenrobot.eventbus.EventBus;

public class MomentItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "MomentItemViewHolder";

    private TextView nameTv;
    private TextView subTv;
    private TextView contentTv;
    private ImageView avatarImg;
    private RelativeLayout momentImgContainer;
    private ViewPager momentVP;
    private CircleIndexLayout circleIndexLayout;
    private TextView postTimeTv;
    private View commentView;
    private View likeView;
    private ImageView likeIconImg;
    private View deleteView;
    private TextView commentCountTv;
    private TextView likeCountTv;

    private Moment moment;

    public MomentItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_post, parent, false));
        nameTv = itemView.findViewById(R.id.item_post_name_tv);
        subTv = itemView.findViewById(R.id.item_post_account_id_tv);
        contentTv = itemView.findViewById(R.id.item_post_content_tv);
        avatarImg = itemView.findViewById(R.id.item_post_avatar_img);
        momentImgContainer = itemView.findViewById(R.id.item_post_img_rl);
        momentVP = itemView.findViewById(R.id.item_post_content_vp);
        circleIndexLayout = itemView.findViewById(R.id.item_post_img_point_cil);
        postTimeTv = itemView.findViewById(R.id.item_post_time_tv);
        commentView = itemView.findViewById(R.id.item_post_comment_ll);
        likeView = itemView.findViewById(R.id.item_post_like_ll);
        likeIconImg = itemView.findViewById(R.id.item_post_like_icon_img);
        deleteView = itemView.findViewById(R.id.item_post_delete_tv);
        commentCountTv = itemView.findViewById(R.id.item_post_comment_count_tv);
        likeCountTv = itemView.findViewById(R.id.item_post_like_count_tv);
        momentVP.setAdapter(new MomentPagerAdapter(itemView.getContext()));
    }

    public void setView(Moment moment) {
        setView(moment, true);
    }

    public void setView(final Moment moment, boolean clickable) {
        if (moment != null) {
            this.moment = moment;
            if (moment.getPicUrls() != null && !moment.getPicUrls().isEmpty()) {
//            if (momentVP.getAdapter() instanceof MomentPagerAdapter) {
//                MomentPagerAdapter adapter = (MomentPagerAdapter) momentVP.getAdapter();
//                adapter.setUrls(moment.getPicUrls());
//                adapter.notifyDataSetChanged();
//            } else {
//                momentVP.setAdapter(new MomentPagerAdapter(itemView.getContext(), moment.getPicUrls()));
//            }
                momentVP.setAdapter(new MomentPagerAdapter(itemView.getContext(), moment.getPicUrls()));
                if (moment.getPicUrls().size() == 1) {
                    circleIndexLayout.setVisibility(View.GONE);
                } else {
                    circleIndexLayout.associateViewPager(momentVP);
                    circleIndexLayout.setVisibility(View.VISIBLE);
                }
                momentImgContainer.setVisibility(View.VISIBLE);
            } else {
                momentImgContainer.setVisibility(View.GONE);
            }
            if (clickable) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MomentDetailActivity.launch(itemView.getContext(), moment.getMomentId());
                    }
                });
            }
            likeIconImg.setImageDrawable(itemView.getResources().getDrawable(moment.getIsLiked() == 0 ? R.drawable.ic_like : R.drawable.ic_liked));
            nameTv.setText(moment.getNickname());
            subTv.setText("@" + moment.getAccountId());
            contentTv.setText(EmojiUtil.emojiRecovery(moment.getPostContent()));
            ImageLoader.loadCircle(moment.getAvatar(), avatarImg);
            likeCountTv.setText("" + moment.getLikeCount());
            commentCountTv.setText("" + moment.getCommentCount());
            postTimeTv.setText("" + DateUtil.formatYMDHMS(moment.getPostTime()));
            if (moment.getAccountId().equals(PiedPiperApplication.getLoginUser().getAccountId())) {
                deleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteDialog.show(itemView.getContext(), moment.getMomentId());
                    }
                });
                deleteView.setVisibility(View.VISIBLE);
            } else {
                deleteView.setVisibility(View.GONE);
            }
            commentView.setOnClickListener(this);
            likeView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        PiedEvent event = null;
        switch (v.getId()) {
            case R.id.item_post_comment_ll:
                event = new PiedEvent(PiedEvent.EventType.MSG_CLICK_COMMENT);
                break;
            case R.id.item_post_like_ll:
                event = new PiedEvent(PiedEvent.EventType.MSG_CLICK_LIKE);
                break;
        }
        if (event != null) {
            event.setParams(moment);
            EventBus.getDefault().post(event);
        }
    }
}
