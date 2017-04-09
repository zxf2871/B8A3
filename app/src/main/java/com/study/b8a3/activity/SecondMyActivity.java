package com.study.b8a3.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class SecondMyActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;

        setContentView(R.layout.second_cat_my_activity);
    }
}
