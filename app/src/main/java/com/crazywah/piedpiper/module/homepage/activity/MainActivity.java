package com.crazywah.piedpiper.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.module.contact.util.RequestCountUtil;
import com.crazywah.piedpiper.module.discovery.activity.PostMomentActivity;
import com.crazywah.piedpiper.module.homepage.MainLogic;
import com.crazywah.piedpiper.module.homepage.adapter.HomePagerAdapter;
import com.crazywah.piedpiper.module.user.activity.UserInfoActivity;
import com.crazywah.piedpiper.util.MessageUtil;
import com.crazywah.piedpiper.util.NotificationUtil;
import com.crazywah.piedpiper.widget.MainBottomView;
import com.crazywah.piedpiper.widget.TitleBarView;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MainBottomView.OnItemCheckListener, Observer<List<RecentContact>>, TitleBarView.OnTitleBarClickListener {

    private static final String TAG = "MainActivity";

    private static final String[] TITLES = new String[]{
            "对话",
            "用户",
            "发现"
    };

    private TitleBarView titleBarView;
    private MainBottomView bottomView;
    private ViewPager mainViewPager;
    private HomePagerAdapter homePagerAdapter;
    private int currPageIndex = 0;
    private MainLogic logic;

    private UserDBService userService;
    private Gson parser = new Gson();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = UserDBService.newInstance();
        NIMClient.getService(MsgServiceObserve.class).observeRecentContact(this, true);
        initView();
        setView();
        load();
    }

    private void load() {
        logic.updateMyInfo();
        logic.loadFriends();
    }

    private void initView() {
        titleBarView = findViewById(R.id.main_titlebar);
        mainViewPager = findViewById(R.id.main_pager);
        bottomView = findViewById(R.id.main_bottom_view);
    }

    private void setView() {
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(homePagerAdapter);
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.addOnPageChangeListener(this);
        titleBarView.setTitle(TITLES[currPageIndex]);
        titleBarView.setTwoImg(R.drawable.ic_person_add_black);
        titleBarView.setOneImg(R.drawable.ic_add_a_photo_black);
        titleBarView.setOnTitleBarClickListener(this);
        bottomView.setItemCheckListener(this);
        selectPage(currPageIndex);
        updateUnRead();
//        bottomView.showRedUnRead(0, "5");
//        bottomView.showGreenUnRead(1, "1");
    }

    private void updateView() {
        titleBarView.setAvatarImg(PiedPiperApplication.getLoginUser());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case MainLogic.MSG_RECENT_CONTACT_CHANGE:
                updateUnRead();
                break;
            case MainLogic.MSG_GET_MY_INFO_SUCC:
                updateUnRead();
                updateView();
                break;
            case MainLogic.MSG_GET_MY_INFO_FAIL:
                updateUnRead();
                break;
            case MainLogic.MSG_GET_FRIENDS_SUCC:
                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_UPDATE_FRIEND_LIST));
                break;
            case MainLogic.MSG_GET_FRIENDS_FAIL:
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new MainLogic(this, handler);
    }

    private long lastTime = 0;


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000) {
            PiedToast.showShort("再按一次退出应用");
            lastTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            PiedPiperApplication.logout();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtil.cannelAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeRecentContact(this, false);
    }

    /**
     * 底部按钮点击事件
     *
     * @param index
     */
    @Override
    public void onItemCheck(int index) {
        selectPage(index);
    }

    private void selectPage(int index) {
        currPageIndex = index;
        mainViewPager.setCurrentItem(index);
        titleBarView.setTitle(TITLES[index]);
        bottomView.check(index);
        switch (index) {
            case MainBottomView.INDEX_CHAT:
                titleBarView.oneVisiable(false);
                titleBarView.twoVisiable(true);
                break;
            case MainBottomView.INDEX_CONTACT:
                titleBarView.oneVisiable(false);
                titleBarView.twoVisiable(true);
                break;
            case MainBottomView.INDEX_DISCOVERY:
                titleBarView.oneVisiable(true);
                titleBarView.twoVisiable(true);
                break;
            default:
                break;
        }
    }

    private void updateUnRead() {
        if (MessageUtil.getTotalUnReadCount() > 0) {
            bottomView.showRedUnRead(MainBottomView.INDEX_CHAT, MessageUtil.getTotalUnReadCount() + "");
        } else {
            bottomView.hideUnRead(MainBottomView.INDEX_CHAT);
        }
        if (RequestCountUtil.getRequestUnRead() > 0) {
            bottomView.showGreenUnRead(MainBottomView.INDEX_CONTACT, RequestCountUtil.getRequestUnRead() + "");
        } else {
            bottomView.hideUnRead(MainBottomView.INDEX_CONTACT);
        }
    }

    /**
     * @param event
     */
    @Override
    public void onEvent(PiedEvent event) {
        switch (event.getType()) {
            case MSG_NOTIFY_REGISTRANT_UPDATE:
                updateView();
                break;
            case MSG_RECEIVE_FRIEND_REQUEST:
            case MSG_UPDATE_UNREAD_COUNT:
            case MSG_READ_FRIEND_REQUEST:
                updateUnRead();
                break;
        }
    }

    @Override
    public void onEvent(List<RecentContact> recentContacts) {
        handler.sendEmptyMessage(MainLogic.MSG_RECENT_CONTACT_CHANGE);
    }

    @Override
    public void onTitleClick(int itemType) {
        switch (itemType) {
            case TitleBarView.CLICK_AVATAR:
                UserInfoActivity.launch(this, PiedPiperApplication.getLoginUser().getAccountId());
                break;
            case TitleBarView.CLICK_TITLE:
                break;
            case TitleBarView.CLICK_ONE:
//                photoDialog.show();
                PostMomentActivity.launch(this);
                break;
            case TitleBarView.CLICK_TWO:
                SearchActivity.launch(this);
                break;
            default:
                break;
        }
    }
}
