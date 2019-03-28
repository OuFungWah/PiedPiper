package com.crazywah.piedpiper.database.service;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.crazywah.piedpiper.database.dao.BaseDao;
import com.crazywah.piedpiper.database.helper.PiedDBHelper;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class DBBaseService<T> {

    private static final String TAG = "DBBaseService";

    private BaseDao dao;

    public DBBaseService(Context context) {
        dao = new BaseDao(new PiedDBHelper(context));
    }

    public boolean add(T t) {
        boolean flag;
        ContentValues contentValues = fromT(t);
        flag = dao.add(getTable(), contentValues);
        return flag;
    }

    public boolean update(T t, String where) {
        boolean flag;
        ContentValues contentValues = fromT(t);
        String[] args = getArgs(contentValues, where);
        flag = dao.update(getTable(), contentValues, where, args);
        return flag;
    }

    public boolean delete(T t, String where) {
        boolean flag;
        ContentValues contentValues = fromT(t);
        String[] args = getArgs(contentValues, where);
        flag = dao.delete(getTable(), where, args);
        return flag;
    }

    public boolean clearDB(){
        boolean flag;
        flag = dao.delete(getTable(), null, null);
        return flag;
    }

    public List<T> selectAll(T t) {
        return select(t, null, null, null, null, null);
    }

    public List<T> select(T t, String where, String groupBy, String having, String orderBy) {
        return select(t, null, where, groupBy, having, orderBy);
    }

    public List<T> select(T t, String[] columns, String where, String groupBy, String having, String orderBy) {
        Gson gson = new Gson();
        List<T> result = new ArrayList<>();
        ContentValues contentValues = fromT(t);
        String[] args = getArgs(contentValues, where);
//        Log.d(TAG, "select: columns = " + gson.toJson(columns));
//        Log.d(TAG, "select: where = " + where);
//        Log.d(TAG, "select: args = " + gson.toJson(args));
        List<Map<String, Object>> mapList = dao.select(getTable(), columns, where, args, groupBy, having, orderBy);
//        Log.d(TAG, "select: 查询到 " + mapList.size() + " 条数据");
        for (Map<String, Object> map : mapList) {
            result.add(fromMap(map));
        }
        return result;
    }

    private boolean isUsefull(String name) {
        return !"serialVersionUID".equals(name);
    }

    /**
     * 自动将查询出来的 Map 转换成对应实体类 T 对象
     *
     * @param map 从数据库查询出来，装载对应数据的 Map 对象
     * @return 装填好对应数据的对应实体类 T 对象
     */
    private T fromMap(Map<String, Object> map) {
        T result = null;
        try {
            Class<T> tClass = ((Class<T>) getTType());
//            Log.d(TAG, "fromMap: " + tClass);
            //新建范型的对象
            result = tClass.newInstance();
            for (String keyName : map.keySet()) {
                //根据查询出来的 key名 获取成员变量
                Field field = tClass.getDeclaredField(keyName);
                //根据 key名 组装 set 方法名
                field.setAccessible(true);
                StringBuffer stringBuffer = new StringBuffer(keyName);
                String typeName = field.getGenericType().toString();
                String firstChar = "" + keyName.charAt(0);
                firstChar = firstChar.toUpperCase();
                stringBuffer.deleteCharAt(0);
                String methodName = "set" + firstChar + stringBuffer;

                //调用 set 方法，并传入参数
                Object value = map.get(keyName);
//                Log.d(TAG, "fromMap: keyName " + keyName + " type Name = " + typeName);
                switch (typeName) {
                    case "int":
                        tClass.getMethod(methodName, int.class).invoke(result, Integer.parseInt(value + ""));
                        break;
                    case "long":
                        tClass.getMethod(methodName, long.class).invoke(result, Long.parseLong(value + ""));
                        break;
                    case "float":
                        tClass.getMethod(methodName, float.class).invoke(result, Float.parseFloat(value + ""));
                        break;
                    case "double":
                        tClass.getMethod(methodName, double.class).invoke(result, Double.parseDouble(value + ""));
                        break;
                    case "boolean":
                        tClass.getMethod(methodName, boolean.class).invoke(result, Boolean.parseBoolean(value + ""));
                        break;
                    case "class java.lang.String":
                        tClass.getMethod(methodName, String.class).invoke(result, (String) value);
                        break;
                    case "class java.util.Date":
                        tClass.getMethod(methodName, Date.class).invoke(result, value == null ? null : new Date(Long.parseLong(value + "")));
                        break;
                    default:
                        break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自动将实体类转换成 ContentValues
     *
     * @param t 具体实体类对象
     * @return 返回填装好的 ContentValues
     */
    private ContentValues fromT(T t) {
        ContentValues contentValues = new ContentValues();
        if (t != null) {
            try {
                Class tClass = t.getClass();
                for (Field field : tClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(t);
                    String valueName = field.getName();
                    if (isUsefull(valueName)) {
                        put(contentValues, valueName, value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return contentValues;
    }

    private String[] getColumns(String where) {
        if (TextUtils.isEmpty(where)) {
            return null;
        }
        List<String> args = new ArrayList<>();
        String[] input = where.split(" ");
        for (String temp : input) {
            if (!"".equals(temp) && !" ".equals(temp) && !"=".equals(temp) && !"?".equals(temp) && !"AND".equals(temp) && !"OR".equals(temp)) {
                args.add(temp);
            }
        }
        return args.toArray(new String[args.size()]);
    }

    protected String[] getArgs(ContentValues contentValues, String where) {
        String[] keys = getColumns(where);
        List<String> args = new ArrayList<>();
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                args.add(contentValues.get(key).toString());
            }
            return args.toArray(new String[args.size()]);
        } else {
            return null;
        }
    }

    private void put(ContentValues contentValues, String valueName, Object value) {
        if (value instanceof Integer) {
            contentValues.put(valueName, (int) value);
        } else if (value instanceof Float) {
            contentValues.put(valueName, (float) value);
        } else if (value instanceof Double) {
            contentValues.put(valueName, (double) value);
        } else if (value instanceof Long) {
            contentValues.put(valueName, (long) value);
        } else if (value instanceof String) {
            contentValues.put(valueName, (String) value);
        } else if (value instanceof Boolean) {
            contentValues.put(valueName, (boolean) value);
        } else if (value instanceof Date) {
            contentValues.put(valueName, ((Date) value).getTime());
        }
    }

    protected abstract String getTable();

    public Class getTClass() {
        return getTType().getClass();
    }

    public Type getTType() {
        Type tClass = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
