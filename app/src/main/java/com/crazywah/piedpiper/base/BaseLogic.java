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
        initData();
    }

    protected void initData(){

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

    public void notifyUi(int what, int arg1){
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        handler.sendMessage(msg);
    }

    public void notifyUi(int what, int arg1, int arg2){
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        handler.sendMessage(msg);
    }

    public void notifyUi(int what, Object obj){
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

}
