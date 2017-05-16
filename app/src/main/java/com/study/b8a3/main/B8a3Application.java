package com.study.b8a3.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.study.b8a3.common.CommonUtils;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class B8a3Application extends Application {

    private static String TAG = B8a3Application.class.getSimpleName();

    public static B8a3Application sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

//        String processName =
        Log.i(TAG, CommonUtils.getProcessName(this)+" "+android.os.Process.myPid());
    }

    public static Context getContext(){
        return sContext;
    }

    public static B8a3Application getInstance(){
        return sContext;
    }
}
