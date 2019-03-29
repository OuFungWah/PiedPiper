package com.crazywah.piedpiper.module.chat.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.crazywah.piedpiper.module.chat.adapter.RecentContactAdapter;
import com.crazywah.piedpiper.module.chat.logic.ChatFragmentLogic;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

public class ChatFragment extends BaseFragment implements Observer<List<RecentContact>> {

    private static final String TAG = "ChatFragment";

    private ChatFragmentLogic logic;
    private RecyclerView recentChatListRv;
    private RecentContactAdapter adapter;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case ChatFragmentLogic.LOAD_RECENT_CONTACTS_SUCC:
                updateView();
                break;
            case ChatFragmentLogic.LOAD_RECENT_CONTACTS_FAIL:
                break;
            default:
                break;
        }
        return false;
    }

    private void updateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NIMClient.getService(MsgServiceObserve.class).observeRecentContact(this, true);
        logic.loadRecentContacts();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(view);
        setView();
        return view;
    }

    private void initView(View view) {
        recentChatListRv = view.findViewById(R.id.chat_fragment_rv);
        recentChatListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecentContactAdapter(logic.getRecentContacts());
    }

    private void setView() {
        recentChatListRv.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeRecentContact(this, false);
    }

    @Override
    public void prepareLogic() {
        logic = new ChatFragmentLogic(getContext(), handler);
    }

    @Override
    public void onEvent(PiedEvent event) {
        switch (event.getType()) {
            case MSG_UPDATE_FRIEND_LIST:
                updateView();
                break;
            case MSG_UPDATE_UNREAD_COUNT:
                logic.loadRecentContacts();
                break;
            default:
                break;
        }
    }

    @Override
    public void onEvent(List<RecentContact> recentContacts) {
        logic.loadRecentContacts();
    }
}
