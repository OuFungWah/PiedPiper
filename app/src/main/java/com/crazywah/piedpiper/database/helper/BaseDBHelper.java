package com.crazywah.piedpiper.database.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDBHelper extends SQLiteOpenHelper {

    public BaseDBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    public BaseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String createTable : createTable()) {
            db.execSQL(createTable);
        }
    }

    public abstract String[] createTable();

}
