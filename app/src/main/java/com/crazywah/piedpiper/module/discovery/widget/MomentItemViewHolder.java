package com.crazywah.piedpiper.module.discovery.widget;

import android.app.Application;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.module.discovery.adapter.MomentPagerAdapter;
import com.crazywah.piedpiper.util.DateUtil;
import com.crazywah.piedpiper.util.EmojiUtil;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.widget.CircleIndexLayout;

public class MomentItemViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "MomentItemViewHolder";

    private TextView nameTv;
    private TextView subTv;
    private TextView contentTv;
    private ImageView avatarImg;
    private RelativeLayout momentImgContainer;
    private ViewPager momentVP;
    private CircleIndexLayout circleIndexLayout;
    private TextView postTimeTv;
    private View CommentView;
    private View likeView;
    private View deleteView;
    private TextView commentCountTv;
    private TextView likeCountTv;


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
        CommentView = itemView.findViewById(R.id.item_post_comment_ll);
        likeView = itemView.findViewById(R.id.item_post_like_ll);
        deleteView = itemView.findViewById(R.id.item_post_delete_tv);
        commentCountTv = itemView.findViewById(R.id.item_post_comment_count_tv);
        likeCountTv = itemView.findViewById(R.id.item_post_like_count_tv);
        momentVP.setAdapter(new MomentPagerAdapter(itemView.getContext()));
    }

    public void setView(final Moment moment) {
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

    }

}
