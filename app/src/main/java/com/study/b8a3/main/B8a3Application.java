package com.study.b8a3.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.study.b8a3.common.CommonUtils;
import com.study.b8a3.utils.GlobalHandler;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class B8a3Application extends Application {

    private static String TAG = B8a3Application.class.getSimpleName();
    private GlobalHandler mGlobalHandler;

    public static B8a3Application sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Log.i(TAG, CommonUtils.getProcessName(this)+" "+android.os.Process.myPid());
        mGlobalHandler = new GlobalHandler();
    }
    public GlobalHandler getGlobalHandler() {
        return mGlobalHandler;
    }

    public static Context getContext(){
        return sContext;
    }

    public static B8a3Application getInstance(){
        return sContext;
    }
}
