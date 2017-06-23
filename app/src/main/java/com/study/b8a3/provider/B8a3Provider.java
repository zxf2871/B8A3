package com.study.b8a3.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/6/22.
 */

public class B8a3Provider extends ContentProvider {

    public static final String TAG = B8a3Provider.class.getSimpleName();

    @Override
    public boolean onCreate() {

        Log.e(TAG, "onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query");

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "getType");
        return "this is provider type!";
    }

    @Nullable
    @Override
    public synchronized Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "insert");
        Log.e(TAG, "insert" + values.toString());

        return null;
    }

    @Override
    public synchronized int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "delete");
        Log.e(TAG, "delete selection : " + selection + " selectionArgs : " + selectionArgs);
        return 0;
    }

    @Override
    public synchronized int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "update");
        Log.e(TAG, "update  selection : " + selection + " selectionArgs : " + selectionArgs);

        return 0;
    }
}
