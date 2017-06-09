package com.study.b8a3.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.study.b8a3.main.B8a3Application;

import java.util.Map;

public class SharedPreferencesUtils {
    /**
     * sp name
     */
    public static final String FILE_NAME = "contacts_data";


    /**
     * save data
     *
     * @param key key
     * @param obj value
     */
    public static void put(String key, Object obj) {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();

        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        editor.apply();
    }

    /**
     * get key value
     */
    public static Object get(String key, Object defaultObj) {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        Object obj = null;
        if (defaultObj instanceof Boolean) {
            obj = sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            obj = sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Integer) {
            obj = sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            obj = sp.getLong(key, (Long) defaultObj);
        } else {
            obj = sp.getString(key, (String) defaultObj);
        }
        return obj == null ? defaultObj : obj;
    }

    /**
     * remove key
     */
    public static void remove(String key) {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * get all key
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Map<String, ?> map = sp.getAll();
        return map;
    }

    /**
     * clear all sp
     */
    public static void clear() {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * check key
     */
    public static boolean contains(String key) {
        SharedPreferences sp = B8a3Application.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

}
