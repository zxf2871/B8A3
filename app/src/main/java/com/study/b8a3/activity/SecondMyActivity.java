package com.study.b8a3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class SecondMyActivity extends AppCompatActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(">>>>>" + TAG, "Task id is " + getTaskId());

        setContentView(R.layout.second_cat_my_activity);
    }
}
