package com.study.b8a3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by B8A3 on 2017/4/9.
 */

public class BaseActivity extends AppCompatActivity {

    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        Log.e(">>>>>" + TAG, "Task id is " + getTaskId());
    }
}
