package com.crazywah.piedpiper.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crazywah.piedpiper.application.PiedPiperApplication;

public class NetworkUtil {

    /**
     * 检查网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) PiedPiperApplication.getInstance().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

}
