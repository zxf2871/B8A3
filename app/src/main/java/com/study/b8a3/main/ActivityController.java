package com.study.b8a3.main;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B8A3 on 2017/4/9.
 */

public class ActivityController {
    private static List<Activity> sList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (sList.contains(activity)) {
            return;
        }
        sList.add(activity);
    }

    public static void removeActivity(Activity activity){
        if(sList.contains(activity)){
            sList.remove(activity);
        }
    }

    public static void finishAll(){
        for(Activity activity:sList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
