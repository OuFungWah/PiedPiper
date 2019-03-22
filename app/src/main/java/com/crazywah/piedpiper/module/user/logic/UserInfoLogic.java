package com.crazywah.piedpiper.module.user.logic;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.NormalEntrance;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.user.activity.ModifyInfoActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.crazywah.piedpiper.common.ConstValue.USER_INFO_METHOD;


public class UserInfoLogic extends BaseLogic {

    public static final int MSG_GET_USER_INFO_SUCC = 0;
    public static final int MSG_GET_USER_INFO_FAIL = 1;
    public static final int MSG_UPLOAD_AVATAR_SUCC = 2;
    public static final int MSG_UPLOAD_AVATAR_FAIL = 3;

    private static final String[] TITLES = new String[]{
            "昵称",
            "密码",
            "性别",
            "生日",
            "电话",
            "地址",
            "邮箱",
            "签名"
    };

    private static final int[] RESIDS = new int[]{
            R.drawable.ic_info_nickname_white,
            R.drawable.ic_info_password_white,
            R.drawable.ic_info_gender_white,
            R.drawable.ic_info_calendar_white,
            R.drawable.ic_info_phone_white,
            R.drawable.ic_info_address_white,
            R.drawable.ic_info_account_id_white,
            R.drawable.ic_info_signature_white
    };

    private List<Object> list = new ArrayList<>();
    private User user;

    public UserInfoLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void loadUserInfo(String id) {
        RequestManager.getInstance().getUserInfo(id, new PiedCallback<User>() {
            @Override
            public void onSuccess(User object) {
                user = object;
                combineList();
                adjustEntrances();
                notifyUi(MSG_GET_USER_INFO_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_USER_INFO_FAIL);
                return false;
            }
        });
    }

    public void uploadAvatar(String base64) {
        RequestManager.getInstance().uploadAvatar(base64, new PiedCallback() {
            @Override
            public void onSuccess(Object object) {
                String url = (String) object;
                PiedPiperApplication.getLoginUser().setAvatar(url);
                user.setAvatar(url);
                notifyUi(MSG_UPLOAD_AVATAR_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_UPLOAD_AVATAR_FAIL);
                return false;
            }
        });
    }

    public void afterGetUserData(User object) {
        user = object;
        combineList();
        adjustEntrances();
        notifyUi(MSG_GET_USER_INFO_SUCC);
    }

    private void adjustEntrances() {
        boolean isMe = user.getAccountId().equals(PiedPiperApplication.getLoginUser().getAccountId());
        if (user != null) {
            Class userClass = User.class;

            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i) instanceof NormalEntrance) {
                    try {
                        Object result = userClass.getMethod(USER_INFO_METHOD[i]).invoke(user);
                        String content = null;
                        if (result instanceof Date) {
                            content = new SimpleDateFormat("yyyy-MM-dd").format(result);
                        } else if (result instanceof String) {
                            content = (String) result;
                        }
                        NormalEntrance entrance = (NormalEntrance) list.get(i);
                        if (!isMe && content == null) {
                            list.remove(entrance);
                        } else {
                            entrance.setContent(content);
                            entrance.setCanClick(isMe);
                            final int finalI = i;
                            entrance.setCallBack(new NormalEntrance.CallBack() {
                                @Override
                                public void onEnter(NormalEntrance entrance) {
                                    ModifyInfoActivity.launch(context, USER_INFO_METHOD[finalI]);
                                }
                            });
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void combineList() {
        list.clear();
        for (int i = 0; i < TITLES.length; i++) {
            list.add(combineEntrance(TITLES[i], RESIDS[i], NormalEntrance.getColor(i % 7)));
        }
    }

    private NormalEntrance combineEntrance(String title, int resId, String color) {
        NormalEntrance entrance = new NormalEntrance();
        entrance.setIconBm(BitmapFactory.decodeResource(context.getResources(), resId));
        entrance.setTitle(title);
        entrance.setBackgroundColor(color);
        return entrance;
    }

    public List<Object> getList() {
        return list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
