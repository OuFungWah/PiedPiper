package com.crazywah.piedpiper.module.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.module.chatroom.ChatRoomMessageReadObserver;
import com.crazywah.piedpiper.module.chatroom.ChatRoomMessageStatusObserver;
import com.crazywah.piedpiper.module.chatroom.adapter.ChatRoomAdapter;
import com.crazywah.piedpiper.module.chatroom.logic.ChatRoomLogic;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.homepage.activity.MainActivity;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.MessageUtil;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener, Observer<List<IMMessage>>, SwipeRefreshLayout.OnRefreshListener {

    public static boolean isShowing = false;

    public static final String KEY_ID = "key_id";
    public static final String KEY_LATEST_MESSAGE = "latest_message";

    private ImageView backImg;
    private ImageView titleAvatarImg;
    private TextView titleTv;
    private TextView subTitleTv;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView chatListRv;
    private ChatRoomAdapter adapter;
    private EditText inputEt;
    private Button sendBtn;

    private ChatRoomLogic logic;
    private String targetId;
    private Gson parser;
    private ChatRoomMessageStatusObserver messageStatusObserver = new ChatRoomMessageStatusObserver();
    private ChatRoomMessageReadObserver readObserver = new ChatRoomMessageReadObserver();

    public static void launch(Context context, String id) {
        Intent intent = new Intent(context, ChatRoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(this, true);
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(messageStatusObserver, true);
        NIMClient.getService(MsgServiceObserve.class).observeMessageReceipt(readObserver, true);
        parser = new Gson();
        handleParams();
        logic.getTargetInfo(targetId);
        MessageUtil.getService().clearUnreadCount(targetId, SessionTypeEnum.P2P);
        initView();
        setView();
        loadData();
    }

    private void loadData() {
        IMMessage message = MessageBuilder.createEmptyMessage(targetId, SessionTypeEnum.P2P, System.currentTimeMillis());
        logic.loadMessage(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
    }

    private void handleParams() {
        targetId = getIntent().getExtras().getString(KEY_ID);
    }

    private void initView() {
        backImg = findViewById(R.id.chat_room_back_img);
        titleAvatarImg = findViewById(R.id.chat_room_avatar_img);
        titleTv = findViewById(R.id.chat_room_title);
        subTitleTv = findViewById(R.id.chat_room_subtitle);
        chatListRv = findViewById(R.id.chat_room_list);
        inputEt = findViewById(R.id.chat_room_et);
        sendBtn = findViewById(R.id.chat_room_send);
        refreshLayout = findViewById(R.id.chat_room_refresh_view);
        adapter = new ChatRoomAdapter(logic.getMessageList());
    }

    private void setView() {
        chatListRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        chatListRv.setAdapter(adapter);
        backImg.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chatroom;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case ChatRoomLogic.MSG_GET_USER_INFO_SUCC:
                updateUserInfoView(logic.getTarget());
                ImageLoader.loadUserAvatar(logic.getTarget(), titleAvatarImg);
                titleAvatarImg.setOnClickListener(this);
                adapter.notifyDataSetChanged();
                break;
            case ChatRoomLogic.MSG_GET_USER_INFO_FAIL:
                break;
            case ChatRoomLogic.MSG_FIRST_LOAD_MESSAGE_SUCC:
                adapter.notifyDataSetChanged();
                chatListRv.smoothScrollToPosition(0);
                break;
            case ChatRoomLogic.MSG_FIRST_LOAD_MESSAGE_FAIL:
                break;
            case ChatRoomLogic.MSG_LOAD_MORE_MESSAGE_SUCC:
                adapter.notifyDataSetChanged();
                break;
            case ChatRoomLogic.MSG_LOAD_MORE_MESSAGE_FAIL:
                break;
            case ChatRoomLogic.MSG_NEW_MESSAGE_SUCC:
                adapter.notifyDataSetChanged();
                chatListRv.smoothScrollToPosition(0);
                break;
            case ChatRoomLogic.MSG_NEW_MESSAGE_FAIL:
                break;
            default:
                break;
        }
        refreshLayout.setRefreshing(false);
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new ChatRoomLogic(this, handler);
    }

    @Override
    public void onBackPressed() {
        MessageUtil.getService().clearUnreadCount(targetId, SessionTypeEnum.P2P);
        MessageUtil.notifyUnReadChange(targetId);
        MainActivity.launch(this);
        finish();
    }

    private void updateUserInfoView(User user) {
        adapter.setUser(user);
        titleTv.setText(user.getNickname());
        subTitleTv.setText(user.getSignature() == null ? "这个人很懒，不写签名" : user.getSignature());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_room_back_img:
                onBackPressed();
                break;
            case R.id.chat_room_avatar_img:
                UserInfoActivity.launch(this, logic.getTarget().getAccountId());
                break;
            case R.id.chat_room_send:
                String input = inputEt.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    PiedToast.showShort("请输入内容");
                } else {
                    logic.doSend(targetId, input);
                    inputEt.setText("");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onEvent(PiedEvent event) {
        switch (event.getType()) {
            case MSG_UPDATE_MESSAGE_STATUS:
                if (adapter != null) {
                    adapter.notifyMessageStatus((IMMessage) event.getParams());
                }
                break;
            case MSG_UPDATE_MESSAGE_READ:
                if (targetId.equals(event.getParams())) {
                    adapter.notifyReadStatus();
                }
                break;
        }
    }

    @Override
    public void onEvent(List<IMMessage> imMessages) {
        if (imMessages.get(0).getFromAccount().equals(targetId)) {
            logic.addToFirst(imMessages);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        PiedToast.showShort("destroy");
        NIMClient.getService(MsgServiceObserve.class).observeMsgStatus(messageStatusObserver, false);
        NIMClient.getService(MsgServiceObserve.class).observeMessageReceipt(readObserver, false);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(this, false);
    }

    @Override
    public void onRefresh() {
        if (logic.getMessageList() != null && !logic.getMessageList().isEmpty()) {
            logic.loadMessage(logic.getMessageList().get(logic.getMessageList().size() - 1));
        } else {
            refreshLayout.setRefreshing(false);
        }
    }
}
