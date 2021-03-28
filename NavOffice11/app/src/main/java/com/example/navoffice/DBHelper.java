package com.example.navoffice;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBHelper  extends SQLiteOpenHelper{

    public static int DATABASE_VERSION = 3;
    public static final String TABLE_CONTACTS = "main";

    public static final String KEY_ROOM = "Room";
    public static final String KEY_DAY = "Day_of_ week";

    public DBHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory() + "/Download/test.db/", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_CONTACTS + " (" + KEY_GROUP
         //      + " integer, " + KEY_DAY + " text, " + KEY_ROOM + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("drop table if exists " + TABLE_CONTACTS);

        onCreate(db);

    }
}
