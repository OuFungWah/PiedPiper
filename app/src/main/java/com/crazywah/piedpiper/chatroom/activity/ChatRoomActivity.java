package com.crazywah.piedpiper.chatroom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.crazywah.piedpiper.chatroom.adapter.ChatRoomAdapter;
import com.crazywah.piedpiper.chatroom.logic.ChatRoomLogic;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.homepage.MainActivity;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener, Observer<List<IMMessage>> {

    public static boolean isShowing = false;

    public static final String KEY_ID = "key_id";
    public static final String KEY_LATEST_MESSAGE = "latest_message";

    private ImageView backImg;
    private ImageView titleAvatarImg;
    private TextView titleTv;
    private TextView subTitleTv;

    private RecyclerView chatListRv;
    private ChatRoomAdapter adapter;
    private EditText inputEt;
    private Button sendBtn;

    private ChatRoomLogic logic;
    private String targetId;
    private IMMessage latestMessage;
    private Gson parser;

    public static void launch(Context context, String id, IMMessage message) {
        Intent intent = new Intent(context, ChatRoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, id);
        bundle.putSerializable(KEY_LATEST_MESSAGE, message);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(this, true);
        parser = new Gson();
        handleParams();
        logic.getTargetInfo(targetId);
        initView();
        setView();
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
        if (getIntent().getExtras().containsKey(KEY_LATEST_MESSAGE)) {
            latestMessage = (IMMessage) getIntent().getExtras().getSerializable(KEY_LATEST_MESSAGE);
        }
    }

    private void initView() {
        backImg = findViewById(R.id.chat_room_back_img);
        titleAvatarImg = findViewById(R.id.chat_room_avatar_img);
        titleTv = findViewById(R.id.chat_room_title);
        subTitleTv = findViewById(R.id.chat_room_subtitle);
        chatListRv = findViewById(R.id.chat_room_list);
        inputEt = findViewById(R.id.chat_room_et);
        sendBtn = findViewById(R.id.chat_room_send);
        adapter = new ChatRoomAdapter(logic.getMessageList());
    }

    private void setView() {
        backImg.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        chatListRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        chatListRv.setAdapter(adapter);
//        IMMessage message = MessageBuilder.createEmptyMessage(targetId, SessionTypeEnum.P2P, System.currentTimeMillis());
        logic.getMessage(latestMessage);
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
                break;
            case ChatRoomLogic.MSG_GET_USER_INFO_FAIL:
                break;
            case ChatRoomLogic.MSG_GET_HISTORY_SUCC:
                adapter.notifyDataSetChanged();
                chatListRv.smoothScrollToPosition(0);
                break;
            case ChatRoomLogic.MSG_GET_HISTORY_FAIL:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new ChatRoomLogic(this, handler);
    }

    @Override
    public void onBackPressed() {
        MainActivity.launch(this);
        finish();
    }

    private void updateUserInfoView(User user) {
        titleTv.setText(user.getNickname());
        subTitleTv.setText(user.getSignature() == null ? "这个人很懒，不写签名" : user.getSignature());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_room_back_img:
                onBackPressed();
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
    public void onEvent(List<IMMessage> imMessages) {
        if (imMessages.get(0).getFromAccount().equals(targetId)) {
            logic.addToFirst(imMessages);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(this, false);
    }
}
