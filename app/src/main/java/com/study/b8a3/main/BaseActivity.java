package com.study.b8a3.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by B8A3 on 2017/4/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;
    public static int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        Log.e(">>>>>" + TAG, "Task id is " + getTaskId());
        Log.e("----i-----", ""+i++);

        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
