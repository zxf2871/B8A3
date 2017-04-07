package com.study.b8a3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/4/7.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
    }
}
