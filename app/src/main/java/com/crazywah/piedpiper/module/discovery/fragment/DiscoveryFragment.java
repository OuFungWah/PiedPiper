package com.crazywah.piedpiper.module.discovery.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseFragment;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.discovery.adapter.DiscoveryMomentAdapter;
import com.crazywah.piedpiper.module.discovery.widget.FooterViewHolder.STATE;
import com.crazywah.piedpiper.module.discovery.logic.DiscoveryLogic;
import com.crazywah.piedpiper.module.discovery.widget.DeleteDialog;
import com.crazywah.piedpiper.widget.NodataView;
import com.crazywah.piedpiper.widget.ProgressingView;

public class DiscoveryFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "DiscoveryFragment";

    private DiscoveryLogic logic;

    private View rootView;
    private ProgressingView progressingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView momentRv;
    private NodataView nodataView;
    private DiscoveryMomentAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragmengt = new DiscoveryFragment();
        return fragmengt;
    }

    public DiscoveryFragment() {
    }

    @Override
    protected void prepareLogic() {
        logic = new DiscoveryLogic(getContext(), handler);
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case DiscoveryLogic.MSG_LOAD_SUCC:
                refreshLayout.setRefreshing(false);
                nodataView.setVisibility(View.GONE);
                momentRv.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                adapter.setState(STATE.NORMAL);
                if (logic.getCurPage() == 0) {
                    momentRv.smoothScrollToPosition(0);
                }
                break;
            case DiscoveryLogic.MSG_LOAD_NO_MORE:
                refreshLayout.setRefreshing(false);
                nodataView.setVisibility(View.GONE);
                momentRv.setVisibility(View.VISIBLE);
                adapter.setState(STATE.NO_MORE);
                break;
            case DiscoveryLogic.MSG_LOAD_FAIL:
                refreshLayout.setRefreshing(false);
                nodataView.setVisibility(View.VISIBLE);
                momentRv.setVisibility(View.GONE);
                adapter.setState(STATE.NORMAL);
                break;
            case DiscoveryLogic.MSG_DELETE_MOMENT:
                adapter.notifyDataSetChanged();
                break;
        }
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logic.initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_discovery, container, false);
        initView();
        setView();
        return rootView;
    }

    private void initView() {
        refreshLayout = rootView.findViewById(R.id.discovery_refresh_view);
        momentRv = rootView.findViewById(R.id.discovery_recycler_view);
        nodataView = rootView.findViewById(R.id.discovery_no_data_view);
        progressingView = rootView.findViewById(R.id.discovery_progressing);
        adapter = new DiscoveryMomentAdapter(logic.getMoments());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }

    private void setView() {
        refreshLayout.setRefreshing(true);
        nodataView.setVisibility(View.GONE);
        momentRv.setAdapter(adapter);
        momentRv.setLayoutManager(linearLayoutManager);
        momentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.d(TAG, "onScrollStateChanged: SCROLL_STATE_IDLE");
                        if (linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                            switch (adapter.getState()) {
                                case NORMAL:
                                    adapter.setState(STATE.LOADING);
                                    logic.loadMore();
                                    break;
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.d(TAG, "onScrollStateChanged: SCROLL_STATE_DRAGGING");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.d(TAG, "onScrollStateChanged: SCROLL_STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                Log.d(TAG, "onScrolled: dx = " + dx + "; dy = " + dy);
            }
        });
        refreshLayout.setOnRefreshListener(this);
        nodataView.setOnClickListener(this);
        //初次加载数据超时监控
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    if (logic.getMoments() == null || logic.getMoments().isEmpty()) {
                        handler.sendEmptyMessage(DiscoveryLogic.MSG_LOAD_FAIL);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onEvent(PiedEvent event) {
        super.onEvent(event);
        switch (event.getType()) {
            case MSG_POST_MOMENT_SUCC:
                onRefresh();
                break;
            case MSG_DELETE_MOMENT_SUCC:
                PiedToast.showErrorShort("删除朋友圈成功");
                logic.deleteMomentById((int) event.getParams());
                progressingView.setVisibility(View.GONE);
                break;
            case MSG_DELETE_MOMENT_FAIL:
                PiedToast.showErrorShort("删除朋友圈失败");
                progressingView.setVisibility(View.GONE);
                break;
            case MSG_PROGRESSING_DELETE:
                progressingView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discovery_no_data_view:
                onRefresh();
                break;
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        logic.loadMoments(0);
    }

    @Override
    public void onDestroy() {
        logic = null;
        super.onDestroy();
    }
}
