package com.crazywah.piedpiper.module.discovery.logic;

import android.content.Context;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.discovery.bean.MomentDetail;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MomentDetailLogic extends BaseLogic {

    private static final int INDEX_MOMENT = 0;
    private static final int INDEX_LIKE = 1;
    private static final int INDEX_COMMENT = 2;

    public static final int MSG_GET_MOMENT_DETAIL_SUCC = 0;
    public static final int MSG_GET_MOMENT_DETAIL_FAIL = 1;
    public static final int MSG_ADD_COMMENT_SUCC = 2;
    public static final int MSG_ADD_COMMENT_FAIL = 3;
    public static final int MSG_LIKE_SUCC = 4;
    public static final int MSG_LIKE_FAIL = 5;
    public static final int MSG_DISLIKE_SUCC = 6;
    public static final int MSG_DISLIKE_FAIL = 7;

    private MomentDetail momentDetail;

    public MomentDetailLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void load(int momentId) {
        RequestManager.getInstance().getMomentDetail(momentId, new PiedCallback<MomentDetail>() {
            @Override
            public void onSuccess(MomentDetail object) {
                momentDetail = object;
                Moment.convertPicUrl(momentDetail.getMoment());
                notifyUi(MSG_GET_MOMENT_DETAIL_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_GET_MOMENT_DETAIL_FAIL);
                return false;
            }
        });
    }

    public void addComment(int momentId, String toId, String content) {
        RequestManager.getInstance().addComment(momentId, toId, content, new PiedCallback<Comment>() {
            @Override
            public void onSuccess(Comment object) {
                PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_COMMENT_SUCC);
                event.setParams(momentDetail.getMoment());
                EventBus.getDefault().post(event);
                notifyUi(MSG_ADD_COMMENT_SUCC);
            }

            @Override
            public boolean onFail(String message) {
                notifyUi(MSG_ADD_COMMENT_FAIL);
                return false;
            }
        });
    }

    public void like(Moment moment) {
        if (moment.getIsLiked() == 0) {
            RequestManager.getInstance().likeMoment(moment.getMomentId(), new PiedCallback<Void>() {
                @Override
                public void onSuccess(Void object) {
                    PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_LIKE_SUCC);
                    event.setParams(momentDetail.getMoment());
                    EventBus.getDefault().post(event);
                    notifyUi(MSG_LIKE_SUCC);
                }

                @Override
                public boolean onFail(String message) {
                    notifyUi(MSG_LIKE_FAIL);
                    return false;
                }
            });
        } else {
            RequestManager.getInstance().dislikeMoment(moment.getMomentId(), new PiedCallback<Void>() {
                @Override
                public void onSuccess(Void object) {
                    PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_DISLIKE_SUCC);
                    event.setParams(momentDetail.getMoment());
                    EventBus.getDefault().post(event);
                    notifyUi(MSG_DISLIKE_SUCC);
                }

                @Override
                public boolean onFail(String message) {
                    notifyUi(MSG_DISLIKE_FAIL);
                    return false;
                }
            });
        }
    }

    public MomentDetail getMomentDetail() {
        return momentDetail;
    }

    public void setMomentDetail(MomentDetail momentDetail) {
        this.momentDetail = momentDetail;
    }
}
