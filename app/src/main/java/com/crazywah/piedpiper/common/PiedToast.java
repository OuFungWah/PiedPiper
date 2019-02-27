package com.crazywah.piedpiper.common;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.widget.PiedToastView;
import com.crazywah.piedpiper.widget.PiedToastView;

public class PiedToast{

    private static Toast toast;

    public static void showShort(String text){
        ((PiedToastView)toast.getView()).setText(text);
        ((PiedToastView)toast.getView()).setBackground(PiedPiperApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLong(String text){
        ((PiedToastView)toast.getView()).setText(text);
        ((PiedToastView)toast.getView()).setBackground(PiedPiperApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showErrorShort(String text){
        ((PiedToastView)toast.getView()).setText(text);
        ((PiedToastView)toast.getView()).setBackground(PiedPiperApplication.getInstance().getResources().getColor(R.color.colorAccent));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showErrorLong(String text){
        ((PiedToastView)toast.getView()).setText(text);
        ((PiedToastView)toast.getView()).setBackground(PiedPiperApplication.getInstance().getResources().getColor(R.color.colorAccent));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void init(Context context){
        toast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
        toast.setView(new PiedToastView(context));
    }

}
