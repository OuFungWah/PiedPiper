package com.crazywah.piedpiper.module.contact.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseFragment;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.contact.adapter.ContactListAdapter;
import com.crazywah.piedpiper.module.contact.logic.ContactLogic;

import org.greenrobot.eventbus.EventBus;

public class ContactFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView contactListRv;
    private SwipeRefreshLayout refreshLayout;
    private ContactLogic logic;
    private ContactListAdapter adapter;


    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    protected void prepareLogic() {
        logic = new ContactLogic(getContext(), handler);
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case ContactLogic.MSG_GET_FRIENDS_SUCC:
                updateView();
                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_UPDATE_FRIEND_LIST));
                break;
            case ContactLogic.MSG_GET_FRIENDS_FAIL:
                refreshLayout.setRefreshing(false);
                break;
            default:
                break;
        }
        return false;
    }

    private void loadData() {
        logic.loadFriendList();
    }

    private void updateView() {
        adapter.setList(logic.getObjects());
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logic.loadFriendList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(view);
        setView();
        return view;
    }

    private void initView(View view) {
        contactListRv = view.findViewById(R.id.contacts_list);
        refreshLayout = view.findViewById(R.id.contacts_refresh);
        adapter = new ContactListAdapter(logic.getObjects());
    }

    private void setView() {
        refreshLayout.setOnRefreshListener(this);
        contactListRv.setAdapter(adapter);
        contactListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onEvent(PiedEvent event) {
        switch (event.getType()) {
            case MSG_UPDATE_FRIEND_LIST:
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
