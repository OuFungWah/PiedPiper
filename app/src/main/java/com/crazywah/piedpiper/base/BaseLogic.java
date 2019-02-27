package com.crazywah.piedpiper.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


public class BaseLogic {

    protected Context context;
    protected Handler handler;

    public BaseLogic(Context context,Handler handler){
        this.context = context;
        this.handler = handler;
    }

    public void notifyUi(int what){
        handler.sendEmptyMessage(what);
    }

    public void notifyUi(int what, String message){
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = message;
        handler.sendMessage(msg);
    }

}
