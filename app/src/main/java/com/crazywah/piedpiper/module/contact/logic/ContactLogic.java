package com.crazywah.piedpiper.module.contact.logic;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.database.service.UserDBService;
import com.crazywah.piedpiper.module.contact.activity.FriendRequestActivity;
import com.crazywah.piedpiper.bean.NormalEntrance;
import com.crazywah.piedpiper.module.contact.util.RequestCountUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactLogic extends BaseLogic {

    private static final String TAG = "ContactLogic";

    public static final int MSG_GET_FRIENDS_SUCC = 0;
    public static final int MSG_GET_FRIENDS_FAIL = 1;

    private List<Object> objects = new ArrayList<>();
    private List<User> friendList = new ArrayList<>();
    private List<NormalEntrance> entranceList = new ArrayList<>();


    public ContactLogic(Context context, Handler handler) {
        super(context, handler);
        initEntrance(context);
    }

    public void initEntrance(final Context context) {
        entranceList.clear();
        NormalEntrance entrance = new NormalEntrance();
        entrance.setIconBm(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_white));
        entrance.setBackgroundColor(NormalEntrance.getColor(NormalEntrance.YELLOW));
        entrance.setCallBack(new NormalEntrance.CallBack() {
            @Override
            public void onEnter(NormalEntrance entrance) {
                FriendRequestActivity.launch(context);
            }
        });
        entrance.setUnReadCount(RequestCountUtil.getRequestUnRead());
        entrance.setTitle("好友请求");
        entranceList.add(entrance);
    }

    public void loadFriendList() {
        RequestManager.getInstance().getFriends(new PiedCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                friendList = result;
                UserDBService.newInstance().addUsers(result);
                notifyUi(MSG_GET_FRIENDS_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_FRIENDS_FAIL);
                return false;
            }
        });
    }

    public List<Object> getObjects() {
        combineObjects();
        return objects;
    }

    private void combineObjects() {
        initEntrance(context);
        objects.clear();
        objects.add("功能入口");
        objects.addAll(entranceList);
        objects.add("好友列表");
        objects.addAll(friendList);
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }
}
