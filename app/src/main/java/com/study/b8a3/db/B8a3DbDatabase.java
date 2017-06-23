package com.study.b8a3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/6/23.
 */

public class B8a3DbDatabase extends SQLiteOpenHelper {

    private static B8a3DbDatabase sInstance;
    public static String DATABASE_NAME = "b8a3_test";
    public static int VESRION_CODE = 1;


    public B8a3DbDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static B8a3DbDatabase getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new B8a3DbDatabase(context, DATABASE_NAME, null, VESRION_CODE);
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
