package com.crazywah.piedpiper.module.discovery.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.discovery.adapter.MomentDetailAdapter;
import com.crazywah.piedpiper.module.discovery.logic.MomentDetailLogic;
import com.crazywah.piedpiper.widget.InputView;
import com.crazywah.piedpiper.widget.NodataView;
import com.crazywah.piedpiper.widget.ProgressingView;
import com.crazywah.piedpiper.widget.TitleBarNormalView;

import java.util.Date;

public class MomentDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "MomentDetailActivity";

    public static final String KEY_ID = "moment_id";

    private MomentDetailLogic logic;

    private TitleBarNormalView titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView detailRv;
    private NodataView nodataView;
    private ProgressingView progressingView;
    private InputView inputView;

    private MomentDetailAdapter adapter;
    private LinearLayoutManager layoutManager;
    private OnRvScrollListener onRvScrollListener = new OnRvScrollListener();

    private int momentId;

    public static void launch(Context context, int momentId) {
        Intent intent = new Intent(context, MomentDetailActivity.class);
        intent.putExtra(KEY_ID, momentId);
        context.startActivity(intent);
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case MomentDetailLogic.MSG_GET_MOMENT_DETAIL_SUCC:
                updateDetail(true);
                break;
            case MomentDetailLogic.MSG_GET_MOMENT_DETAIL_FAIL:
                updateDetail(false);
                break;
            case MomentDetailLogic.MSG_ADD_COMMENT_SUCC:
                progressingView.dismiss();
                inputView.dismiss();
                break;
            case MomentDetailLogic.MSG_ADD_COMMENT_FAIL:
                progressingView.dismiss();
                inputView.dismiss();
                break;
            case MomentDetailLogic.MSG_LIKE_SUCC:
                progressingView.dismiss();
                adapter.getMoment().setIsLiked(1);
                adapter.getMoment().setLikeCount(adapter.getMoment().getLikeCount() + 1);
                adapter.notifyItemChanged(MomentDetailAdapter.INDEX_MOMENT, "");
                break;
            case MomentDetailLogic.MSG_LIKE_FAIL:
                progressingView.dismiss();
                PiedToast.showErrorShort("点赞失败");
                break;
            case MomentDetailLogic.MSG_DISLIKE_SUCC:
                progressingView.dismiss();
                progressingView.dismiss();
                adapter.getMoment().setIsLiked(0);
                adapter.getMoment().setLikeCount(adapter.getMoment().getLikeCount() - 1);
                adapter.notifyItemChanged(MomentDetailAdapter.INDEX_MOMENT, "");
                break;
            case MomentDetailLogic.MSG_DISLIKE_FAIL:
                progressingView.dismiss();
                PiedToast.showErrorShort("取消点赞失败");
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArguments(getIntent());
        logic.load(momentId);
        initView();
        setView();
    }

    private void handleArguments(Intent intent) {
        momentId = intent.getIntExtra(KEY_ID, -1);
    }

    private void initView() {
        titleBar = findViewById(R.id.moment_detail_title);
        refreshLayout = findViewById(R.id.moment_detail_rl);
        detailRv = findViewById(R.id.moment_detail_rv);
        nodataView = findViewById(R.id.moment_detail_no_data_view);
        inputView = findViewById(R.id.moment_detail_floating_input_view);
        progressingView = findViewById(R.id.moment_detail_progressing_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new MomentDetailAdapter(logic.getMomentDetail());
    }

    private void setView() {
        titleBar.setTitle("详情");
        titleBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentDetailActivity.this.finish();
            }
        });
        showNoData(false, "");
        nodataView.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        detailRv.setLayoutManager(layoutManager);
        detailRv.setAdapter(adapter);
        detailRv.addOnScrollListener(onRvScrollListener);
        detailRv.setVisibility(View.GONE);
        inputView.dismiss();
        progressingView.dismiss();
    }

    private void updateDetail(boolean succ) {
        refreshLayout.setRefreshing(false);
        if (succ) {
            adapter.setAll(logic.getMomentDetail());
            adapter.notifyItemMoved(0, 2);
            showNoData(false, "");
            detailRv.setVisibility(View.VISIBLE);
        } else {
            showNoData(true, "访问朋友圈详情失败，点击屏幕重试");
            detailRv.setVisibility(View.GONE);
        }
    }

    public void showNoData(boolean show, String tips) {
        if (show) {
            nodataView.setVisibility(View.VISIBLE);
            nodataView.setTips(tips);
        } else {
            nodataView.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moment_detail;
    }

    @Override
    protected void prepareLogic() {
        logic = new MomentDetailLogic(this, handler);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        logic.load(momentId);
    }

    @Override
    public void onEvent(PiedEvent event) {
        super.onEvent(event);
        switch (event.getType()) {
            case MSG_CLICK_COMMENT:
                postComment(event.getParams());
                break;
            case MSG_CLICK_LIKE:
                if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED && event.getParams() instanceof Moment) {
                    logic.like((Moment) event.getParams());
                    progressingView.show();
                }
                break;
        }
    }

    private void postComment(final Object params) {
        if (params instanceof Moment) {
            inputView.show("请输入评论", new InputView.CallBack() {
                @Override
                public void onFinish(String inputText) {
                    logic.addComment(momentId, null, inputText);
                    progressingView.show();
                }
            });
        } else if (params instanceof Comment) {
            inputView.show("@ " + ((Comment) params).getFromName(), new InputView.CallBack() {
                @Override
                public void onFinish(String inputText) {
                    logic.addComment(momentId, ((Comment) params).getFromId(), inputText);
                    progressingView.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (inputView.isShowing()) {
            inputView.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moment_detail_no_data_view:
                onRefresh();
                break;
        }
    }

    private class OnRvScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        refreshLayout.setEnabled(true);
                    } else {
                        refreshLayout.setEnabled(false);
                    }
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

    }

}
