package com.crazywah.piedpiper.util;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.crazywah.piedpiper.application.PiedPiperApplication;

public class DensityUtils {

    /**
     * 屏幕密度dpi
     *
     * @return 屏幕密度
     */
    public static float getDensity() {
        return PiedPiperApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    public static float getScaledDensity() {
        return PiedPiperApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return PiedPiperApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return PiedPiperApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    private static Point sRealSizePoint;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Point getScreenRealSize(Activity activity) {
        if (sRealSizePoint == null) {
            sRealSizePoint = new Point();
            activity.getWindowManager().getDefaultDisplay().getRealSize(sRealSizePoint);
        }
        return sRealSizePoint;
    }

    public static int dp2px(float density, int dp) {
        return (int) (dp * density + 0.5f);
    }

    public static int dp2px(int dp) {
        return (int) (dp * getDensity() + 0.5f);
    }

    public static int dp2px(float dp) {
        return (int) (dp * getDensity() + 0.5f);
    }

    public static int px2dp(float density, int px) {
        return (int) (px / density + 0.5f);
    }

    public static int px2dp(int px) {
        return (int) (px / getDensity() + 0.5f);
    }

    public static int px2dp(float px) {
        return (int) (px / getDensity() + 0.5f);
    }

    public static int sp2px(float scaledDensity, int sp) {
        return (int) (sp * scaledDensity + 0.5f);
    }

    public static int sp2px(int sp) {
        return (int) (sp * getScaledDensity() + 0.5f);
    }

    public static int sp2px(float sp) {
        return (int) (sp * getScaledDensity() + 0.5f);
    }

    public static int px2sp(float scaledDensity, int px) {
        return (int) (px / scaledDensity + 0.5f);
    }

    public static int px2sp(int px) {
        return (int) (px / getScaledDensity() + 0.5f);
    }

    public static int px2sp(float px) {
        return (int) (px / getScaledDensity() + 0.5f);
    }

}
