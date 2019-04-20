package com.crazywah.piedpiper.module.discovery.logic;

import android.content.Context;
import android.os.Handler;
import android.util.ArraySet;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiscoveryLogic extends BaseLogic {

    public static final int MSG_LOAD_SUCC = 0;
    public static final int MSG_LOAD_FAIL = 1;
    public static final int MSG_LOAD_NO_MORE = 2;
    public static final int MSG_DELETE_MOMENT = 3;

    private static final int PAGE_SIZE = 10;
    private int curPage = 0;

    private List<Moment> moments = new ArrayList<>();
    private Gson parser = new Gson();
    private Type urlsType;

    public DiscoveryLogic(Context context, Handler handler) {
        super(context, handler);
        urlsType = new TypeToken<ArrayList<String>>() {
        }.getType();
    }

    public void initData() {
        curPage = 0;
        loadMoments(curPage);
    }

    public void loadMore() {
        curPage++;
        loadMoments(curPage);
    }

    public void loadMoments(int page) {
        RequestManager.getInstance().getAllMoments(PAGE_SIZE, page * PAGE_SIZE, new PiedCallback<List<Moment>>() {
            @Override
            public void onSuccess(List<Moment> object) {
                if (object != null && !object.isEmpty()) {

                    for (Moment moment : object) {
                        moment.setPicUrls((List<String>) parser.fromJson(moment.getPhotoList(), urlsType));
                    }
                    addMoment(object);
                    notifyUi(MSG_LOAD_SUCC);
                } else {
                    curPage--;
                    notifyUi(MSG_LOAD_NO_MORE);
                }
            }

            @Override
            public boolean onFail(String message) {
                curPage--;
                notifyUi(MSG_LOAD_FAIL);
                return false;
            }
        });
    }

    public void addMoment(List<Moment> newMomentList) {
        if (!moments.isEmpty()) {
            if (newMomentList != null && !newMomentList.isEmpty()) {
                if (newMomentList.get(0).getMomentId() < moments.get(moments.size() - 1).getMomentId()) {
                    moments.addAll(newMomentList);
                } else if (newMomentList.get(newMomentList.size() - 1).getMomentId() > moments.get(0).getMomentId()) {
                    moments.addAll(0, newMomentList);
                } else {
                    for (Moment moment : newMomentList) {
                        int position = searchIndex(moment, 0, moments.size() - 1);
                        if (moments.get(position).getMomentId() == moment.getMomentId()) {
                            moments.set(position, moment);
                        } else {
                            moments.add(position, moment);
                        }
                    }
                }
            }
        } else {
            moments.addAll(newMomentList);
        }
    }

    public void deleteMomentById(int momentId) {
        int targetIndex = searchIndex(momentId, 0, moments.size() - 1);
        if (moments.get(targetIndex).getMomentId() == momentId) {
            moments.remove(targetIndex);
            notifyUi(MSG_DELETE_MOMENT);
        }
    }

    //二分查找
    public int searchIndex(Moment target, int start, int end) {
        return searchIndex(target.getMomentId(), start, end);
    }

    public int searchIndex(int targetId, int start, int end) {
        if (start == end) {
            return start;
        }
        int mid = start + (end - start) / 2;
        if (moments.get(mid).getMomentId() == targetId) {
            return mid;
        } else if (moments.get(mid).getMomentId() > targetId) {
            return searchIndex(targetId, mid + 1, end);
        } else {
            return searchIndex(targetId, start, mid);
        }
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<Moment> getMoments() {
        return moments;
    }

    public void setMoments(List<Moment> moments) {
        this.moments = moments;
    }
}
