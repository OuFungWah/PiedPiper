package com.crazywah.piedpiper.bean;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.util.Random;

public class NormalEntrance {

    /**
     * 紫色
     */
    public static final int PURPLE = 0;
    /**
     * 绿色
     */
    public static final int GREEN = 1;
    /**
     * 红色
     */
    public static final int RED = 2;
    /**
     * 宝蓝色
     */
    public static final int SAPPHIRE_BLUE = 3;
    /**
     * 天蓝色
     */
    public static final int SKY_BLUE = 4;
    /**
     * 黑色
     */
    public static final int BLACK = 5;
    /**
     * 灰色
     */
    public static final int GREY = 6;
    /**
     * 橙色
     */
    public static final int ORANGE = 7;
    /**
     * 橙色
     */
    public static final int YELLOW = 8;

    private static final String[] COLORS = new String[]{
            "#825AF6",//紫色
            "#7CD153",//绿色
            "#EB5D70",//红色
            "#3884F7",//宝蓝色
            "#55BDF9",//天蓝色
            "#000000",//黑色
            "#878E98",//灰色
            "#ED755E",//橙色
            "#FFEB3B"//黄色
    };

    public static String lastColor;

    public static String getColor(int index) {
        lastColor = COLORS[index];
        return COLORS[index];
    }

    public static String getColor() {
        String color;
        Random random = new Random(System.currentTimeMillis());
        do {
            color = COLORS[random.nextInt(COLORS.length)];
        } while (!TextUtils.isEmpty(color) && color.equals(lastColor));
        return color;
    }

    private String title;
    private String content;
    private Bitmap iconBm;
    private CallBack callBack;
    private String backgroundColor;
    private boolean canClick;
    private int unReadCount;

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getIconBm() {
        return iconBm;
    }

    public void setIconBm(Bitmap iconBm) {
        this.iconBm = iconBm;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public static interface CallBack {
        void onEnter(NormalEntrance entrance);
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
