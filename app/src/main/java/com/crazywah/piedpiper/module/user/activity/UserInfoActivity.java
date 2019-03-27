package com.crazywah.piedpiper.module.user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.bean.CustomMessageType;
import com.crazywah.piedpiper.bean.CustomNotificationBean;
import com.crazywah.piedpiper.bean.RELATIONSHIP;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.chatroom.activity.ChatRoomActivity;
import com.crazywah.piedpiper.module.user.adapter.UserInfoEntranceAdapter;
import com.crazywah.piedpiper.module.user.logic.UserInfoLogic;
import com.crazywah.piedpiper.util.BitmapUtil;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.PhotoDenpendence;
import com.crazywah.piedpiper.widget.NormalDialog;
import com.crazywah.piedpiper.widget.PhotoDialog;
import com.google.gson.Gson;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_ID = "key_id";

    private ImageView avatarImg;
    private TextView nickNameTv;
    private TextView accountIdTv;
    private NestedScrollView scrollView;
    private RecyclerView infoListRv;

    private UserInfoLogic logic;
    private UserInfoEntranceAdapter adapter;
    private PhotoDialog photoDialog;
    private NormalDialog sendRequestDialog;
    private PhotoDenpendence photoDenpendence;

    private FloatingActionButton addFriendFab;
    private FloatingActionButton sendMessageFab;

    private Gson parser = new Gson();
    private String id;
    private boolean isMe = false;

    public static void launch(Context context, String accountId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_ID, accountId);
        context.startActivity(intent);
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case UserInfoLogic.MSG_GET_USER_INFO_SUCC:
                updateView();
                break;
            case UserInfoLogic.MSG_GET_USER_INFO_FAIL:
                break;
            case UserInfoLogic.MSG_UPLOAD_AVATAR_SUCC:
                updateView();
                break;
            case UserInfoLogic.MSG_UPLOAD_AVATAR_FAIL:
                break;
            case UserInfoLogic.MSG_SEND_MESSAGE_SUCC:
                sendRequestDialog.dismiss();
                PiedToast.showShort("发送请求成功");
                break;
            case UserInfoLogic.MSG_SEND_MESSAGE_FAIL:
                PiedToast.showShort("发送请求失败");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleParams(getIntent());
        photoDenpendence = new PhotoDenpendence(this, handler);
        photoDenpendence.setCallBack(new PhotoDenpendence.BitmapCallBack() {
            @Override
            public void afterGetBitmap(Uri uri, Bitmap bitmap) {
                String base64 = BitmapUtil.bitmapToBase64(bitmap);
                logic.uploadAvatar(base64);
                photoDialog.dismiss();
            }
        });
        addDependence(photoDenpendence);
        initView();
        setView();
        //获取数据
        if (id.equals(PiedPiperApplication.getLoginUser().getAccountId())) {
            logic.afterGetUserData(PiedPiperApplication.getLoginUser());
        } else {
            logic.loadUserInfo(id);
        }
    }

    private void handleParams(Intent intent) {
        id = intent.getStringExtra(KEY_ID);
        isMe = id.equals(PiedPiperApplication.getLoginUser().getAccountId());
    }

    private void initView() {
        avatarImg = findViewById(R.id.user_info_avatar);
        nickNameTv = findViewById(R.id.user_info_nickname);
        accountIdTv = findViewById(R.id.user_info_account_id_tv);
        infoListRv = findViewById(R.id.user_info_list_rv);
        scrollView = findViewById(R.id.user_info_scrollview);
        addFriendFab = findViewById(R.id.user_info_add_friend_request_fab);
        sendMessageFab = findViewById(R.id.user_info_send_message_fab);
        sendRequestDialog = new NormalDialog(this, R.style.NormalDialogBgStyle);
        adapter = new UserInfoEntranceAdapter(logic.getList());
        photoDialog = new PhotoDialog(this, R.style.PickPhotoDialog);
    }

    private void setView() {
        infoListRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        infoListRv.setAdapter(adapter);
        avatarImg.setOnClickListener(this);
        addFriendFab.setOnClickListener(this);
        sendMessageFab.setOnClickListener(this);
        photoDialog.setDependence(photoDenpendence);
        if (!id.equals(PiedPiperApplication.getLoginUser().getAccountId())) {
            photoDialog.hideTakeAndSelect();
        }
        sendRequestDialog.init();
        sendRequestDialog.setTitle("发送好友请求");
        sendRequestDialog.setCallBack(new NormalDialog.CallBack() {
            @Override
            public void onPositive(String text) {
                if (!TextUtils.isEmpty(text)) {
                    CustomNotificationBean bean = new CustomNotificationBean();
                    bean.setFromName(PiedPiperApplication.getLoginUser().getName());
                    bean.setType(CustomMessageType.TYPE_FRIEND_REQUEST);
                    bean.setContent(text);
                    logic.sendFriendRequest(id, parser.toJson(bean));
                } else {
                    PiedToast.showShort("请填写请求信息");
                }
            }

            @Override
            public void onNegative() {
                sendRequestDialog.dismiss();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    public void updateView() {
        User user = logic.getUser();
        ImageLoader.loadUserAvatar(user, avatarImg);
        nickNameTv.setText(user.getName());
        accountIdTv.setText("ID：" + user.getAccountId());
        if (isMe) {
            addFriendFab.setVisibility(View.GONE);
            sendMessageFab.setVisibility(View.GONE);
        } else {
            switch (user.getRelation()) {
                case RELATIONSHIP.STRANGER:
                    addFriendFab.setVisibility(View.VISIBLE);
                    sendMessageFab.setVisibility(View.GONE);
                    break;
                case RELATIONSHIP.FRIEND:
                case RELATIONSHIP.SPECIAL:
                case RELATIONSHIP.STARED:
                    sendMessageFab.setVisibility(View.VISIBLE);
                    addFriendFab.setVisibility(View.GONE);
                    break;
                case RELATIONSHIP.BLACK:
                    sendMessageFab.setVisibility(View.GONE);
                    addFriendFab.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
        adapter.setObjectList(logic.getList());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    @Override
    public void onEvent(PiedEvent event) {
        super.onEvent(event);
        switch (event.getType()) {
            case MSG_NOTIFY_REGISTRANT_UPDATE:
                if (isMe) {
                    logic.afterGetUserData(PiedPiperApplication.getLoginUser());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void prepareLogic() {
        logic = new UserInfoLogic(this, handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_avatar:
                photoDialog.show();
                break;
            case R.id.user_info_add_friend_request_fab:
                sendRequestDialog.show();
                break;
            case R.id.user_info_send_message_fab:
                ChatRoomActivity.launch(this, id);
                break;
            default:
                break;
        }
    }
}
