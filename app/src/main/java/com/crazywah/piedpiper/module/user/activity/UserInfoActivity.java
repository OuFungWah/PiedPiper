package com.crazywah.piedpiper.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.user.adapter.UserInfoEntranceAdapter;
import com.crazywah.piedpiper.module.user.logic.UserInfoLogic;
import com.crazywah.piedpiper.util.BitmapUtil;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.PhotoDenpendence;
import com.crazywah.piedpiper.widget.PhotoDialog;

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
    private PhotoDenpendence photoDenpendence;

    private String id;
    private boolean isMe = false;

    public static void launch(Context context, String accountId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_ID, accountId);
        context.startActivity(intent);
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
        photoDialog.setDependence(photoDenpendence);
        if (!id.equals(PiedPiperApplication.getLoginUser().getAccountId())) {
            photoDialog.hideTakeAndSelect();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
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
            default:
                break;
        }
        return false;
    }

    public void updateView() {
        User user = logic.getUser();
        ImageLoader.loadCircle(user.getAvatar(), avatarImg);
        nickNameTv.setText(user.getName());
        accountIdTv.setText("ID：" + user.getAccountId());
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
            default:
                break;
        }
    }
}
