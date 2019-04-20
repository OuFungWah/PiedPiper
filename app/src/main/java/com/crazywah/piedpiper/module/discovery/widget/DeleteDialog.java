package com.crazywah.piedpiper.module.discovery.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;

import org.greenrobot.eventbus.EventBus;

public class DeleteDialog {

    public static void show(Context context,final int momentId) {
        new AlertDialog.Builder(context).setTitle("注意")
                .setMessage("请问你确定要删除这条朋友圈？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_PROGRESSING_DELETE));
                        RequestManager.getInstance().deleteMoment(momentId, new PiedCallback<Integer>() {
                            @Override
                            public void onSuccess(Integer object) {
                                PiedEvent event = new PiedEvent(PiedEvent.EventType.MSG_DELETE_MOMENT_SUCC);
                                event.setParams(momentId);
                                EventBus.getDefault().post(event);
                            }

                            @Override
                            public boolean onFail(String message) {
                                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_DELETE_MOMENT_FAIL));
                                return false;
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}
