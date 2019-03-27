package com.crazywah.piedpiper.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crazywah.piedpiper.database.helper.BaseDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao {

    private BaseDBHelper helper;

    public BaseDao(BaseDBHelper helper) {
        this.helper = helper;
    }

    public boolean add(String table, ContentValues contentValues) {
        boolean flag = false;
        long index = -1L;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            index = database.insert(table, null, contentValues);
            if (index != -1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    /**
     * @param table         表名
     * @param contentValues 需要被修改的字段与字段值对
     * @param where         约束字段 " name = ? "、" name = ? AND num = ? "、" name = 'Tom' " ，注意若在此参数指明了参数值请在Args参数处传null
     * @param whereArgs     约束字段对应的值
     * @return 操作是否成功
     */
    public boolean update(String table, ContentValues contentValues, String where, String whereArgs[]) {
        boolean flag = false;
        long index = -1L;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            index = database.update(table, contentValues, where, whereArgs);
            if (index != -1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    /**
     * 基础的删除功能
     *
     * @param where     约束字段名 " name = ? "、" name = ? AND num = ? "、" name = 'Tom' " ，注意若在此参数指明了参数值请在Args参数处传null
     * @param whereArgs 字段对应的值 对应的值 对应多少个问号就多少个值
     * @return 操作是否成功
     */
    public boolean delete(String table, String where, String whereArgs[]) {
        boolean flag = false;
        //用于存储操作结束后受影响的行数的变量，-1为无影响
        int index = -1;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            index = database.delete(table, where, whereArgs);
            if (index != -1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            if(database!=null) {
                database.close();
            }
        }
        return flag;
    }

    /**
     * 基础的 Select 方法
     *
     * @param table         表名
     * @param columns       选择要获取到的字段
     * @param selection     约束字段名 如： " name = ? "、" name = ? AND num = ? "、" name = 'Tom' "，注意若在此参数指明了参数值请在Args参数处传null
     * @param selectionArgs 对应的值 对应多少个问号就多少个值
     * @param groupBy       用于分组的字段名 " sex "
     * @param having        选择字段对应值的分组 " '男' "
     * @param orderBy       排序 " num ASC " " num DESC "
     * @return 返回所有符合条件的数据
     */
    public List<Map<String, Object>> select(String table, String[] columns, String selection,
                                            String[] selectionArgs, String groupBy, String having,
                                            String orderBy) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SQLiteDatabase database = null;
        /**
         * getType()返回的类型与对应的 int 值
         * int FIELD_TYPE_BLOB = 4;
         * int FIELD_TYPE_FLOAT = 2;
         * int FIELD_TYPE_INTEGER = 1;
         * int FIELD_TYPE_NULL = 0;
         * int FIELD_TYPE_STRING = 3;
         */
        Cursor cursor = null;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    //判断数据类型再按相应的类型进行存储
                    switch (cursor.getType(i)) {
                        case 0:
                            map.put(cursor.getColumnName(i), null);
                            break;
                        case 1:
                            map.put(cursor.getColumnName(i), cursor.getInt(i));
                            break;
                        case 2:
                            map.put(cursor.getColumnName(i), cursor.getFloat(i));
                            break;
                        case 3:
                            map.put(cursor.getColumnName(i), cursor.getString(i));
                            break;
                        case 4:
                            map.put(cursor.getColumnName(i), cursor.getBlob(i));
                            break;
                    }
                }
                mapList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(database!=null) {
                database.close();
            }
        }
        return mapList;
    }

}
