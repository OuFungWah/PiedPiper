package com.crazywah.piedpiper.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PiedDBHelper extends BaseDBHelper {

    public static final int VERSION = 0x000007;

    public static final String TABLE_NAME_USER = "user";

    public PiedDBHelper(Context context) {
        super(context, "piedDB", VERSION);
    }

    @Override
    public String[] createTable() {
        return new String[]{
                "CREATE TABLE " + TABLE_NAME_USER + " (" +
                        "  memberId         INT," +
                        "  accountId        CHAR(64) PRIMARY KEY UNIQUE ," +
                        "  nickname         CHAR(64)," +
                        "  password         CHAR(64)," +
                        "  avatar           TEXT," +
                        "  token            CHAR(225)," +
                        "  gender           CHAR(225)," +
                        "  address          CHAR(225)," +
                        "  mobile           CHAR(225)," +
                        "  birthday         INTEGER," +
                        "  registerTime     INTEGER," +
                        "  signature        CHAR(225)," +
                        "  email            CHAR(225)," +
                        "  relation         INT," +
                        "  alias            CHAR(64)," +
                        "  remark           CHAR(225)," +
                        "  friendTime       INTEGER," +
                        "  requestMessage   CHAR(225)," +
                        "  requestTime      INTEGER," +
                        "  requestStatus    INT" +
                        ");"};

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME_USER);
        for (String createDb : createTable()) {
            db.execSQL(createDb);
        }
    }

}
