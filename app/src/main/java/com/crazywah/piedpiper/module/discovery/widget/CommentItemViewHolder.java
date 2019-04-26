package com.crazywah.piedpiper.module.discovery.widget;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.DateUtil;
import com.crazywah.piedpiper.util.ImageLoader;

import org.greenrobot.eventbus.EventBus;

public class CommentItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView avatarImg;
    private TextView nameTv;
    private TextView contentTv;
    private TextView dateTv;
    private View divider;

    private Comment comment;

    public CommentItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_comment, parent, false));
        avatarImg = itemView.findViewById(R.id.item_comment_avatar_img);
        nameTv = itemView.findViewById(R.id.item_comment_name_tv);
        contentTv = itemView.findViewById(R.id.item_comment_content_tv);
        dateTv = itemView.findViewById(R.id.item_comment_time_tv);
        divider = itemView.findViewById(R.id.item_comment_divider);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_CLICK_COMMENT);
                event.setParams(comment);
                EventBus.getDefault().post(event);
            }
        });
    }

    public void setView(Comment comment, boolean isSame) {
        this.comment = comment;
        ImageLoader.loadCircle(comment.getAvatar(), avatarImg, R.drawable.users);
        avatarImg.setOnClickListener(this);
        combineText(comment);
        contentTv.setText(comment.getContent());
        dateTv.setText(DateUtil.formatYMDHMS(comment.getCommentTime()));
        divider.setVisibility(isSame ? View.VISIBLE : View.GONE);
    }

    private void combineText(final Comment comment) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(comment.getFromName());
        if (!TextUtils.isEmpty(comment.getToName())) {
            spannableString.append(" @ ");
            spannableString.append(comment.getToName());
        }
        spannableString.append(":");

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#212121"));
        ClickableSpan fromClickableSpan = new ClickableSpanImpl(comment.getFromId());
        ClickableSpan toClickableSpan = new ClickableSpanImpl(comment.getToId());
        spannableString.setSpan(fromClickableSpan, 0, comment.getFromName().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(colorSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        if (!TextUtils.isEmpty(comment.getToName())) {
            spannableString.setSpan(colorSpan, comment.getFromName().length(), comment.getFromName().length() - 1 + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableString.setSpan(toClickableSpan, comment.getFromName().length() + 3, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        nameTv.setText(spannableString);
        nameTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_comment_avatar_img:
                break;
        }
    }

    static class ClickableSpanImpl extends ClickableSpan {

        private String accountId;

        public ClickableSpanImpl(String accountId) {
            this.accountId = accountId;
        }

        @Override
        public void onClick(View widget) {
            UserInfoActivity.launch(widget.getContext(), accountId);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

}
