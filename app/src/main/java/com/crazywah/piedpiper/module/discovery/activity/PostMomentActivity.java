package com.crazywah.piedpiper.module.discovery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.discovery.adapter.ImageListAdapter;
import com.crazywah.piedpiper.module.discovery.logic.PostMomentLogic;
import com.crazywah.piedpiper.util.ImageLoader;
import com.crazywah.piedpiper.util.PhotoDenpendence;
import com.crazywah.piedpiper.widget.PhotoDialog;

import org.greenrobot.eventbus.EventBus;

public class PostMomentActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PostMomentActivity";

    private TextView cancelTv;
    private Button postBtn;
    private ImageView avatarImg;
    private EditText inputEt;
    private RecyclerView imageListRv;
    private ImageListAdapter adapter;
    private LinearLayoutManager horizontalManager;
    private PhotoDialog photoDialog;

    private PhotoDenpendence denpendence;
    private PostMomentLogic logic;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PostMomentActivity.class));
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case PostMomentLogic.MSG_PIC_CHANGE:
                adapter.notifyDataSetChanged();
                break;
            case PostMomentLogic.MSG_POST_SUCC:
                finish();
                PiedToast.showShort("发送成功");
                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_POST_MOMENT_SUCC));
                break;
            case PostMomentLogic.MSG_POSY_FAIL:
                PiedToast.showErrorShort("发送失败");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setView();
        loadData();
    }

    private void initView() {
        cancelTv = findViewById(R.id.post_moment_cancel_tv);
        postBtn = findViewById(R.id.post_moment_post_btn);
        avatarImg = findViewById(R.id.post_moment_avatar_img);
        inputEt = findViewById(R.id.post_moment_input_et);
        imageListRv = findViewById(R.id.post_moment_image_rv);
        horizontalManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new ImageListAdapter(logic.getBitmapList(), logic.getSourceUriList());
        photoDialog = new PhotoDialog(this);
        denpendence = new PhotoDenpendence(this, handler);
        denpendence.setCallBack(new PhotoDenpendence.BitmapCallBack() {
            @Override
            public void afterGetBitmap(Uri uri, Bitmap bitmap) {
                Log.d(TAG, "afterGetBitmap: uri = " + uri);
                logic.addSource(uri, bitmap);
                photoDialog.dismiss();
            }

            @Override
            public void checkBitmap() {
            }
        });
    }

    private void setView() {
        addDependence(denpendence);
        photoDialog.setDependence(denpendence);
        photoDialog.hideCheckImg();
        imageListRv.setLayoutManager(horizontalManager);
        imageListRv.setAdapter(adapter);
        ImageLoader.loadUserAvatar(PiedPiperApplication.getLoginUser(), avatarImg);

        cancelTv.setOnClickListener(this);
        postBtn.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_moment;
    }

    @Override
    protected void prepareLogic() {
        logic = new PostMomentLogic(this, handler);
    }

    @Override
    public void onEvent(PiedEvent event) {
        switch (event.getType()) {
            case MSG_REQUEST_PHOTO:
                photoDialog.show();
                break;
            case MSG_REMOVE_POSTING_PHOTO:
                int position = (int) event.getParams();
                logic.remove(position);
                break;
        }
        super.onEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeDependence(denpendence);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_moment_cancel_tv:
                onBackPressed();
                break;
            case R.id.post_moment_post_btn:
                String content = inputEt.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    logic.postMoment(content, 0);
                } else {
                    PiedToast.showErrorShort("请填写推文内容");
                }
                break;
        }
    }
}
