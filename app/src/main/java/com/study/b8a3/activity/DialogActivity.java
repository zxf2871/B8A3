package com.study.b8a3.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.b8a3.R;

public class DialogActivity extends Activity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(">>>>>" + TAG, "Task id is " + getTaskId());
        setContentView(R.layout.activity_dialog);
    }
}
