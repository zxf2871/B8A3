package com.study.b8a3.main;

import android.app.Application;
import android.content.Context;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class B8a3Application extends Application {

    public static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
