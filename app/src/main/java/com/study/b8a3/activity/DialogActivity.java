package com.study.b8a3.activity;

import android.os.Bundle;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class DialogActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
