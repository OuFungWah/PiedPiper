package com.crazywah.piedpiper.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.common.ConstValue;

public class SPUtil {

    private static SharedPreferences sp;

    private SPUtil() {

    }

    public static SPUtil from(String name) {
        sp = PiedPiperApplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
        return getInstance();
    }

    public void save(String k, Object v) {
        if (v instanceof Integer) {
            sp.edit().putInt(k, (int) v).apply();
        } else if (v instanceof String) {
            sp.edit().putString(k, (String) v).apply();
        } else if (v instanceof Long) {
            sp.edit().putLong(k, (Long) v).apply();
        } else if (v instanceof Float) {
            sp.edit().putFloat(k, (Float) v).apply();
        } else if (v instanceof Boolean) {
            sp.edit().putBoolean(k, (Boolean) v).apply();
        }
    }

    public int getInt(String k) {
        return sp.getInt(k, 0);
    }

    public String getString(String k) {
        return sp.getString(k, "");
    }

    public long getLong(String k) {
        return sp.getLong(k, 0);
    }

    public float getFloat(String k) {
        return sp.getFloat(k, 0);
    }

    public boolean getBoolean(String k) {
        return sp.getBoolean(k, false);
    }

    public boolean contains(String k){
        return sp.contains(k);
    }

    public void remove(String k) {
        sp.edit().remove(k).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }

    public void clearAll() {
        for (String name : ConstValue.SP_NAMES) {
            from(name).clear();
        }
    }

    public static SPUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        static SPUtil instance = new SPUtil();
    }

}
