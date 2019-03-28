package com.crazywah.piedpiper.module.contact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.contact.adapter.FriendRequestListAdapter;
import com.crazywah.piedpiper.module.contact.logic.FriendRequestLogic;
import com.crazywah.piedpiper.widget.TitleBarNormalView;

import org.greenrobot.eventbus.EventBus;

public class FriendRequestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FriendRequestLogic logic;

    private TitleBarNormalView titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private FriendRequestListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View noResultView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FriendRequestActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setView();
        logic.loadRequestList();
    }

    private void initView() {
        titleBar = findViewById(R.id.friend_request_list_title);
        refreshLayout = findViewById(R.id.friend_request_list_refresh_sr);
        recyclerView = findViewById(R.id.friend_request_list_rv);
        noResultView = findViewById(R.id.friend_request_no_request);
        adapter = new FriendRequestListAdapter(logic, logic.getUsers());
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void setView() {
        noResultView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        refreshLayout.setRefreshing(true);
        titleBar.setTitle("好友请求");
        titleBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void updateView() {
        if (logic.getUsers().isEmpty()) {
            noResultView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
        } else {
            noResultView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setUserList(logic.getUsers());
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case FriendRequestLogic.MSG_GET_USERS_SUCC:
                updateView();
                break;
            case FriendRequestLogic.MSG_GET_USERS_FAIL:
                noResultView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                break;
            case FriendRequestLogic.MSG_HANDLE_SUCC:
                logic.loadRequestList();
                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_UPDATE_FRIEND_LIST));
                break;
            case FriendRequestLogic.MSG_HANDLE_FAIL:
                PiedToast.showErrorShort("处理请求失败");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onEvent(PiedEvent event) {
        super.onEvent(event);
        switch (event.getType()) {
            case MSG_RECEIVE_FRIEND_REQUEST:
                onRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    protected void prepareLogic() {
        logic = new FriendRequestLogic(this, handler);
    }

    @Override
    public void onRefresh() {
        logic.loadRequestList();
    }
}
